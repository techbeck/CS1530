package com.caffeine.logic;

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

	public void setSide(String side) {
		if (side.equals("white"))
			userWhite = true;
		else
			userWhite = false;
	}

	public boolean userWhite() {
		return userWhite;
	}

	public void captureBlackPiece(String piece) {
		captByWhite = captByWhite.concat(" " + piece);
		Core.takenPanel.setCaptByWhite(captByWhite);
	}

	public void captureWhitePiece(String piece) {
		captByBlack = captByBlack.concat(" " + piece);
		Core.takenPanel.setCaptByBlack(captByBlack);
	}

	public String getCaptByBlack() {
		return captByBlack;
	}

	public String getCaptByWhite() {
		return captByWhite;
	}

	public boolean move(int oldRank, int oldFile, int newRank, int newFile) {
		String oldLoc = (char)(oldFile+65) + "" + (oldRank+1);
		String newLoc = (char)(newFile+65) + "" + (newRank+1);
		if (true) {//(Engine.isValidMove(oldLoc+newLoc)) {
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

	public void reset() {
		whiteActive = true;
		userWhite = true;
		captByBlack = "";
		captByWhite = "";
		initializesPieces();
	}

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