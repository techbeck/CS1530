// First-Party Imports
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.swing.*;
import java.awt.Frame;


// Third-Party Imports
import com.jwarner.jockfish.JockfishEngine;
import org.assertj.swing.core.*;
import org.assertj.swing.fixture.*;
import org.assertj.swing.exception.*;
import org.assertj.swing.core.matcher.JButtonMatcher.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.swing.launcher.ApplicationLauncher.application;
import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.finder.JOptionPaneFinder.findOptionPane;

// Local Imports
import com.caffeine.Chess;
import com.caffeine.view.*;

public class ChessTest {

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

    // @Test
    // public void testChessPieceMove(){
    //     JButtonFixture firstSquare = frame.findByName("boardSquare:E,2", BoardSquare.class);
    //     JButtonFixture secondSquare = frame.findByName("boardSquare:E,4", BoardSquare.class);
    //     firstSquare.click();
    //     secondSquare.click();
    //     firstSquare.requireText(" ");
    //     secondSquare.requireText("\u2659");
    // }

    @Test
    public void testJockfishEngineIntegrated(){
        String expected = "Stockfish 7 64 by T. Romstad, M. Costalba, J. Kiiski, G. Linscott\n";
        JockfishEngine jockfish = new JockfishEngine();
        String observed = jockfish.read();
        assertTrue(expected.equals(observed));
    }

	@Test
	public void testAssertJIntegrated(){
		assertThat("test").isEqualTo("test");
	}
}
