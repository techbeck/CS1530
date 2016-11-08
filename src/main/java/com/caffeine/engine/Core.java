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

    public boolean move(String move){
        // Gets current board, performs a move, and tests to verify it happened.
        String oldBoard, newBoard, sanMove;

        sanMove = move.toLowerCase().trim();

        oldBoard = getFEN();
        write(String.format("position fen %s moves %s", oldBoard, sanMove));
        newBoard = getFEN();

        return (!newBoard.equals(oldBoard));
    }

    public String cpuMove(int timeout){
        // Does suggested best move from Stockfish after a maximum time (ms).
        // Returns said move.
        boolean pieceMoved;
        String bestMove;
        bestMove = getBestMove(timeout);
        pieceMoved = move(bestMove);
        if (pieceMoved){ return bestMove; }
        else { return "(none)"; }
    }

// ================= INCOMPLETE/WIP METHODS    ================================
// ================= DO NOT USE THESE METHODS! ================================

    public ArrayList<String> getCheckers(){
        // Gets list of positions from which active color is in Check.
        ArrayList<String> result = new ArrayList<String>();
        return result;
    }

    // Look into using Stockfish to get list of moves with SEARCH
}
