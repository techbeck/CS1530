import org.junit.Test;
import static org.junit.Assert.*;

import com.caffeine.Chess;

public class ChessTest {

    @Test
    public void testExitWithoutError(){
        String phrase = "Our Chess is The One True Chess.";
        assertEquals(Chess.echo(phrase), phrase);
    }
}
