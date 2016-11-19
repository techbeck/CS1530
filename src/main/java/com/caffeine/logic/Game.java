package com.caffeine.logic;

import com.caffeine.Chess;
import com.caffeine.view.Core;

import java.util.ArrayList;

public class Game {
    public Piece[] pieces = new Piece[32];

    public ArrayList<String> moveHistory = new ArrayList<String>();

	private int mode = 0; // 0 = easy, 1 = medium, 2 = hard
	private static final int[] timeoutsForModes = {500, 2000, 5000};
    
	protected boolean whiteActive;
	protected boolean userWhite;
	protected String captByBlack;
	protected String captByWhite;

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
		initializesPieces();
	}

	/**
	 * 	Sets the user as either white or black
	 * 	
	 *  @param side The color the user will play as
	 */
	public void setSide(String side) {
		if (side.equals("white"))
			userWhite = true;
		else
			userWhite = false;
	}

	/**
	 * Sets the mode based on the string passed in.
	 * Default is easy.
	 *
	 * @param mode  The string to determine which mode to set.
	 */
	public void setMode(String mode) {
		if (mode.equals("hard")) this.mode = 2;
		else if (mode.equals("medium")) this.mode = 1;
		else this.mode = 0;
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
	 * 	Move a piece from one set of coordinates to another
	 *  @param  oldRank The current horizontal coordinate
	 *  @param  oldFile The current vertical coordinate
	 *  @param  newRank The new horizontal coordinate to move to
	 *  @param  newFile The new vertical coordinate to move to
	 *  @return true if move is successful, false otherwise
	 */
	public boolean move(int oldRank, int oldFile, int newRank, int newFile) {
		String oldLoc = (char)(oldFile+65) + "" + (oldRank+1);
		String newLoc = (char)(newFile+65) + "" + (newRank+1);

		if (Chess.engine.move(oldLoc+newLoc)) {
			doMove(oldRank, oldFile, newRank, newFile);
			addToMoveHistory(oldLoc+newLoc);
			return true;
		}
		return false;
	}

	public boolean cpuMove() {
		int timeout = timeoutsForModes[mode];
		String move = Chess.engine.cpuMove(timeout);
		if (move.equals("(none)")) return false;
		char[] moveData = move.toCharArray();
		int oldRank = (int) moveData[1] - '1';
		int oldFile = (int) moveData[0] - 'a';
		int newRank = (int) moveData[3] - '1';
		int newFile = (int) moveData[2] - 'a';
		doMove(oldRank, oldFile, newRank, newFile);

		addToMoveHistory(move);

		return true;
	}

	public void doMove(int oldRank, int oldFile, int newRank, int newFile) {
		Piece taken = getPieceMatching(newRank,newFile);
		Piece moving = getPieceMatching(oldRank, oldFile);
		
		if (taken != null) {
			takePiece(taken);
		}

		String fen = Chess.engine.getFEN();

		// check for en passant and taking of en passant
		if (!enPassantLoc.equals("-")) {
			if (moving.getType().equals(pawn)) {
				int enPassantRank = (int) enPassantLoc.charAt(1) - '1';
				int enPassantFile = (int) enPassantLoc.charAt(0) - 'a';
				if (newRank == enPassantRank && newFile == enPassantFile) {
					if (moving.isWhite()) {
						takePiece(getPieceMatching(newRank-1,newFile));
					} else {
						takePiece(getPieceMatching(newRank+1,newFile));
					}
				}
			}
		}
		enPassantLoc = fen.split(" ")[3];

		setPiecesFromFEN(fen);
	}

	/**
	 * 	Gets the piece at a given position
	 *  @param  rank 	The given x coordinate
	 *  @param  file 	The given y coordinate
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
	 * 	Resets the game to the default settings
	 */
	public void reset() {
		whiteActive = true;
		userWhite = true;
		captByBlack = "";
		captByWhite = "";
		initializesPieces();
	}

	/**
	 * 	Populates the pieces array with the standard 32 chess pieces
	 */
	public void initializesPieces() {
		pieces[0] = new Piece(Core.rook, "black", 7, 0);
		pieces[1] = new Piece(Core.knight, "black", 7, 1);
		pieces[2] = new Piece(Core.bishop, "black", 7, 2);
		pieces[3] = new Piece(Core.queen, "black", 7, 3);
		pieces[4] = new Piece(Core.king, "black", 7, 4);
		pieces[5] = new Piece(Core.bishop, "black", 7, 5);
		pieces[6] = new Piece(Core.knight, "black", 7, 6);
		pieces[7] = new Piece(Core.rook, "black", 7, 7);
		for (int i = 0; i < 8; i++) {
			pieces[i+8] = new Piece(Core.pawn, "black", 6, i);
			pieces[i+16] = new Piece(Core.pawn, "white", 1, i);
		}
		pieces[24] = new Piece(Core.rook, "white", 0, 0);
		pieces[25] = new Piece(Core.knight, "white", 0, 1);
		pieces[26] = new Piece(Core.bishop, "white", 0, 2);
		pieces[27] = new Piece(Core.queen, "white", 0, 3);
		pieces[28] = new Piece(Core.king, "white", 0, 4);
		pieces[29] = new Piece(Core.bishop, "white", 0, 5);
		pieces[30] = new Piece(Core.knight, "white", 0, 6);
		pieces[31] = new Piece(Core.rook, "white", 0, 7);
	}

	public void setPiecesFromFEN(String fen) {

		//System.out.println(fen);

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
        				pieces[pieceInd] = new Piece(typeToUnicode(currentChar),typeToSide(currentChar),7-i,j);
        			} else {
        				pieces[pieceInd] = new Piece(typeToUnicode(currentChar),typeToSide(currentChar),7-i,rowCursor);
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

	public String typeToUnicode(char type) {

		switch(type) {
			case 'K':
			case 'k': return king;
			case 'Q':
			case 'q': return queen;
			case 'R':
			case 'r': return rook;
			case 'B':
			case 'b': return bishop;
			case 'N':
			case 'n': return knight;
			case 'P':
			case 'p': return pawn;
			default: return "null";
		}
	}

	public String typeToSide(char type) {
		if (((int) type) < 90) return "white";
		else return "black";
	}
}