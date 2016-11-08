package com.caffeine.logic;

import com.caffeine.logic.Board;
import com.caffeine.logic.Piece;

public class State {
    // TODO: Finish This Class


    // State Variables
    // Game details (See PGN for status and purpose)
    private HashMap<String, String> reqGameDetails; // See PGN -> Seven Tag Roster
    private HashMap<String, String> optGameDetails; // See PGN -> Supplemental Tag Names
    // Based on FEN String Fields, see PGN -> FEN Data Fields
    private Board board;
    private boolean isActiveColorWhite;
    private String castling;
    private String enPassant;
    private Integer halfMoves;
    private Integer fullMoves;
    // Misc
    private ArrayList<Piece> capturedByBlack; // List of pieces taken by Black
    private ArrayList<Piece> capturedByWhite; // List of pieces taken by White
    private Pieces[] blackPieces; // Record of all Black Pieces
    private Pieces[] whitePieces; // Record of all White Pieces



    // Constructors
    public State(){} // Don't bother with many constructors just yet.



    // Getters: Anything that reads State.
    public String getDetail(String detailName){ return ""; }

    public String getFEN(){ return ""; }

    public boolean isActiveColorWhite(){ return false; }

    public String getCastlers(){ return "-"; }

    public String getEnPassant(){ return "-"; }

    public Integer getHalfMoves(){ return 0; }

    public Integer getFullMoves(){ return 0; }



    // Setters: Anything that changes alters State. Returns success status.
    public boolean setDetail(String detailName){ return false; }

    public boolean setFEN(String fen){ return false; }

    public boolean setActiveColorWhite(boolean activeColorIsWhite){ return false; }

    public boolean setCastlers(String castlers){ return false; }

    public boolean setEnPassant(String enPassant){ return false; }

    public boolean setHalfMoves(Integer moves){ return false; }

    public boolean setFullMoves(Integer moves){ return false; }

}