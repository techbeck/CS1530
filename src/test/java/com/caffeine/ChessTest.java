// First-Party Imports
import org.junit.Test;
import static org.junit.Assert.*;

// Third-Party Imports
import com.jwarner.jockfish.JockfishEngine;
import static org.assertj.core.api.Assertions.*;

// Local Imports
import com.caffeine.Chess;

public class ChessTest {

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
