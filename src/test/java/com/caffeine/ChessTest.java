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
import org.assertj.swing.testing.AssertJSwingTestCaseTemplate;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.swing.launcher.ApplicationLauncher.application;
import static org.assertj.swing.finder.WindowFinder.findFrame;

// Local Imports
import com.caffeine.Chess;

public class ChessTest {
    public FrameFixture frame;
    @BeforeClass
    public static void setUp() {
        application(Chess.class).start();
    }

    @Test
    public void testMyFaithInGod(){
        Robot robot = BasicRobot.robotWithNewAwtHierarchy();
        frame = findFrame()
        frame = findFrame(new GenericTypeMatcher<Frame>(Frame.class) {
            protected boolean isMatching(Frame frame) {
                return "Laboon Chess".equals(frame.getTitle()) && frame.isShowing();
            }
        }).using(robot);
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
