// First-Party Imports

// Third-Party Imports
import org.junit.Test;
import static org.junit.Assert.*;
import com.jwarner.jockfish.JockfishEngine;

// Local Imports


/* ============================================================================
                            Engine Core Tests
============================================================================ */
public class CoreTest {

    @Test // Sanity Check -- Is Chess properly imported and accessible?
    public void testJockfishEngineIntegrated(){
        String expected = "Stockfish 7 64 by T. Romstad, M. Costalba, J. Kiiski, G. Linscott\n";
        JockfishEngine jockfish = new JockfishEngine();
        String observed = jockfish.read();
        assertTrue(expected.equals(observed));
    }

}
