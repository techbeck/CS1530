package com.caffeine.logic;

import com.caffeine.Chess;
import com.caffeine.view.Core;

public class Game {
    public Piece[] pieces = new Piece[32];
    
	protected boolean whiteActive;
	protected boolean userWhite;
	protected String captByBlack;
	protected String captByWhite;

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
	 * 	Getter for whether the user is playing as white or black
	 * 	
	 *  @return true if the user is playing as white, false if black
	 */
	public boolean userWhite() {
		return userWhite;
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
			Piece taken = getPieceMatching(newRank,newFile);
			Piece moving = getPieceMatching(oldRank, oldFile);
			
			if (taken != null) {
				taken.moveTo(-1,-1); // indicates piece has been taken
				if (taken.isWhite())
					captureWhitePiece(taken.getType());
				else
					captureBlackPiece(taken.getType());
			}
			moving.moveTo(newRank, newFile);
			return true;
		}
		return false;
	}

	/**
	 * 	Gets the piece at a given position
	 *  @param  rank 	The given x coordinate
	 *  @param  file 	The given y coordinate
	 *  @return a Piece if a Piece exists at the given position, null if not
	 */
	public Piece getPieceMatching(int rank, int file) {
		for (Piece p : pieces) {
			if (p.getRank() == rank) {
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
		pieces[0] = new Piece(Core.rook, 0, "black", 7, 7);
		pieces[1] = new Piece(Core.rook, 1, "black", 7, 0);
		pieces[2] = new Piece(Core.knight, 0, "black", 7, 1);
		pieces[3] = new Piece(Core.knight, 1, "black", 7, 6);
		pieces[4] = new Piece(Core.bishop, 0, "black", 7, 2);
		pieces[5] = new Piece(Core.bishop, 1, "black", 7, 5);
		pieces[6] = new Piece(Core.queen, 0, "black", 7, 3);
		pieces[7] = new Piece(Core.king, 0, "black", 7, 4);
		for (int i = 0; i < 8; i++) {
			pieces[i+8] = new Piece(Core.pawn, i, "black", 6, i);
			pieces[i+16] = new Piece(Core.pawn, i, "white", 1, i);
		}
		pieces[24] = new Piece(Core.rook, 0, "white", 0, 0);
		pieces[25] = new Piece(Core.rook, 1, "white", 0, 7);
		pieces[26] = new Piece(Core.knight, 0, "white", 0, 1);
		pieces[27] = new Piece(Core.knight, 1, "white", 0, 6);
		pieces[28] = new Piece(Core.bishop, 0, "white", 0, 2);
		pieces[29] = new Piece(Core.bishop, 1, "white", 0, 5);
		pieces[30] = new Piece(Core.queen, 0, "white", 0, 3);
		pieces[31] = new Piece(Core.king, 0, "white", 0, 4);
	}
}