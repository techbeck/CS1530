package com.caffeine.engine;

// First-Party Imports
import java.util.*;
import java.lang.Thread;

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

    public void write(String command){
        // Wrapper around JockfishEngine.write()
        jockfish.write(command);
    }

    public String read(){
        // Wrapper around JockfishEngine.read()
        return jockfish.read();
    }

    public String readLine(){
        // Wrapper around JockfishEngine.readLine()
        return jockfish.readLine().trim();
    }

    public void flush(){
        // Discards JockfishEngine output buffer
        jockfish.read();
    }

    public HashMap<String, String> getConfig(){
        // TODO: DOCSTRINGS
        HashMap<String, String> result = new HashMap<String, String>();
        write("uci");
        String responseLine;
        while (true){
            responseLine = readLine();
            if (responseLine.equals("uciok")){ break; }
            if (responseLine.contains("option name")){
                String[] items = responseLine.split(" ", 4);
                result.put(items[2], items[3]);
            }
        }
        return result;
    }

    public String getFEN(){
        // Retrieves current board from Stockfish.
        // Returns said Board as a FEN string.
        String result = "";
        String responseLine;
        write("d");
        write("isready");
        while (true){
            responseLine = readLine();
            if (responseLine.equals("readyok")){ break; }
            if (responseLine.contains("Fen")){
                result = responseLine.split(" ", 2)[1];
            }
        }
        return result;
    }

    public boolean setFEN(String fen){
        // Sets board in Stockfish.
        // Returns true if successful.
        if (!Utils.isValidFEN(fen)){ return false; }
        flush();
        write(String.format("position fen %s", fen));
        String newFen = getFEN();
        return (newFen.equals(fen));
    }

// ================= INCOMPLETE/WIP METHODS    ================================
// ================= DO NOT USE THESE METHODS! ================================

    public String getBestMove(int timeout){
        // Gets suggested best move from Stockfish after a maximum time (ms).
        // Returns said move.
        String result = "";
        String responseLine;
        write(String.format("go movetime %d", timeout));
        while (true){
            responseLine = readLine();
            if (responseLine.contains("bestmove")){
                result = responseLine.split(" ", 3)[1];
                break;
            }
        }
        return result;
    }

    // TODO: USE UTILS PKG, FINISH AND TEST
    public boolean move(String move){
        return false;
    }

    // TODO: USE UTILS PKG, FINISH AND TEST
    public String cpuMove(int timeout){
        // Does suggested best move from Stockfish after a maximum time (ms).
        // Returns said move.
        return "";
    }
}
