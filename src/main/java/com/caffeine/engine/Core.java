package com.caffeine.engine;

// First-Party Libs
import java.util.*;

// Local Libs
import com.jwarner.jockfish.JockfishEngine;

public class Core{

    private JockfishEngine jockfish;

    public Core(){
        jockfish = new JockfishEngine();
    }

    public void in(String command){
        // Wrapper around JockfishEngine.write()
        jockfish.write(command);
    }

    public String out(){
        // Wrapper around JockfishEngine.read()
        return jockfish.read();
    }

    // UCI Interface

    public void go(String type, String[] parameters){
        String cmd = String.format("go %s ", type);
        for (int i = 0; i < parameters.length; i++){
            cmd.concat(parameters[i] + " ");
        }
        jockfish.write(cmd);
    }

    public void stop(){
        jockfish.write("stop");
    }

    // Write a UCI method that returns a Dict of values representing:
    //
    // ID:
    // AUTHOR:
    // OPTIONS:
    //     WRITE:
    //     CONTEMPT:
    //     THREADS:
    //     HASH:
    //     CLEAR:
    //     PONDER:
    //     MULTIPV:
    //     SKILL-LEVEL:
    //     MOVE-OVERHEAD:
    //     MINIMUM-THINKING-TIME:
    //     SLOW-MOVER:
    //     NODES-TIME:
    //     UCI-CHESS960:
    //     SYZYGY-PATH:
    //     SYZYGY-PROBE-DEPTH:
    //     SYZYGY-50-MOVE-RULE:
    //     SYZYGY-PROBE-LIMIT:

// TODO: WRITE JOCKFISH UNIT TESTS
// TODO: UPGRADE JOCKFISH ENGINE LOCALLY

    public HashMap<String, Object> uci(){
        String res;
        String[] resArr;
        Map<String, Object> result = new HashMap<String, Object>();

        jockfish.write("uci");

        // Turn string responses into an accessible dictionary-like mapping.
        result.put("options", new HashMap<String, String>()); // Options subMap
        loopReadLines: while(true){
            res = jockfish.readLine().trim();
            resArr = res.split(" ");
            switch (resArr[0]){
                case "id":
                    result.put(resArr[1], Arrays.copyOfRange(resArr, 2, resArr.length));
                    break;
                case "options":
                    result.get("options").put(resArr[2], Arrays.copyOfRange(resArr, 3, resArr.length));
                    break;
                case "uciok":
                    break loopReadLines;
                default:
                    break;
            }
        }
    }
}
