// First-Party Imports
import javax.swing.*;
import java.awt.Frame;
import java.awt.Color;
import java.awt.event.*;

// Third-Party Imports
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import org.assertj.swing.core.*;
import org.assertj.swing.fixture.*;
import org.assertj.swing.exception.*;
import org.assertj.swing.core.matcher.*;
import org.assertj.swing.core.matcher.JButtonMatcher.*;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.swing.launcher.ApplicationLauncher.application;
import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.finder.JOptionPaneFinder.findOptionPane;
import static org.assertj.swing.fixture.Containers.showInFrame;

// Local Imports
import com.caffeine.Chess;
import com.caffeine.view.*;


/* ============================================================================
                                Integration Tests
============================================================================ */
public class ChessTest {

    @Test // Sanity Check -- Is Chess properly imported and accessible?
    public void testChessIntegrated(){
        Chess game = new Chess();
    }

    static FrameFixture frame;
    static Robot robot;

    @BeforeClass
    public static void setUp() {
        application(Chess.class).start();
        robot = BasicRobot.robotWithCurrentAwtHierarchy();
        frame = findFrame("frame").using(robot);
    }

    @AfterClass
    public static void tearDown(){
        frame.close();
        frame.cleanUp();
    }

    @Test
    public void testWindowIsNotResizeable(){
        boolean threwError = false;

        try{
            frame.resizeWidthTo(250);
        }
        catch(IllegalStateException e){
            threwError = true;
        }

        assertTrue("Window Should not be Resizeable", threwError);
    }

    @Test
    public void testLoadButtonCreatesOptionPane(){
        frame.button("loadButton").click();
        JOptionPaneFixture optionPane = findOptionPane().using(robot);
        optionPane.requireTitle("Load Game");
        optionPane.cancelButton().click();
    }

    @Test
    public void testLoadButtonEnterText(){
        frame.button("loadButton").click();
        JOptionPaneFixture optionPane = findOptionPane().using(robot);
        optionPane.textBox().enterText("newgame");
        optionPane.okButton().click();
        String actualLabel = frame.label("statusLabel").text();
        assertThat(actualLabel).contains("Loading game from file: newgame.pgn");
    }

    @Test
    public void testSaveButtonCreatesOptionPane(){
        frame.button("saveButton").click();
        JOptionPaneFixture optionPane = findOptionPane().using(robot);
        optionPane.requireTitle("Save Game");
        optionPane.cancelButton().click();
    }

    @Test
    public void testSaveButtonEnterText(){
        frame.button("saveButton").click();
        JOptionPaneFixture optionPane = findOptionPane().using(robot);
        optionPane.textBox().enterText("oldgame");
        optionPane.okButton().click();
        String actualLabel = frame.label("statusLabel").text();
        assertThat(actualLabel).contains("Saving game to file: oldgame.pgn");
    }

    @Test
    public void testChooseSideButtonCreatesOptionPane(){
        frame.button("chooseSideButton").click();
        JOptionPaneFixture optionPane = findOptionPane().using(robot);
        optionPane.requireTitle("Choose Side");
        optionPane.cancelButton().click();
    }

    @Test
    public void testChooseWhite(){
        frame.button("chooseSideButton").click();
        JOptionPaneFixture optionPane = findOptionPane().using(robot);
        optionPane.buttonWithText("White").click();
        String actualLabel = frame.label("statusLabel").text();
        assertThat(actualLabel).contains("Now playing as White");
    }

    @Test
    public void testChooseBlack(){
        frame.button("chooseSideButton").click();
        JOptionPaneFixture optionPane = findOptionPane().using(robot);
        optionPane.buttonWithText("Black").click();
        String actualLabel = frame.label("statusLabel").text();
        assertThat(actualLabel).contains("Now playing as Black");
    }

    @Test
    public void testTutorialButtonCreatesOptionPane(){
        frame.button("tutorialButton").click();
        JOptionPaneFixture optionPane = findOptionPane().using(robot);
        optionPane.requireTitle("Tutorial");
        optionPane.okButton().click();
    }

    @Test
    public void testChessPieceMove(){
        ComponentFinder newFinder = robot.finder();
        JButton firstSquare = (JButton) newFinder.findByName(frame.target(), "BoardSquare:E,2", BoardSquare.class);
        JButton secondSquare = (JButton) newFinder.findByName(frame.target(), "BoardSquare:E,4", BoardSquare.class);
        JButtonFixture firstSquareFix = new JButtonFixture(robot, firstSquare);
        JButtonFixture secondSquareFix = new JButtonFixture(robot, secondSquare);
        String expectedText = firstSquareFix.text();
        firstSquareFix.click();
        secondSquareFix.click();
        firstSquareFix.requireText(" ");
        secondSquareFix.requireText(expectedText);
    }

    @Test
    public void testWhitePieceColorChange(){
        ComponentFinder newFinder = robot.finder();
        JButton whiteSquare = (JButton) newFinder.findByName(frame.target(), "BoardSquare:A,1", BoardSquare.class);
        JButtonFixture squareFix = new JButtonFixture(robot, whiteSquare);
        frame.button("pieceColorButton").click();
        JOptionPaneFixture optionPane = findOptionPane().using(robot);
        optionPane.buttonWithText("White").click();
        JColorChooser chooser = (JColorChooser) newFinder.findByType(JColorChooser.class);
        chooser.setColor(Color.RED);
        JButtonMatcher matcher = JButtonMatcher.withText("OK").andShowing();
        JButtonFixture okButton = new JButtonFixture(robot,(JButton)newFinder.find(matcher));
        okButton.click();
        squareFix.foreground().requireEqualTo(Color.RED);
    }

    @Test
    public void testBlackPieceColorChange(){
        ComponentFinder newFinder = robot.finder();
        JButton blackSquare = (JButton) newFinder.findByName(frame.target(), "BoardSquare:A,8", BoardSquare.class);
        JButtonFixture squareFix = new JButtonFixture(robot, blackSquare);
        frame.button("pieceColorButton").click();
        JOptionPaneFixture optionPane = findOptionPane().using(robot);
        optionPane.buttonWithText("Black").click();
        JColorChooser chooser = (JColorChooser) newFinder.findByType(JColorChooser.class);
        chooser.setColor(Color.BLUE);
        JButtonMatcher matcher = JButtonMatcher.withText("OK").andShowing();
        JButtonFixture okButton = new JButtonFixture(robot,(JButton)newFinder.find(matcher));
        okButton.click();
        squareFix.foreground().requireEqualTo(Color.BLUE);
    }
}
