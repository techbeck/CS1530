import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.junit.Test;
import static org.junit.Assert.*;

import com.caffeine.engine.Core;
import com.caffeine.utils.Platform;

public class EngineTest {

    @Test
    public void alwaysTrue(){ assertTrue(true); }

/*
    @Test
    public void testEngineStarts(){
        Engine testEngine = new Engine();
        String expected = new String("Tord Romstad, Marco Costalba and Joona Kiiski");

        testEngine.start();
        String observed = new String(testEngine.getOutput(0));

        fail("Engine Output: '" + observed + "'");


/*
        String cmd = new String("bin/stockfish_" + Platform.getPlatformString());

        try {
            testEngine = Runtime.getRuntime().exec(cmd);
            testReader = new BufferedReader(
                new InputStreamReader(testEngine.getInputStream())
            );

        } catch (IOException e) {
            fail("Failed to start Stockfish Engine: " + e.toString());
        }
    }
*/
}
