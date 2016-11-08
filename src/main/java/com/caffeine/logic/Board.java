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



    // Static Methods
    public static Board fromFEN(String fen) throws IllegalArgumentException {
        if (!Utils.isValid(fen)){
            String exMsg = "Invalid FEN string was encountered.";
            throw new IllegalArgumentException(exMsg);
        } else {
            Piece[][] board = new Piece[8][8];
            String[] boardRowStrings = fen.split(" ", 2)[0].split("/");
        }
    }

    public Piece getPieceAt(String position) throws IllegalArgumentException {
        if (!Utils.isValidBoardPosition(position)){
            String exMsg = "Invalid position was encountered.";
            throw new IllegalArgumentException(exMsg);
        } else {
            char file;
            int rank;
            int col, row;
            file =
            rank =
        }
    }
}
