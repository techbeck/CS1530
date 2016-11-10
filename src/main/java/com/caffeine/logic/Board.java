package com.caffeine.logic;

import com.caffeine.logic.Piece;
import com.caffeine.logic.Utils;

public class Board {

    // Board Represented by an 8x8 Piece array.
    private Piece[][] = new Piece[8][8];



    // Constructors
    public Board(String fen) throws IllegalArgumentException {

    }

    public Board(){
        final String defaultFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        this(defaultFEN);
    }



    // Public Methods
    public Piece get(String position) throws IllegalArgumentException {
        if (!Utils.isValidBoardPosition(position)){
            String exMsg = "Invalid position was encountered.";
            throw new IllegalArgumentException(exMsg);
        } else {
            Integer[] idx = translate(position);
            return
        }
    }

    public boolean put(String position) throws IllegalArgumentException {
        if (!Utils.isValidBoardPosition(position)){
            String exMsg = "Invalid position was encountered.";
            throw new IllegalArgumentException(exMsg);
        } else {
            break;
        }
    }

    // Private Methods
    private Integer[] translate(String position){
        // File and Idx[0] are the letter.
        String file, rank;
        Integer col, row;
        file = position.substring(0,1).toLowerCase();
        rank = position.substring(1,2);
        Integer[] indices = new Integer[2];
        indices[0] = ((int)file.charAt(0))-97;
        indices[1] = Integer.parseInt(rank)-1;
        return indices;
    }

}
