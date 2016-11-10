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

    /**
     *  Gets the current configuration settings specified for Stockfish
     *  @return a hashmap of Stockfish's current configuration
     */
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

    /**
     *  Retrieves the current chess board from Stockfish
     *  @return the current chess board as a FEN String
     */
    public String getFEN(){
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

    /**
     *  Sets the chess board in Stockfish
     *  @param  fen The desired chess board as a FEN string
     *  @return true if successful, false otherwise
     */
    public boolean setFEN(String fen){
        if (!Utils.isValidFEN(fen)){ return false; }
        flush();
        write(String.format("position fen %s", fen));
        String newFen = getFEN();
        return (newFen.equals(fen));
    }

    /**
     *  Gets a suggested best move from Stockfish after a specified
     *  amount of alloted time.
     *  @param  timeout How long (in ms) Stockfish can ponder upon a move
     *  @return a moved suggested by Stockfish
     */
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

    /**
     *  Gets current board, performs a move, and tests to verify it happened.
     *  @param  move The requested move, as a String
     *  @return true if the move was successful, false if Stockfish rejected the move
     */
    public boolean move(String move){
        String oldBoard, newBoard, sanMove;

        sanMove = move.toLowerCase().trim();

        oldBoard = getFEN();
        write(String.format("position fen %s moves %s", oldBoard, sanMove));
        newBoard = getFEN();

        return (!newBoard.equals(oldBoard));
    }

    /**
     *  Performs suggested best move from Stockfish after a maximum time (ms).
     *  @param  timeout How long (in ms) Stockfish should ponder upon a move
     *  @return the move that was just performed
     */
    public String cpuMove(int timeout){
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
