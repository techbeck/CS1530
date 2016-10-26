// First-Party Imports
import org.junit.BeforeClass;
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

// Local Imports
import com.caffeine.Chess;

public class ChessTest {

    @BeforeClass
    public static void setUp() {
        application(Chess.class).start();
    }

    @Test
    public void testWindowIsNotResizeable(){
        boolean threwError = false;
        Robot robot = BasicRobot.robotWithCurrentAwtHierarchy();
        FrameFixture frame = findFrame("frame").using(robot);
        try{
            frame.resizeWidthTo(250);
        }
        catch(IllegalStateException e){
            threwError = true;
        }

        assertTrue("Window Should not be Resizeable", threwError);
    }

    @Test
    public void testLoadButton(){
        Robot robot = BasicRobot.robotWithCurrentAwtHierarchy();
        FrameFixture frame = findFrame("frame").using(robot);
        frame.button("loadButton").click();
    }

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
