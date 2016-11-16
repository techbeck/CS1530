package com.caffeine.logic;

public class Piece {

    // Constants
    // White pieces as letters
    public static final Character WHITE_PAWN = 'P';
    public static final Character WHITE_ROOK = 'R';
    public static final Character WHITE_KNIGHT = 'N';
    public static final Character WHITE_BISHOP = 'B';
    public static final Character WHITE_QUEEN = 'Q';
    public static final Character WHITE_KING = 'K';
    // Black pieces as letters
    public static final Character BLACK_PAWN = 'p';
    public static final Character BLACK_ROOK = 'r';
    public static final Character BLACK_KNIGHT = 'n';
    public static final Character BLACK_BISHOP = 'b';
    public static final Character BLACK_QUEEN = 'q';
    public static final Character BLACK_KING = 'k';
    // Unicode Symbols
    public static final String UNICODE_PAWN = "\u265F";
    public static final String UNICODE_ROOK = "\u265C";
    public static final String UNICODE_KING = "\u265A";
    public static final String UNICODE_QUEEN = "\u265B";
    public static final String UNICODE_BISHOP = "\u265D";
    public static final String UNICODE_KNIGHT = "\u265E";



    // Class Properties
    private Character type; // Defined as constants in Piece
    private String position; // This piece's position on the board.



    // Constructors
    /**
     * Initializes type, number, and side as passed in as parameters.
     *
     * @param type  The type of chess piece, defined as valid above
     * @param side  The side, eg. black or white
     */
    public Piece(Character type, String position) {
        this.type = type;       // eg. 'K', 'r', or "Piece.BLACK_QUEEN"
        this.position = position.toLowerCase();
    }



    // Public methods
    /**
     * Returns the type of the piece as a String.
     *
     * @return  The type, eg. 'K', 'r', or Piece.BLACK_QUEEN
     */
    public Character getType() { return type; }

    /**
     * Returns whether the piece is white or not.
     *
     * @return  true if white, otherwise false (aka black)
     */
    public boolean isWhite() { return Character.isUpperCase(type); }

    /**
     * Gets this piece's file.
     *
     * @return the char within [a-h] corresponding to the col on the board.
     */
    public char getFile() { return position.charAt(0); }

    /**
     * Gets this piece's rank.
     *
     * @return the int within [1-8] corresponding to the row on the board.
     */
    public int getRank() { return Integer.parseInt(position.substring(1,2)); }

    /**
     * Gets this piece's position as a 2-letter String, e.g. "e8"
     *
     * @return a String that maps to a valid position on a chess board.
     */
    public String getPosition(){ return position; }

    /**
     * Returns the Unicode character corresponding to this Piece type.
     *
     * @return A single unicode character as a String.
     */
    public String getUnicode() {
        Character typeLower = Character.toLowerCase(type);
        String unicode;
        switch(typeLower) {
            case 'p':
                unicode = UNICODE_PAWN;
                break;
            case 'r':
                unicode = UNICODE_ROOK;
                break;
            case 'n':
                unicode = UNICODE_KNIGHT;
                break;
            case 'b':
                unicode = UNICODE_BISHOP;
                break;
            case 'k':
                unicode = UNICODE_KING;
                break;
            case 'q':
                unicode = UNICODE_QUEEN;
                break;
            default:
                unicode = "?";
                break;
        }
        return unicode;
    }



    // Protected Methods
    /**
     * To be called when a piece is moved on the board.
     * Allows Piece to track location.
     *
     */
    protected void setPosition(String position) {
        if (position == null){ this.position = null; }
        if (Utils.isValidBoardPosition(position)){
            this.position = position.toLowerCase();
        }
    }

}
