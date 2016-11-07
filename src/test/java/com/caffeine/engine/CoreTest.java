// First-Party Imports
import java.util.*;

// Third-Party Imports
import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.lang3.StringUtils;

// Local Imports
import com.caffeine.engine.Core;

public class CoreTest {

    @Test
    public void testJockfishIntegration(){
        String expected = "Stockfish 7 64 by T. Romstad, M. Costalba, J. Kiiski, G. Linscott\n";
        Core engine = new Core();
        String observed = engine.read();
        assertTrue(expected.equals(observed));
    }

    @Test
    public void testCoreGetConfig(){
        Core engine = new Core();

        HashMap<String, String> response = engine.getConfig();

        // Define Options as initialized in a new Stockfish instance
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("Write", "Debug Log type check default false");
        options.put("Contempt", "type spin default 0 min -100 max 100");
        options.put("Threads", "type spin default 1 min 1 max 128");
        options.put("Hash", "type spin default 16 min 1 max 1048576");
        options.put("Clear", "Hash type button");
        options.put("Ponder", "type check default false");
        options.put("MultiPV", "type spin default 1 min 1 max 500");
        options.put("Skill", "Level type spin default 20 min 0 max 20");
        options.put("Move", "Overhead type spin default 30 min 0 max 5000");
        options.put("Minimum", "Thinking Time type spin default 20 min 0 max 5000");
        options.put("Slow", "Mover type spin default 84 min 10 max 1000");
        options.put("nodestime", "type spin default 0 min 0 max 10000");
        options.put("UCI_Chess960", "type check default false");
        options.put("SyzygyPath", "type string default <empty>");
        options.put("SyzygyProbeDepth", "type spin default 1 min 1 max 100");
        options.put("Syzygy50MoveRule", "type check default true");
        options.put("SyzygyProbeLimit", "type spin default 6 min 0 max 6");

        for (Map.Entry<String, String> option : options.entrySet()){
            String optString = response.getOrDefault(option.getKey(), "KNP");
            if (optString.equals("KNP")){ fail("Did not find optString for key '"+option.getKey()+"'."); }
            if (!optString.equals(response.get(option.getKey()))){
                String failString = String.format(
                    "Option did not match expected.\n" +
                    "  DEFAULT: '%s'\n" +
                    "  FOUND:   '%s'\n",
                    option.getValue(),
                    optString
                );
                fail(failString);
            }
        }
    }

    @Test
    public void testCoreGetFEN(){
        Core engine = new Core();

        final String expected = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2";

        // Do it the hard way since we can't count on setFen() yet
        engine.write(String.format("position fen %s\n", expected));
        String observed = engine.getFEN();

        assertTrue(expected.equals(observed));
    }

    @Test
    public void testCoreSetFEN(){
        Core engine = new Core();

        final String fen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2";

        boolean result = engine.setFEN(fen);

        String newFen = engine.getFEN();

        assertTrue(result && newFen.equals(fen));
    }

    @Test
    public void testCoreGetBestMove(){
        Core engine = new Core();

        final String expected = "e2e4";

        String observed = engine.getBestMove(500);

        assertTrue(expected.equals(observed));
    }
}
