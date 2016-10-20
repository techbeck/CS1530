package com.caffeine.engine;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.caffeine.utils.Platform;

public class Core {

    private Process stockfish;
    private BufferedReader stockfishReader;
    private OutputStreamWriter stockfishWriter;

    public boolean start(){

        String cmd = "bin/stockfish_" + Platform.getPlatformString();

        // try {
        //     engine = Runtime.getRuntime().exec(cmd);
        //     engineReader = new BufferedReader(new InputStreamReader(
        //             engine.getInputStream())
        //     );
        //     engineWriter = new OutputStreamWriter(
        //             engine.getOutputStream()
        //     );
        // } catch (Exception e) {
        //     return false;
        // }
        return true;
    }
}
