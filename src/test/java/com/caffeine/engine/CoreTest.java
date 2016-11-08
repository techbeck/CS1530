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

        final String expected = "d4e5";
        final String board = "k7/8/8/4q3/3B4/8/8/7K w - - 0 1";

        engine.setFEN(board);
        String observed = engine.getBestMove(500);

        assertTrue(expected.equals(observed));
    }

    @Test
    public void testCoreMove(){
        Core engine = new Core();

        final String newBoard = "2r5/1p1nbqk1/8/8/8/8/1P1NBQK1/2R5 w - - 0 1";
        final String expectedEnd = "r5k1/8/1n6/1q6/1Q6/1N6/7K/R7 w - - 0 8";

        String observedEnd;

        engine.setFEN(newBoard);
        engine.move("b2b4"); // T: White pawn u 2
        engine.move("b7b5"); // T: Black pawn d 2
        engine.move("C1a1"); // T: White rook l 3 (Should be case insensitive)
        engine.move("c8A8"); // T: Black rook r 3 (Should be case insensitive)
        engine.move("d2b3"); // T: White knight u/l
        engine.move("d7b6"); // T: Black knight d/l
        engine.move("e2b5"); // T: White bishop takes black pawn, u/l
        engine.move("e7b4"); // T: Black bishop takes white pawn, d/l
        engine.move("F2H4"); // T: White queen u/r 2 (Should be case insensitive)
        engine.move("f7f1"); // T: Black queen d 7 (White king in check)
        engine.move("b5a6"); // F: White bishop, u/l, king still in check
        engine.move("g2h2"); // T: White king r 1 (Moves out of check)
        engine.move("g7h7"); // F: Black king r 1 (Moves into check)
        engine.move("g7g8"); // T: Black king u 1
        engine.move("h4b4"); // T: White queen takes black bishop
        engine.move("f1b5"); // T: Black queen takes white bishop
        observedEnd = engine.getFEN();

        assertTrue(expectedEnd.equals(observedEnd));
    }

    @Test
    public void testCoreCpuMove(){
        // Plays 10 moves of Chess on modified board, expects checkmate.
        Core engine = new Core();
        String moveTest = "";

        final String board = "kr6/8/8/8/8/8/8/K6r w - - 0 1";

        engine.setFEN(board);
        for (int i = 0; i < 10; i++){ moveTest = engine.cpuMove(0); }
        if (!moveTest.equals("(none)")){ fail(); }
    }
}
