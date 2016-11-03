package com.caffeine.engine;

// First-Party Imports
import java.util.*;

// Third-Party Imports
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import com.jwarner.jockfish.JockfishEngine;

// Local Imports
import com.caffeine.logic.Utils;

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

    public HashMap<String, String> getConfig(){
        HashMap<String, String> result = new HashMap<String, String>();
        jockfish.write("uci");
        String responseLine;
        while (true) {
            responseLine = jockfish.readLine().trim();
            if (responseLine.equals("uciok")) { break; }
            if (responseLine.contains("option name")){
                String[] items = responseLine.split(" ", 4);
                result.put(items[2], items[3]);
            }
        }
        return result;
    }

// ================= INCOMPLETE/WIP METHODS    ================================
// ================= DO NOT USE THESE METHODS! ================================

    public void go(String type, String[] parameters){
        String cmd = String.format("go %s ", type);
        for (int i = 0; i < parameters.length; i++){ cmd.concat(parameters[i] + " "); }
        jockfish.write(cmd);
    }

    public boolean setFEN(String fen){
        if (Utils.isValidFEN(fen)){
            jockfish.write(String.format("position", fen));
            return true;
        }
        return false;
    }


    public void stop(){
        jockfish.write("stop");
    }

}
