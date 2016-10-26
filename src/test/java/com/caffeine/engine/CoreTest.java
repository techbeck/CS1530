// First-Party Imports
import org.junit.Test;
import static org.junit.Assert.*;

// Local Imports
import com.caffeine.engine.Core;

public class CoreTest {

    @Test
    public void testJockfishIntegration(){
        String expected = "Stockfish 7 64 by T. Romstad, M. Costalba, J. Kiiski, G. Linscott\n";
        Core engine = new Core();
        String observed = engine.out();
        assertTrue(expected.equals(observed));
    }
}
