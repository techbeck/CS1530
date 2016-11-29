package com.caffeine.logic;

import com.caffeine.Chess;
import com.caffeine.logic.Utils;
import com.caffeine.view.Core;
import com.caffeine.view.ViewUtils;

import java.util.*;

public class Game {
public Piece[] pieces = new Piece[32];
    public ArrayList<String> moveHistory = new ArrayList<String>();
    protected ArrayList<String> fenHistory = new ArrayList<String>();
    protected ArrayList<String> dupHistory = new ArrayList<String>();

    public boolean gameStarted = false;
    public int gameResult = 0;  // 0 = ongoing
                                // 1 = white won
                                // 2 = black won
                                // 3 = stalemate
                                // 4 = draw

	private int mode = 0; // 0 = easy, 1 = medium, 2 = hard
	private static final int[] timeoutsForModes = {5, 100, 200};

	private static final String startFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    protected HashMap<String,String> pgnTags;

	protected boolean whiteActive;
	protected boolean userWhite;
	protected String captByBlack;
	protected String captByWhite;
    protected boolean threeMoveDraw = false;

    // used for en passant checking and undoing moves
    protected String prevFEN = null;
	protected String lastFEN = null;
	protected String currFEN = startFEN;

	protected String enPassantLoc = "-";
	protected Piece enPassantPiece = null;

	// 	Unicode chess pieces
	protected static final String king = "\u265A";
	protected static final String queen = "\u265B";
	protected static final String rook = "\u265C";
	protected static final String bishop = "\u265D";
	protected static final String knight = "\u265E";
	protected static final String pawn = "\u265F";

    public Game() {
        whiteActive = true;
        userWhite = true;
        captByBlack = "";
        captByWhite = "";
        initializePGN();
        Chess.engine.setFEN(startFEN);
    }

    /**
     * Initialize PGN tags
     */
    public void initializePGN() {
        pgnTags = new HashMap<String,String>();
        pgnTags.put("Event", "CS1530");
        pgnTags.put("Site", "Pittsburgh, PA, USA");
        pgnTags.put("Date", "Fall 2016");
        pgnTags.put("Round", "420");
        pgnTags.put("FEN", currFEN);
    }

    /**
     * Starts the game.
     */
    public void startGame() {
        gameStarted = true;
        initializesPieces();
        ViewUtils.refreshBoard();
    }


	/**
	 * 	Sets the user as either white or black
	 *
	 *  @param side The color the user will play as
	 */
	public void setSide(String side) {
		if (side.equals("white") || side.equals("White")) {
			userWhite = true;
			pgnTags.put("White", "User");
			pgnTags.put("Black", "CPU");
		}
		else {
			userWhite = false;
			pgnTags.put("White", "CPU");
			pgnTags.put("Black", "User");
		}
	}

	/**
	 * Sets the mode based on the integer passed in.
	 * Default is easy.
	 *
	 * @param mode  The integer to determine which mode to set.
	 */
     public void setMode(int mode){
         if (mode >= 0 || mode <= 2){ this.mode = mode; }
     }

    /**
	 * Sets the mode based on the string passed in.
	 * Default is easy.
	 *
	 * @param mode  The string to determine which mode to set.
	 */
    public void setMode(String mode) {
        if (mode.equals("hard") || mode.equals("Hard")) setMode(2);
        else if (mode.equals("medium") || mode.equals("Medium")) setMode(1);
        else setMode(0);
    }

    /**
     * Returns the mode of the CPU opponent
     * 
     * @return  A string representing the mode: easy, medium, or hard
     */
    public String getMode() {
    	if (mode == 2) return "Hard";
    	else if (mode == 1) return "Medium";
    	else return "Easy";
    }

	/**
	 * 	Getter for whether the user is playing as white or black
	 *
	 *  @return true if the user is playing as white, false if black
	 */
	public boolean userWhite() {
		return userWhite;
	}

	/**
	 * Add the piece passed in to one of the captured strings.
	 *
	 * @param taken  The piece taken.
	 */
	public void takePiece(Piece taken) {
		taken.moveTo(-1,-1); // indicates piece has been taken
		if (taken.isWhite())
			captureWhitePiece(taken.getType());
		else
			captureBlackPiece(taken.getType());
	}

	/**
	 * 	Adds a piece to the list of black pieces taken
	 *
	 *	@param piece The newly taken black piece
	 */
	public void captureBlackPiece(String piece) {
		captByWhite = captByWhite.concat(" " + piece);
		Core.takenPanel.setCaptByWhite(captByWhite);
	}

	/**
	 * 	Adds a piece to the list of white pieces taken
	 *
	 * 	@param piece The newly taken white piece
	 */
	public void captureWhitePiece(String piece) {
		captByBlack = captByBlack.concat(" " + piece);
		Core.takenPanel.setCaptByBlack(captByBlack);
	}

	/**
	 * 	Getter for the current list of pieces taken by black
	 * @return list of captured white pieces as a String
	 */
	public String getCaptByBlack() {
		return captByBlack;
	}

	/**
	 * 	Getter for the current list of pieces taken by white
	 * @return list of captured black pieces as a String
	 */
	public String getCaptByWhite() {
		return captByWhite;
	}

	/**
	 * 	Adds a move to the move history panel
	 *
	 * 	@param currMove  The newly made move
	 */
	public void addToMoveHistory(String currMove) {
		moveHistory.add(currMove);
		Core.historyPanel.updateMoveHistory(moveHistory);
	}

    /**
     * Ends the game
     */
    public void endGame(int result) {
        boolean timerEnded = Core.timerPanel.isTimeOut();
        if (timerEnded) {
            if (userWhite) {
                gameResult = 2;
            } else {
                gameResult = 1;
            }
        } else {
            gameResult = result;
        }
        gameStarted = false;
        ViewUtils.endGame(gameResult);
    }

    /**
     *  Move a piece from one set of coordinates to another
     *  @param  oldRank The current horizontal coordinate
     *  @param  oldFile The current vertical coordinate
     *  @param  newRank The new horizontal coordinate to move to
     *  @param  newFile The new vertical coordinate to move to
     *  @return true if move is successful, false otherwise
     */
    public boolean move(int oldRank, int oldFile, int newRank, int newFile) {
        String oldLoc = (char)(oldFile+97) + "" + (oldRank+1);
        String newLoc = (char)(newFile+97) + "" + (newRank+1);

        if (Chess.engine.move(oldLoc+newLoc)) {
            boolean pieceTaken = doMove(oldRank, oldFile, newRank, newFile);

            return true;
        }
        return false;
    }

    /**
     *  Move a piece as decided by the engine and return the move made.
     *
     *  @return The move made if successful. null otherwise.
     */
    public String cpuMove() {
        int timeout = timeoutsForModes[mode];
        String move = Chess.engine.cpuMove(timeout);
        if (move.equals("(none)")) return null;
        char[] moveData = move.toCharArray();
        int oldRank = (int) moveData[1] - '1';
        int oldFile = (int) moveData[0] - 'a';
        int newRank = (int) moveData[3] - '1';
        int newFile = (int) moveData[2] - 'a';
        boolean pieceTaken = doMove(oldRank, oldFile, newRank, newFile);

        return move;
    }

    /**
     * Do the move functionality: taking pieces, en passant checking, setting pieces array
     *  @param  oldRank The current horizontal coordinate
     *  @param  oldFile The current vertical coordinate
     *  @param  newRank The new horizontal coordinate to move to
     *  @param  newFile The new vertical coordinate to move to
     *  @return true if move takes a piece, false otherwise
     */
    public boolean doMove(int oldRank, int oldFile, int newRank, int newFile) {
        boolean pieceTaken = false;

        Piece taken = getPieceMatching(newRank,newFile);
        Piece moving = getPieceMatching(oldRank, oldFile);

        if (taken != null) {
            takePiece(taken);
            pieceTaken = true;
        }

        prevFEN = lastFEN;
        lastFEN = currFEN;
        currFEN = Chess.engine.getFEN();
        pgnTags.put("FEN", currFEN);

        updateThreeMoveDraw(currFEN);
        // check for en passant and taking of en passant
        if (!enPassantLoc.equals("-")) {
            if (moving.getType().equals(pawn)) {
                int enPassantRank = (int) enPassantLoc.charAt(1) - '1';
                int enPassantFile = (int) enPassantLoc.charAt(0) - 'a';
                if (newRank == enPassantRank && newFile == enPassantFile) {
                    pieceTaken = true;
                    if (moving.isWhite()) {
                        takePiece(getPieceMatching(newRank-1,newFile));
                    } else {
                        takePiece(getPieceMatching(newRank+1,newFile));
                    }
                }
            }
        }
        enPassantLoc = currFEN.split(" ")[3];

        String oldLoc = (char)(oldFile+97) + "" + (oldRank+1);
        String newLoc = (char)(newFile+97) + "" + (newRank+1);
        boolean kingside = false;
        boolean queenside = false;
        if (lastFEN != null && !currFEN.split(" ")[2].equals(lastFEN.split(" ")[2])) {
            if (oldLoc.equals("e1") || oldLoc.equals("e8")) {
                if (newLoc.equals("g1") || newLoc.equals("g8")) {
                    kingside = true;
                }
                if (newLoc.equals("c1") || newLoc.equals("c8")) {
                    queenside = true;
                }
            }
        }
        if (kingside) {
            addToMoveHistory("O-O");
        } else if (queenside) {
            addToMoveHistory("O-O-O");
        } else {
            if (pieceTaken) {
                addToMoveHistory(oldLoc + "x" + newLoc);
            } else {
                addToMoveHistory(oldLoc+newLoc);
            }
        }

        setPiecesFromFEN(currFEN);

        whiteActive = !whiteActive;

        return pieceTaken;
    }



    /**
     *  Tells whether or not a move is possible.
     */
    public boolean tryMove(String newMove){
        if (!Utils.isValidMove(newMove)){ return false; }
        String oldFEN, newFEN;
        oldFEN = Chess.engine.getFEN();
        Chess.engine.move(newMove);
        newFEN = Chess.engine.getFEN();
        Chess.engine.setFEN(oldFEN);
        return (!oldFEN.equals(newFEN));
    }

    /**
     *  Tells whether or not a move is possible. Convenient alias w/ two
     *  positions on a board.
     */
    public boolean tryMove(String from, String to){
        if (from == null || to == null){ return false; }
        return tryMove(from + to);
    }

    /**
     *  Tells whether or not a move is possible. Convenient alias
     */
    public boolean tryMove(int oldRank, int oldFile, int newRank, int newFile){
        String oldPos = Utils.translate(oldRank, oldFile);
        String newPos = Utils.translate(newRank, newFile);
        return tryMove(oldPos, newPos);
    }

    /**
     *  Gets the piece at a given position
     *  @param  rank    The given x coordinate
     *  @param  file    The given y coordinate
     *  @return a Piece if a Piece exists at the given position, null if not
     */
    public Piece getPieceMatching(int rank, int file) {
        for (Piece p : pieces) {
            if (p != null && p.getRank() == rank) {
                if (p.getFile() == file) {
                    return p;
                }
            }
        }
        return null;
    }

    /**
     *  Resets the game to the default settings
     */
    public void reset() {
        whiteActive = true;
        userWhite = true;
        captByBlack = "";
        captByWhite = "";
        initializesPieces();
    }

    /**
     *  Populates the pieces array with the standard 32 chess pieces
     */
    public void initializesPieces() {
        setPiecesFromFEN(startFEN);
    }

    /**
     * Sets pieces array based on data from FEN string passed in
     *
     * @param fen  The FEN string that determines piece placement
     */
    public void setPiecesFromFEN(String fen) {

        // new array for pieces
        Piece[] pieces = new Piece[32];
        // Get board as String[8] where [0] is row 8 and [7] is row 1.
        String[] board = fen.split(" ", 2)[0].split("/");
        // The current line examined in the for loop, and its length
        String currentLine;
        // The current character in the currentLine String AND its num val.
        char currentChar;
        int charVal;
        // The progress through the current board row
        int rowCursor;
        // Overall progress through pieces
        int pieceInd = 0;
        for (int i = 0; i < 8; i++) {
            currentLine = board[i];
            rowCursor = 0;
            for (int j = 0; j < currentLine.length(); j++) {
                currentChar = currentLine.charAt(j);
                charVal = (int) currentChar - '0';
                if (charVal > 9) { // piece character
                    if (rowCursor == 0) {
                        pieces[pieceInd] = new Piece(typeToUnicode(currentChar),
                                                typeToSide(currentChar),7-i,j);
                    } else {
                        pieces[pieceInd] = new Piece(typeToUnicode(currentChar),
                                                typeToSide(currentChar),7-i,rowCursor);
                    }
                    pieceInd++;
                    rowCursor++;
                } else { // number of empty squares
                    rowCursor += charVal;
                }
            }
        }
        for (int i = pieceInd; i < 32; i++) {
            // taken pieces no longer in array, these are placeholders
            pieces[pieceInd] = new Piece("null","null",-1,-1);
        }
        this.pieces = pieces;
    }

    /**
     * Converts from type KQRBNPkqrbnp to equivalent Unicode characters
     *
     * @param type  the type to be converted
     */
    public String typeToUnicode(char type) {
        switch(type) {
        case 'K':
        case 'k':
            return king;
        case 'Q':
        case 'q':
            return queen;
        case 'R':
        case 'r':
            return rook;
        case 'B':
        case 'b':
            return bishop;
        case 'N':
        case 'n':
            return knight;
        case 'P':
        case 'p':
            return pawn;
        default:
            return "null";
        }
    }

    /**
     * Converts from type KQRBNPkqrbnp to equivalent side black/white
     *
     * @param type  the type to be converted
     */
    public String typeToSide(char type) {
        if (((int) type) < 90) return "white";
        else return "black";
    }

    /**
     * Loads the game state from the passed in FEN string.
     * Used for loading from a file.
     *
     * @param fen  The fen string to load.
     */
    public void loadFEN(String fen) {
        setPiecesFromFEN(fen);
        ViewUtils.refreshBoard();
        enPassantLoc = fen.split(" ")[3];
        Chess.engine.setFEN(fen);
        currFEN = fen;
        pgnTags.put("FEN", fen);
        // Parse taken from fen
        ArrayList<Character> possTaken = new ArrayList<Character>();
        possTaken.add(Character.valueOf('K'));
        possTaken.add(Character.valueOf('k'));
        possTaken.add(Character.valueOf('Q'));
        possTaken.add(Character.valueOf('q'));
        possTaken.add(Character.valueOf('R'));
        possTaken.add(Character.valueOf('R'));
        possTaken.add(Character.valueOf('r'));
        possTaken.add(Character.valueOf('r'));
        possTaken.add(Character.valueOf('B'));
        possTaken.add(Character.valueOf('B'));
        possTaken.add(Character.valueOf('b'));
        possTaken.add(Character.valueOf('b'));
        possTaken.add(Character.valueOf('N'));
        possTaken.add(Character.valueOf('N'));
        possTaken.add(Character.valueOf('n'));
        possTaken.add(Character.valueOf('n'));
        for (int i = 0; i < 8; i++) {
            // Add repetitions of pawn pieces
            possTaken.add(Character.valueOf('p'));
            possTaken.add(Character.valueOf('P'));
        }
        char[] fenArray = fen.split(" ")[0].toCharArray();
        for (int i = 0; i < fenArray.length; i++) {
            if (fenArray[i] > '9') {
                possTaken.remove(Character.valueOf(fenArray[i]));
            }
        }
        for (Character c : possTaken) {
            Piece p = new Piece(typeToUnicode(c), typeToSide(c),-1,-1);
            takePiece(p);
        }
    }

    /**
     * 	Checks to see if Jockfish thinks we have a next best move.
     * Stockfish returns ""(none)" if there are no best moves, which indicates
     * obliquely that it is checkmate, stalemate, or a draw
     * @return boolean value of whether there are best moves remaining
     */
    public boolean hasBestMove(){
        String nextMove = Chess.engine.getBestMove(1);
        return !nextMove.contains("none");
    }

    /**
     * Checks to see if the list of checkers from stockfish's debug mode is empty
     * @return boolean value of whether there are pieces checking the king
     */
    public boolean hasCheckers(){
        String checkersList = Chess.engine.getCheckers();
        return !checkersList.equals("Checkers:");
    }

    /**
     * If the king is in check, (there are checkers) and there is no best move
     * that indicates that the game is in checkmate
     * @return boolean value of whether the game is in checkmate
     */
    public boolean isCheckmate(){
        boolean toReturn = false;
        if(hasCheckers() && !hasBestMove()){
            toReturn = true;

        }
        return toReturn;
    }

    /**
     * If the king is not in check, (there are no checkers) and there is no best move
     * that indicates that the game is in stalemate
     * @return boolean value of whether the game is in stalemate
     */
    public boolean isStalemate(){
        boolean toReturn = false;
        if(!hasCheckers() && !hasBestMove()){
            toReturn = true;
        }
        return toReturn;
    }

    /**
     * If there are more than 99 halfmoves since the last capture or pawn move
     * It's a fifty move mate
     * @return boolean value of whether the game is a fifty move draw
     */
    public boolean isFiftyMove(){
        boolean toReturn = false;
        String fen = Chess.engine.getFEN();
        int halfmoves = Integer.parseInt(fen.split(" ")[4]);
        if(halfmoves > 99){
            toReturn = true;
        }
        return toReturn;
    }

    /**
     * A check to see if the game has ended.
     * @return int. 0 means game goes on, 1 means white wins, 2 means black,
     * 3 means stalemate, four means draw
     */
    public int getGameEndStatus(){
        if(isCheckmate() && !whiteActive){
            System.out.println("You know white won.");
            gameResult = 1;
        }
        else if(isCheckmate() && whiteActive){
            System.out.println("Black got this.");
            gameResult = 2;
        }
        else if(isStalemate()){
            gameResult = 3;
        }
        else if(threeMoveDraw){
            gameResult = 4;
        }
        else if(isFiftyMove()){
            gameResult = 4;
        }
        return gameResult;
    }

    /**
	 * Given a move string, updates the arrays and bools that check for a
     * three move draw/
	 *
	 * @param fen  the new position
	 */
    public void updateThreeMoveDraw(String fen){
        String[] splitFen = fen.split(" ");
        fen = splitFen[0] +  splitFen[2];
        if(fenHistory.contains(fen)){
            if(dupHistory.contains(fen)){
                threeMoveDraw = true;
            }
            else{
                dupHistory.add(fen);
            }
        }
        else{
            fenHistory.add(fen);
        }
    }

    /**
     * Undoes up to the last player move.
     * If computer moved since last player move, that is undone as well.
     */
    public void undoMove() {
        if (!Chess.game.gameStarted) return;
        if (lastFEN == null || prevFEN == null) {
        	Core.statusLabel.setText("Unable to undo move");
            return;
        }
        if (!userWhite) {
            // player black - undo just their move
            String move = moveHistory.remove(moveHistory.size()-1);
            if (move.contains("x")) {
                captByBlack = captByBlack.substring(0,captByBlack.lastIndexOf(' '));
                Core.takenPanel.setCaptByBlack(captByBlack);
            }
            rollbackFEN();
        } else {
            // player white - undo both their move and responding CPU move
            String move = moveHistory.remove(moveHistory.size()-1);
            if (move.contains("x")) {
                captByBlack = captByBlack.substring(0,captByBlack.lastIndexOf(' '));
                Core.takenPanel.setCaptByBlack(captByBlack);
            }
            rollbackFEN();
            move = moveHistory.remove(moveHistory.size()-1);
            if (move.contains("x")) {
                captByWhite = captByWhite.substring(0,captByWhite.lastIndexOf(' '));
                Core.takenPanel.setCaptByWhite(captByWhite);
            }
            rollbackFEN();
        }
        Core.historyPanel.updateMoveHistory(moveHistory);
    }

    /**
     * Rolls back the stored FEN strings by one, sets the pieces array accordingly,
     * and refreshes the board to visualize the change.
     */
    private void rollbackFEN() {
    	String[] splitFen = currFEN.split(" ");
        String fen = splitFen[0] +  splitFen[2];
    	fenHistory.remove(fen);
    	dupHistory.remove(fen);
        currFEN = lastFEN;
        lastFEN = prevFEN;
        prevFEN = null;
        Chess.engine.setFEN(currFEN);
        setPiecesFromFEN(currFEN);
        ViewUtils.refreshBoard();
    }
}
