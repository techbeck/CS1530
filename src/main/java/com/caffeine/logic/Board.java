package com.caffeine.logic;

import java.util.Arrays;
import java.util.ArrayList;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import com.caffeine.logic.Piece;
import com.caffeine.logic.Utils;

public class Board {

    // Board Represented by an 8x8 Piece array.
    private Piece[][] pieces = new Piece[8][8];



    // Constructors
    public Board(Piece[][] pieceArray){ pieces = pieceArray; }

    public Board(){ this(Board.getEmptyBoard()); }

    public Board(String fenString){ this(pieceArrayFromFEN(fenString)); }



    // Public Methods
    public Piece getPiece(String position) throws IllegalArgumentException {
        if (!Utils.isValidBoardPosition(position)){
            String exMsg = "Invalid position was encountered.";
            throw new IllegalArgumentException(exMsg);
        } else {
            Integer[] idx = Utils.translateCoordinate(position);
            return pieces[idx[0]][idx[1]];
        }
    }

    public boolean rmPiece(String position){
        // An alias around putPiece with a null.
        return putPiece(position, null);
    }

    public boolean putPiece(String position, Piece piece) throws IllegalArgumentException {
        if (!Utils.isValidBoardPosition(position)){
            String exMsg = "Invalid position was encountered.";
            throw new IllegalArgumentException(exMsg);
        } else {
            Integer[] idx = Utils.translateCoordinate(position);
            pieces[idx[0]][idx[1]] = piece;
            return true;
        }
    }

    public Piece[][] pieceArrayFromFEN(String fen) throws IllegalArgumentException {
        if (!Utils.isValidFEN(fen)){

            String exMsg = "Invalid FEN was encountered.";
            throw new IllegalArgumentException(exMsg);

        } else {

            // Default: Board squares w/ no piece are null
            Piece[][] pieces = getEmptyBoard();

            // Get board as String[8] where [0] is row 8 and [7] is row 1.
            String[] board = fen.split(" ", 2)[0].split("/");
            // The current line examined in the for loop, and its length
            String currentLine;
            int currentLineLen;
            // The current character in the currentLine String AND its num val.
            Character currentChar;
            Integer charVal;
            // The progress through the current board row
            int rowCursor;
            for (int i = 0; i < 8; i++){ // Iterate over rows
                currentLine = board[i];
                currentLineLen = currentLine.length();
                rowCursor = 0;
                for (int j = 0; i < currentLineLen; j++){ // Iterate over currentLine
                    currentChar = currentLine.charAt(j); // Current character.
                    charVal = CharUtils.toIntValue(currentChar, 0); // Numeric value of current character
                    if (charVal == 0){
                        // Char was a piece descriptor
                        pieces[i][rowCursor] = new Piece(currentChar, Utils.translateCoordinate(new Integer[]{i, rowCursor}));
                    } else {
                        // Move cursor that many places ahead.
                        // All the pieces in between are already null.
                        rowCursor += charVal;
                    }
                }
            }
        }

        return pieces;
    }

    public String toFENPartial(){
        // Creates a String of the format 8/7/6/5/4/3/2/1 where:
        //     - Each '/'' separates a 'row' String meeting the criteria below.
        //     - The digit in the above corresponds to that row on a ches board.
        //     - Each row is composed only of the groups [PRNBQKprnbqk], and [1-8].
        //     - The length of a row cannot exceed 8 chars.
        //     - For each row,
        //          - Assign pt value '1' to character matching in [PRNBQKprnbqk].
        //          - Assign pt value equal to any digit in [1-8].
        //          - The sum of all pt values cannot exceed 8.
        //          - e.g. "2QP3r" = 2 + 1 + 1 + 3 + 1 = 8.
        String result;
        String sep = "/";
        Piece currentPiece;
        int lineCursor;

        result = "";
        for (int i = 0; i < 8; i++){ // Iterate over board's rows
            for (int j = 0; j < 8; ){ // Iterate over board's columns
                do {
                    currentPiece = getPiece(Utils.translateCoordinate(new Integer[]{i, j}));
                    lineCursor++;
                } while(currentPiece == null);
                if (lineCursor > 1){ result += Integer.toString(lineCursor); }
                result += currentPiece.getClass().toString();
                j += lineCursor;
            }
            result += sep;
        }
        result = StringUtils.chop(result); // Remove trailing '/'
    }

    public String toString(){
        // Creates a multi-line string depicting the board in ASCII.
        // Same as how Stockfish generates it.
        String result;
        String sep = " +---+---+---+---+---+---+---+---+";
        String pieceTemplate = " %c |";
        String currentRowString;

        result = "";
        for (int i = 0; i < 8; i++){ // Iterate over rows
            // Build the row's string, append it to the board.
            currentRowString = "";
            for (int j = 0; j < 8; j++){ // Iterate over columns
                Piece pc = getPiece(Utils.translateCoordinate(new Integer[]{i, j}));
                currentRowString += String.format(" %c |", pc.getType());
            }
            result += String.format("%s\n | %s\n", currentRowString);
        }
        result += sep;
        return result;
    }



    // Private Methods
    private static Piece[][] getEmptyBoard(){
        Piece[][] emptyBoard = new Piece[8][8];
        for (int i = 0; i < 8; i++){ Arrays.fill(emptyBoard[i], null); }
        return emptyBoard;
    }
}
