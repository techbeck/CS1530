package com.caffeine.logic;

import com.caffeine.view.Core;

public class Game {
    public static Piece[] pieces = new Piece[32];

	protected static boolean whiteActive;
	protected static boolean userWhite;
	protected static String captByBlack = "";
	protected static String captByWhite = "";

	public static void captureBlackPiece(String piece) {
		captByWhite = captByWhite.concat(" " + piece);
		Core.takenPanel.setCaptByWhite(captByWhite);
	}

	public static void captureWhitePiece(String piece) {
		captByBlack = captByBlack.concat(" " + piece);
		Core.takenPanel.setCaptByBlack(captByBlack);
	}

	public static String getCaptByBlack() {
		return captByBlack;
	}

	public static String getCaptByWhite() {
		return captByWhite;
	}

	public static boolean move(int oldRank, int oldFile, int newRank, int newFile) {
		String oldLoc = (char)(oldFile+65) + "" + (oldRank+1);
		String newLoc = (char)(newFile+65) + "" + (newRank+1);
		System.out.println(oldLoc+newLoc);
		if (true) {//(Engine.isValidMove(oldLoc+newLoc)) {
			Piece taken = getPieceMatching(newRank,newFile);
			Piece moving = getPieceMatching(oldRank, oldFile);
			
			if (taken != null) {
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

	public static Piece getPieceMatching(int rank, int file) {
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