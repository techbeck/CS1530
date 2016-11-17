package com.caffeine.logic;

import java.util.ArrayList;

import com.caffeine.Chess;
import com.caffeine.logic.Board;
import com.caffeine.logic.State;

public class Game {

    // Instance vars
    public State state;
    public com.caffeine.engine.Core engine;



    // Constructors
    public Game(){
        state = new State();
        engine = Chess.engine;
    }




/* =========================================================================
                        View Layer API (GUI <---> Manager)
   ========================================================================= */


    /*
     *   Setters: The view layer uses these to ALTER state.
     */
    public boolean move(String move){
        // Takes a String composed of two valid board positions.
        //
        // Tests the move provided using Stockfish.
        // If test move succeeds:
        //     Updates the Piece's position on both Board & Piece.
        //     Marks any captured pieces automatically.
        //
        // These changes manifest visually when the GUI refreshes itself.
        boolean invalidMove;

        if (!Utils.isValidMove(move)){ return false; }

        invalidMove = !engine.move(move);
        if (invalidMove){ return false; } // Move didn't take in Stockfish.
        else { return state.move(move); }
        // (Delegated to State class for package access)
    }

    public boolean newGame(boolean userIsWhite){
        // TODO
        //     Will completely clear existing State and restart the match.
        //
        //     One way to implement this is to create a "New Game" save file
        //     and make this an alias for 'loadGame(templateFilePath)'.
        //
        //     Another way (provided we keep ALL state in the State class)
        //     would be to replace the existing state with 'new State()'.
        //
        return false;
    }

    public boolean loadGame(String filepath){
        // TODO
        //     Will delegate to FileManager class and create a State using the
        //     selected file.
        //
        //     If there is a problem with the file, the State isn't affected and
        //     this returns false.
        //
        //     Only returns 'true' if the new State was successfully loaded.
        //
        return false;
    }

    public boolean saveGame(String filepath){
        // TODO
        //     Will delegate to FileManager class which will translate the
        //     current State into PGN format.
        //
        //     If there is any problem with the save file, this returns false.
        //
        //     Only returns 'true' if the new State was successfully saved.
        //
        return false;
    }



    /*
     *   Getters: The view layer uses these to READ state.
     */
    public String getPiecesCapturedByBlack(){
        // Returns a String of unicode symbols representing
        // The white pieces that Black has captured.
        return state.getPiecesCapturedByBlack();
    }

    public String getPiecesCapturedByWhite(){
        // Returns a String of unicode symbols representing
        // The black pieces that White has captured.
        return state.getPiecesCapturedByBlack();
    }

    public String getBoard(){
        // Returns the full board as one convenient String.
        //
        // For example, the board:
        //
        // +---+---+---+---+---+---+---+---+
        // |   |   | k | r |   |   | r |   |
        // +---+---+---+---+---+---+---+---+
        // |   | p | p | b |   | p |   | p |
        // +---+---+---+---+---+---+---+---+
        // |   |   |   |   |   |   |   |   |
        // +---+---+---+---+---+---+---+---+
        // | p | N | B | p |   | Q | p |   |
        // +---+---+---+---+---+---+---+---+
        // |   | n |   |   |   | B |   |   |
        // +---+---+---+---+---+---+---+---+
        // |   | P |   |   |   |   |   |   |
        // +---+---+---+---+---+---+---+---+
        // | P |   | P |   |   | P | P | P |
        // +---+---+---+---+---+---+---+---+
        // | R |   |   |   |   | R | K |   |
        // +---+---+---+---+---+---+---+---+
        //
        //  Would be returned as:
        //      --kr--r--ppb-p-p--------pNBp-Qp--n---B---P------P-P--PPPR----RK-
        //
        return state.getBoardAsString();
    }

    public ArrayList<String> getMoveHistory(){
        // TODO
        return new ArrayList<String>();
    }

    public Character getUserColor(){
        // Returns either 'w' or 'b'.
        return state.getUserColor();
    }

}
