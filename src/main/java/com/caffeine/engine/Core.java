package com.caffeine.engine;

// First-Party Libs
import java.util.*;

// Third-Party Libs
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

// Local Libs
import com.jwarner.jockfish.JockfishEngine;

public class Core{

    private JockfishEngine jockfish;

    public Core(){
        jockfish = new JockfishEngine();
    }

    public void in(String command){
        // Wrapper around JockfishEngine.write()
        jockfish.write(command);
    }

    public String out(){
        // Wrapper around JockfishEngine.read()
        return jockfish.read();
    }

    // UCI Interface

    public void go(String type, String[] parameters){
        String cmd = String.format("go %s ", type);
        for (int i = 0; i < parameters.length; i++){ cmd.concat(parameters[i] + " "); }
        jockfish.write(cmd);
    }

    public void stop(){
        jockfish.write("stop");
    }

    public HashMap<String, String> getConfig(){
        HashMap<String, String> result = new HashMap<String, String>();
        jockfish.write("uci");
        String responseLine;
        while (true) {
            responseLine = jockfish.readLine().trim();
            if (responseLine.equals("uciok")) { break; }
            if (responseLine.contains("option name")){
                String[] items = responseLine.split(" ", 4);
                result.put(items[2], items[3]);
            }
        }
        return result;
    }

    public void setFEN(String fen){
        jockfish.write(String.format("position", fen));
    }

    private boolean isValid(String fen){
        /*  Assert that properties of the String are true, else return false.
            Properties can be found at:
              https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
        */
        String validOptions;

        //  FEN String cannot be blank.
        if (StringUtils.isBlank(fen)){ return false; }

        //  FEN String has exactly 6 fields separated by spaces.
        String[] fenFields = fen.trim().split(" ");
        if (fenFields.length != 6){ return false; }

        //  FEN Field 1 is Piece Placement from Rows 8 to 1 delimited by '/'.
        //    Player 1 (White)'s Pieces are within "PNBRQK",
        //    Player 2 (Black)'s Pieces are within "pnbrqk",
        //    Empty squares grouped as digits within [1,8] (incl.)
        validOptions = "pnbrqk";
        String[] boardState = fenFields[0].split("/");
        if (boardState.length != 8){ return false; }
        for (String str : boardState){
            int rowValue = 0; // Digits are their value. Letters are a 1.
            for (char ch : str.toLowerCase().toCharArray()){
                if (validOptions.contains(ch)){
                    rowValue += CharUtils.toIntValue(ch, 1);
                } else { return false; }
            }
            if (rowValue != 8){ return false; }
        }

        //  FEN Field 2: Active Color. Can only be "w" or "b".
        validOptions = "wb";
        String activePlayer = fenFields[1];
        if (activePlayer.length != 1){ return false; }
        if (!validOptions.contains(activePlayer)){ return false; }

        //  FEN Field 3: Castling Availability of Kings and Queens; "KQkq".
        validOptions = "KQkq";
        String castlingAbility = fenFields[2];
        // No more than 4 chars, e.g. "KQkq-" is invalid.
        if (castlingAbility.length > 4){ return false; }
        // If '-', nobody can castle and we can skip all this.
        if (!castlingAbility.equals("-")){
            int uniqueCount = 0;
            String uniqueChars;
            for (char ch : castlingAbility.toCharArray()){
                // Check all chars are valid.
                if (!validOptions.contains(ch)){ return false; }
                // Check all chars are unique. "KKkq" is invalid.
                if (!uniqueChars.contains(ch)){ uniqueChars = ch + uniqueChars; }
            }
            if (uniqueChars.length != castlingAbility.length){ return false; }
        }

        // FEN Field 4: En Passant, can be either '-' or a board location.
        String enPassantStatus = fenFields[3].toLowerCase();
        // If '-', no en-passant and we can skip all this.
        if (!enPassantStatus.equals("-")){ return false; }
        if (enPassantStatus.length() != 2){ return false; }
        char[] enPassantArray = enPassantStatus.toCharArray();
        // If not '-' it has to be a char in [a-h] followed by a digit in [1-8]
        validOptions = "abcdefgh";
        if (!validOptions.contains(enPassantArray[0])){ return false; }
        validOptions = "12345678";
        if (!validOptions.contains(enPassantArray[1])){ return false; }

        // FEN Field 5: Halfmove Clock (Ply Count since last pawn move or capture).
        String halfmoveClock = fenFields[4];
        // Must be non-negative integer.
        try {
            int halfmoveVal = Integer.parseInt(halfmoveClock);
            if (halfmoveVal < 0){ return false; }
        } catch (Exception e){ return false; } // If string isn't a number

        // FEN Field 6: Fullmove Clock (Full turns in the game).
        String fullmoveClock = fenFields[5];
        // Must be non-negative.
        try {
            int fullmoveVal = Integer.parseInt(fullmoveClock);
            if (fullmoveVal < 0){ return false; }
        } catch (Exception e){ return false; } // If string isn't a number

        // NOTE: Can't easily validate halfmoveVal and fullmoveVal in
        //       relation to one another because of the start/endgame
        //       edge cases.

        // If we're still here, we've most likely got a valid FEN String!
        return true;
    }
}
