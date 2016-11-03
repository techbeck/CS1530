// First-Party Libs
import java.util.*;

// Third-Party Libs
import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.lang3.StringUtils;

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
    public void testCoreGetConfig(){
        Core engine = new Core();

        HashMap<String, String> response = engine.getConfig();

        // Define Options as initialized in a new Stockfish instance
        List<List<String>> options = new List<List<String>>();
        options.add(["Write", "Debug Log type check default false"]);
        options.add(["Contempt", "type spin default 0 min -100 max 100"]);
        options.add(["Threads", "type spin default 1 min 1 max 128"]);
        options.add(["Hash", "type spin default 16 min 1 max 1048576"]);
        options.add(["Clear", "Hash type button"]);
        options.add(["Ponder", "type check default false"]);
        options.add(["MultiPV", "type spin default 1 min 1 max 500"]);
        options.add(["Skill", "Level type spin default 20 min 0 max 20"]);
        options.add(["Move", "Overhead type spin default 30 min 0 max 5000"]);
        options.add(["Minimum", "Thinking Time type spin default 20 min 0 max 5000"]);
        options.add(["Slow", "Mover type spin default 84 min 10 max 1000"]);
        options.add(["nodestime", "type spin default 0 min 0 max 10000"]);
        options.add(["UCI_Chess960", "type check default false"]);
        options.add(["SyzygyPath", "type string default <empty>"]);
        options.add(["SyzygyProbeDepth", "type spin default 1 min 1 max 100"]);
        options.add(["Syzygy50MoveRule", "type check default true"]);
        options.add(["SyzygyProbeLimit", "type spin default 6 min 0 max 6"]);

        for (String[] option : options){
            String optString = response.get(option[0]);
            if (StringUtils.isBlank(optString)){
                fail("Did not find option '"+optString+"'.");
            }
            if (!optString.equals(option[1])){
                String failString = String.format(
                    "Option did not match default.\n" +
                    "  DEFAULT: '%s'\n" +
                    "  FOUND:   '%s'\n",
                    option[1],
                    optString
                );
                fail(failString);
            }
        }
    }
}
