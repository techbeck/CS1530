package com.caffeine.logic;

//  First-Party Imports
import com.caffeine.logic.Game;

//  Third-Party Imports
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

//  Local Imports

/*  A library of public static functions that we find helpful and don't want
    to store in multiple places across the product. */

public class Utils {

    /**
     *  Parses through a FEN string and checks whether
     *  it is UCI compliant.
     *
     *  Proper notation for a FEN string can be found here:
     *  https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
     *
     *  @param  fen The string to be validated
     *  @return true if valid, or false if invalid
     */
    public static boolean isValidFEN(String fen){

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

        /**
         *  FEN Field 1 is Piece Placement from Rows 8 to 1 delimited by '/'.
         *
         *  Player 1 (White)'s Pieces are within "PNBRQK",
         *  Player 2 (Black)'s Pieces are within "pnbrqk",
         *  Empty squares grouped as digits within [1,8] (incl.)
         */
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
        boolean passantValidPos = isValidBoardPosition(enPassantStatus);
        boolean passantNonPos = enPassantStatus.equals("-");

        if ( !passantValidPos && !passantNonPos ){ return false; }


        // FEN Field 5:
        // Halfmove Clock (number of halfmoves since last capture or pawn move).
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


        /**
         *  NOTE: Can't easily validate halfmoveVal and fullmoveVal in
         *  relation to one another because of the start/endgame
         *  edge cases.
         *  If we're still here, we've most likely got a valid FEN String!
         */

        return true;
    }

    /**
     *  Checks whether a given board position has proper X (rank)
     *  and Y (file) coordinates.
     *
     *  @param  position The given coordinate as a String
     *  @return true if the given coordinate exists on a board, false otherwise
     */
    public static boolean isValidBoardPosition(String position){

        String sanitizedPosition = position.toLowerCase().trim();
        String validRanks = "12345678";
        String validFiles = "abcdefgh";
        if (sanitizedPosition.length() != 2){ return false; }
        if (!validRanks.contains(sanitizedPosition.substring(1,2))){ return false; }
        if (!validFiles.contains(sanitizedPosition.substring(0,1))){ return false; }
        return true;
    }

    /**
     *  Checks whether a piece's movement both starts and ends
     *  on valid positions.
     *
     *  @param  move A piece's move as a String
     *  @return true if the given move starts and ends on valid positions, false otherwise
     */
    public static boolean isValidMove(String move){

        boolean move1, move2;
        if (move.length() != 4){ return false; }
        move1 = isValidBoardPosition(move.substring(0,2));
        move2 = isValidBoardPosition(move.substring(2,4));
        return (move1 && move2);
    }

    /**
     *  Translates Board position to Integer pair, e.g. "A3" to {2,0}.
     *  RANK AND FILE ARE REVERSED BETWEEN POSITION AND INT ARRAY.
     *
     * @param position String of length 2 marking File, then Rank. e.g. "e2".
     * @return int array of length 2 marking Rank and File. null if invalid
     */
    public static Integer[] translate(String position){
        if (!isValidBoardPosition(position)){ return null; }
        return new Integer[]{
            Integer.parseInt(position.substring(1,2)) - 1,
            (int) Character.toLowerCase(position.charAt(0)) - 97
        };
    }

    /**
     * Translates Integer pair to Board position, e.g. {3,6} to "F4".
     * RANK AND FILE ARE REVERSED BETWEEN POSITION AND INT ARRAY.
     *
     * @param  index int array of length 2 marking Rank and File
     * @return String of length 2 marking File, then Rank. null if invalid
     */
    public static String translate(Integer[] index){
        if (index.length != 2){ return null; }
        if (index[0] < 0 || index[0] > 7){ return null; }
        if (index[1] < 0 || index[1] > 7){ return null; }
        return new String(
            new String(Character.toString((char)(index[1] + 97))) +
            Integer.toString(index[0] + 1)
        );
    }

    /**
     * Translates Two integers (Rank and then File) to a Board position.
     *
     * @param   rank int within [0-7] (incl) marking a ROW on a Chess board.
     * @param   file int within [0-7] (incl) marking a COL on a Chess board.
     * @return  String of length 2 marking File, then Rank.
     */
    public static String translate(Integer rank, Integer file){
        if (rank < 0 || rank > 7){ return null; }
        if (file < 0 || file > 7){ return null; }
        return new String(
            new String(Character.toString((char)(file + 97))) +
            Integer.toString(rank + 1)
        );
    }

    /**
     * Converts from char type KQRBNPkqrbnp to equivalent Unicode characters
     *
     * @param type  the type to be converted
     * @return unicode character for chess piece. null if no matching piece
     */
    public static String typeToUnicode(char type) {
        switch(type) {
        case 'K':
        case 'k':
            return Game.king;
        case 'Q':
        case 'q':
            return Game.queen;
        case 'R':
        case 'r':
            return Game.rook;
        case 'B':
        case 'b':
            return Game.bishop;
        case 'N':
        case 'n':
            return Game.knight;
        case 'P':
        case 'p':
            return Game.pawn;
        default:
            return "null";
        }
    }

    /**
     * Converts from type KQRBNPkqrbnp to equivalent side black/white
     *
     * @param type  the type to be converted
     * @return color-swapped character (white to black, or vice-versa)
     */
    public static String typeToSide(char type) {
        if (((int) type) < 90) return "white";
        else return "black";
    }

}
