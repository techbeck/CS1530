// First-Party Libs
import java.util.*;

// Third-Party Libs
import org.junit.Test;
import static org.junit.Assert.*;

// Local Libs
import com.caffeine.engine.Core;

public class CoreTest {

    @Test
    public void testJockfishIntegration(){
        String expected = "Stockfish 7 64 by T. Romstad, M. Costalba, J. Kiiski, G. Linscott\n";
        Core engine = new Core();
        String observed = engine.out();
        assertTrue(expected.equals(observed));
    }

    @Test
    public void testCoreUCI(){
        Core engine = new Core();
        String trash = engine.out(); // Eat greeting string.
        HashMap<String, Object> engineConfig = engine.uci();
        for (Map.Entry<String, Object> entry : engineConfig.entrySet()){
            if (entry.getKey().equals("options")){
                for (Map.Entry<String, String> fntry : entry.getValue().entrySet()){
                    System.out.println("\t"+fntry.getKey()+" "+fntry.getValue());
                }
            } else {
                System.out.print(String.format("%s: %s\n", entry.getKey(), entry.getValue()));
            }
        }
    }
}
