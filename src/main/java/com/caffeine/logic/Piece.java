package com.caffeine.logic;

public class Piece {
	String type;
	int number;
	boolean isWhite;

	/**
	 * Initializes type, number, and side as passed in as parameters.
	 *
	 * @param type  The type of chess piece
	 * @param number  The number, to differentiate between pieces of same type
	 * @param side  The side, eg. black or white
	 */
	public Piece(String type, int number, String side) {
		this.type = type;		// eg. king or knight
		this.number = number;	// eg. knight 0 or 1
		if (side.equals("white")) {
			isWhite = true;
		} else {
			isWhite = false;
		}
	}

	/**
	 * Returns the type of the piece as a String.
	 *
	 * @return  The type, eg. king or knight
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns the numeric ID of the piece.
	 *
	 * @return  The ID to differentiate between pieces of same type
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Returns whether the piece is white or not.
	 *
	 * @return  true if white, otherwise false (aka black)
	 */
	public boolean isWhite() {
		return isWhite;
	}
}