package com.caffeine.logic;

import com.caffeine.view.Core;

public class Game {
    public Piece[] pieces = new Piece[32];

	protected boolean whiteActive;
	protected boolean userWhite;
	protected String captByBlack = "";
	protected String captByWhite = "";

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
}