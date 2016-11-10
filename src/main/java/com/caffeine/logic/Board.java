package com.caffeine.logic;

import java.utils.Arrays;
import java.utils.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.caffeine.logic.Piece;
import com.caffeine.logic.Utils;

public class Board {

    // Board Represented by an 8x8 Piece array.
    private Piece[][] pieces = new Piece[8][8];



    // Constructors
    public Board();



    // Public Methods
    public Piece get(String position) throws IllegalArgumentException {
        if (!Utils.isValidBoardPosition(position)){
            String exMsg = "Invalid position was encountered.";
            throw new IllegalArgumentException(exMsg);
        } else {
            Integer[] idx = translate(position);
            return pieces[idx[0]][idx[1]];
        }
    }

    public boolean put(String position) throws IllegalArgumentException {
        if (!Utils.isValidBoardPosition(position)){
            String exMsg = "Invalid position was encountered.";
            throw new IllegalArgumentException(exMsg);
        } else {
            Integer[] idx = translate(position);

        }
    }

    public Board fromFEN(String fen) throws IllegalArgumentException {
        if (!Utils.isValidFEN(fen)){
            String exMsg = "Invalid FEN was encountered.";
            throw new IllegalArgumentException(exMsg);
        } else {
            String validNums = "12345678";
            String validChars = "prnbkqPRNBKQ";
            String[] boardArray = fen.split(" ", 2)[0].split("/");
            for (int i = 0; i < 8; i++){
                String rank = boardArray[i];
                for (int j = 0; j < rank.length(); j++){
                    String c = rank.substring(j, j+1);
                    if (StringUtils.isAlpha(c)){

                    }
                }
            }
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
