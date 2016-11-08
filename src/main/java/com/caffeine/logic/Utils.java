package com.caffeine.logic;

//First-Party Imports

// Third-Party Imports
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

// Local Imports

/* A library of public static functions that we find helpful and don't want
   to store in multiple places across the product.
*/
public class Utils {

    public static boolean isValidFEN(String fen){
        /*  Assert that properties of the String are true, else return false.
            Properties can be found at:
              https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
        */
        String validOptions;

        //  FEN String cannot be blank.
        if (StringUtils.isBlank(fen)){
            return false;
        }

        //  FEN String has exactly 6 fields separated by spaces.
        String[] fenFields = fen.trim().split(" ");
        if (fenFields.length != 6){
            return false;
        }

        //  FEN Field 1 is Piece Placement from Rows 8 to 1 delimited by '/'.
        //    Player 1 (White)'s Pieces are within "PNBRQK",
        //    Player 2 (Black)'s Pieces are within "pnbrqk",
        //    Empty squares grouped as digits within [1,8] (incl.)
        validOptions = "pnbrqk12345678";
        String[] boardState = fenFields[0].split("/");
        if (boardState.length != 8){
            return false;
        }
        for (String str : boardState){
            int rowValue = 0; // Digits are their value. Letters are a 1.
            for (char ch : str.toLowerCase().toCharArray()){
                if (validOptions.contains(new String(""+ch))){
                    rowValue += CharUtils.toIntValue(ch, 1);
                } else {
                    return false;
                }
            }
            if (rowValue != 8){
                return false;
            }
        }

        //  FEN Field 2: Active Color. Can only be "w" or "b".
        validOptions = "wb";
        String activePlayer = fenFields[1];
        if (activePlayer.length() != 1){
            return false;
        }
        if (!validOptions.contains(activePlayer)){
            return false;
        }

        //  FEN Field 3: Castling Availability of Kings and Queens; "KQkq".
        validOptions = "KQkq";
        String castlingAbility = fenFields[2];
        // No more than 4 chars, e.g. "KQkq-" is invalid.
        if (castlingAbility.length() > 4){
            return false;
        }
        // If '-', nobody can castle and we can skip all this.
        if (!castlingAbility.equals("-")){
            int uniqueCount = 0;
            String uniqueChars = "";
            for (char ch : castlingAbility.toCharArray()){
                // Check all chars are valid.
                if (!validOptions.contains(new String(""+ch))){
                    return false;
                }
                // Check all chars are unique. "KKkq" is invalid.
                if (!uniqueChars.contains(new String(""+ch))){ uniqueChars = ch + uniqueChars; }
            }
            if (uniqueChars.length() != castlingAbility.length()){
                return false;
            }
        }

        // FEN Field 4: En Passant, can be either '-' or a board location.
        String enPassantStatus = fenFields[3].toLowerCase();
        // If '-', no en-passant and we can skip all this.
        if (!enPassantStatus.equals("-")){
            if (enPassantStatus.length() > 0 && enPassantStatus.length() <= 2){
                return false;
            }
            char[] enPassantArray = enPassantStatus.toCharArray();
            // If not '-' it has to be a char in [a-h] followed by a digit in [1-8]
            validOptions = "abcdefgh";
            if (!validOptions.contains(new String(""+enPassantArray[0]))){
                return false;
            }
            validOptions = "12345678";
            if (!validOptions.contains(new String(""+enPassantArray[1]))){
                return false;
            }
        }

        // FEN Field 5: Halfmove Clock (Ply Count since last pawn move or capture).
        String halfmoveClock = fenFields[4];
        // Must be non-negative integer.
        try {
            int halfmoveVal = Integer.parseInt(halfmoveClock);
            if (halfmoveVal < 0){
                return false;
            }
        } catch (Exception e){
            return false;
        } // If string isn't a number


        // FEN Field 6: Fullmove Clock (Full turns in the game).
        String fullmoveClock = fenFields[5];
        // Must be non-negative.
        try {
            int fullmoveVal = Integer.parseInt(fullmoveClock);
            if (fullmoveVal < 0){
                return false;
            }
        } catch (Exception e){
            return false;
        } // If string isn't a number


        // NOTE: Can't easily validate halfmoveVal and fullmoveVal in
        //       relation to one another because of the start/endgame
        //       edge cases.

        // If we're still here, we've most likely got a valid FEN String!
        return true;
    }

    public static boolean isValidBoardPosition(String position){
        // The position falls within the rank and file of a chess board.
        String sanitizedPosition = position.toLowerCase().trim();
        String validRanks = "12345678";
        String validFiles = "abcdefgh";
        if (sanitizedPosition.length() != 2){ return false; }
        if (!validRanks.contains(sanitizedPosition.substring(1,2))){ return false; }
        if (!validFiles.contains(sanitizedPosition.substring(0,1))){ return false; }
        return true;
    }

    public static boolean isValidMove(String move){
        // Both positions in the move fall within the rank/file of the board.
        boolean move1, move2;
        if (move.length() != 4){ return false; }
        move1 = isValidBoardPosition(move.substring(0,2));
        move2 = isValidBoardPosition(move.substring(2,4));
        return (move1 && move2);
    }
}
