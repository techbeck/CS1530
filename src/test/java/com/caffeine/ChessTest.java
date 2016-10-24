// First-Party Imports
import org.junit.Test;
import static org.junit.Assert.*;

// Third-Party Imports
import com.jwarner.jockfish.JockfishEngine;

// Local Imports
import com.caffeine.Chess;

public class ChessTest {

    @Test
    public void testExitWithoutError(){
        String phrase = "Our Chess is The One True Chess.";
        assertEquals(Chess.echo(phrase), phrase);
    }

    @Test
    public void testJockfishEngineIntegrated(){
        String expected = "Stockfish 7 64 by T. Romstad, M. Costalba, J. Kiiski, G. Linscott\n";
        JockfishEngine jockfish = new JockfishEngine();
        String observed = jockfish.read();
        assertTrue(expected.equals(observed));
    }
}
