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
        // TODO: DOCSTRINGS
        HashMap<String, String> result = new HashMap<String, String>();
        jockfish.write("uci");
        String responseLine;
        while (true){
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

    public ArrayList<String> getAuthors(){
        // TODO: DOCSTRINGS
        ArrayList<String> result = new ArrayList<String>();
        jockfish.write("uci");
        String responseLine;
        while(true){
            responseLine = jockfish.readLine().trim();
            if (responseLine.equals("uciok")) { break; }
            if (responseLine.contains("id author")){
                String authorString = responseLine.split(" ", 3)[2];
                String[] authors = authorString.split(",")
                for (int i = 0; i < authors.length; i++){ result.add(authors[i].trim()); }
            }
        }
        return result;
    }
}
