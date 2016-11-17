package com.caffeine.logic;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import com.caffeine.logic.Board;
import com.caffeine.logic.Piece;

public class State {

    /* A collection of variables and data generally unfettered by methods. */

    // State Variables
    // Game Tags (See PGN for status and purpose)
    private HashMap<String, String> reqGameTags; // See PGN -> Seven Tag Roster
    private HashMap<String, String> optGameTags; // See PGN -> Supplemental Tag Names

    // Based on FEN String Fields, see PGN -> FEN Data Fields
    private Board board;       // See com.caffeine.logic.Board
    private Character activeColor;  // 'w' or 'b'
    private String castlers;   // At most 4 unique chars in [KQkq]
    private String enPassant;  // A board position
    private Integer halfMoves; // Positive non-zero int. Mind the 50-move rule.
    private Integer fullMoves; // Positive non-zero int. Number of rounds so far.

    // Supplemental PGN Data
    private ArrayList<Piece> capturedByBlack; // Collection of 'w' Pieces taken
    private ArrayList<Piece> capturedByWhite; // Collection of 'b' Pieces taken
    private Character userColor; // 'w' or 'b'



    // Constructors
    public State(
        ArrayList<String> tags,
        ArrayList<String> moveHistory
    ){
        // Process EVERY TAG
        for (String tag : tags){

            String key = parseTag(tag)[0];
            String val = parseTag(tag)[1];

            switch (key){
                // Mandatory Seven-Tag Roster
                case "Event":
                    reqGameTags.put("Event", val);
                    break;
                case "Site":
                    reqGameTags.put("Site", val);
                    break;
                case "Date":
                    reqGameTags.put("Date", val);
                    break;
                case "Round":
                    reqGameTags.put("Round", val);
                    break;
                case "White":
                    reqGameTags.put("White", val);
                    break;
                case "Black":
                    reqGameTags.put("Black", val);
                    break;
                case "Result":
                    reqGameTags.put("Result", val);
                    break;


                // Special Cases (things LABOON CHESS cares about)
                case "CapturedByBlack":
                    capturedByBlack = new ArrayList<Piece>();
                    for (Character c : val.toCharArray()){ capturedByBlack.add(new Piece(c)); }
                    break;
                case "CapturedByWhite":
                    capturedByWhite = new ArrayList<Piece>();
                    for (Character c : val.toCharArray()){ capturedByWhite.add(new Piece(c)); }
                    break;
                case "UserColor":
                    userColor = ( StringUtils.isBlank(val) ? 'w' : val );
                    break;

                // FEN SetUp
                case "SetUp":
                    // TODO
                    break;
                case "FEN":
                    // TODO
                    break;

                // All other tags whether or not we care.
                default:
                    optGameTags.put(key, val);
                    break;
            }

        }
    }



    // Getters: Anything that reads State.
    public String getTag(String tagName){ return ""; }

    public Board getBoard(){ return board; }

    public String getFEN(){
        // TODO
        return "";
    }

    public boolean activeColorWhite(){ return (activeColor.equals('w')); }

    public String getCastlers(){ return castlers; }

    public String getEnPassant(){ return enPassant; }

    public Integer getHalfMoves(){ return halfMoves; }

    public Integer getFullMoves(){ return fullMoves; }

    public ArrayList<Piece> getCapturedByBlack(){ return capturedByBlack; }

    public ArrayList<Piece> getCapturedByWhite(){ return capturedByWhite; }

    public Character getUserColor(){ return userColor; }


    // Setters: Anything that changes alters State. Returns success status.
    public boolean setTag(String TagName){
        // PGN allows for arbitrary information to be stored. We'll do this
        // by having a hashmap of MANDATORY tags (see spec) and a hashmap
        // of ARBITRARY tags, wherein we'll store any data OUR project needs
        // even if it's not normally in the standard PGN spec.

        // TODO
        return false;
    }

    public boolean setWhiteActive(){
        activeColor = 'w';
        return true;
    }

    public boolean setBlackActive(){
        activeColor = 'b';
        return true;
    }

    public boolean setCastlers(String newCastlers){
        // Early Exit
        if (newCastlers.equals("-")){ castlers = newCastlers; return true; }
        // Property-based Validation
        if (castlers.length() > 4){ return false; }
        String validChars = "KQkq";
        String wipString = "";
        for (char c : castlers.toCharArray()){
            if (!validChars.contains(Character.toString(c))){ return false; }
            if ( validChars.contains(Character.toString(c))){ return false; }
            // Has to be a character in [KQkq] unique to wipString; Add it!
            wipString += Character.toString(c);
        }
        castlers = newCastlers;
        return true;
    }

    public boolean setEnPassant(String enPassant){
        // Property-based Validation
        //     I'm not entirely sure what properties this SHOULD have...
        if (!Utils.isValidBoardPosition(enPassant)){ return false; }
        return true;
    }

    public boolean setHalfMoves(Integer moves){
        if (moves < 0){ return false; }
        halfMoves = moves;
        return true;
    }

    public boolean setFullMoves(Integer moves){
        if (moves < 1){ return false; }
        fullMoves = moves;
        return true;
    }

    public boolean capturePieceAt(String position){
        // Gets non-null piece, updates piece AND board, and registers as captured.
        Piece piece;

        if (!Utils.isValidBoardPosition(position)){ return false; }
        piece = board.getPiece(position);
        if (piece == null){ return false; }
        board.rmPiece(position);
        piece.setPosition(null);
        if (piece.isWhite()){
            capturedByBlack.add(piece);
        } else {
            capturedByWhite.add(piece);
        }
        return true;
    }

    public boolean setUserColor(Character color){
        String validChars = "wb";
        if (!validChars.contains(color.toString())){ return false; }
        userColor = color;
        return true;
    }



    // Private Methods
    private String[] parseTag(String tag){
        if (StringUtils.isBlank(tag)){ return null; }
        rawTag = tag.substring(1, tag.length()-1); // Remove brackets
        String[] keyValPair = rawTag.split(" ", 2);
        keyValPair[1] = keyValPair.substring(1, val.length()-1); // Remove quotes
        return keyValPair;
    }



// ============================================================================
//      GUI-Wrapped Functions (For State package-level access)
// ============================================================================

    public boolean move(String move){
        String src, dst;
        Piece piece;

        src = Utils.split(move, 2)[0];
        dst = Utils.split(move, 2)[1];
        capturePieceAt(dst); // If null, does nothing.
        piece = board.getPiece(src);
        // Update Piece and Board location data
        board.rmPiece(src);
        piece.setPosition(dst);
        // Commit the change.
        board.putPiece(dst, piece);

        return true;
    }

    public String getPiecesCapturedByBlack(){
        String result = "";
        for (Piece p : capturedByBlack){ result += p.getUnicode(); }
        return result;
    }

    public String getPiecesCapturedByWhite(){
        String result = "";
        for (Piece p : capturedByWhite){ result += p.getUnicode(); }
        return result;
    }

    public String getBoardAsString(){
        String fenPartial = board.toFENPartial();
        String result = "";

        // Replace all num digits in FEN with a '-' String of that length.
        int multiple;
        for (char c : fenPartial.toCharArray()){
            multiple = CharUtils.toIntValue(c, 0);
            if (multiple > 0){
                result += StringUtils.repeat('-', multiple);
            }
            else {
                result += StringUtils.repeat(c, 1);
            }
        }

        return result;
    }

}
