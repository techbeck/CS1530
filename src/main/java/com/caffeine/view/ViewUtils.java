package com.caffeine.view;

import com.caffeine.Chess;
import com.caffeine.logic.Piece;

import java.util.*;

public class ViewUtils {
	/**
	 * Refreshes the board squares to visualize the current pieces array in logic
	 */
	public static void refreshBoard() {
		for (int i = 0; i < 8; i++) {
		    for (int j = 0; j < 8; j++) {
		        Piece currPiece = Chess.game.getPieceMatching(7-i,j);
		        if (currPiece != null) {
		            Core.squares[i][j].setPiece(currPiece);
		        } else {
		            Core.squares[i][j].removePiece();
		        }
		    }
		}
	}

	/**
	 * Clears the Taken Panel of both black and white captures
	 */
	public static void clearTakenPanel() {
		Core.takenPanel.setCaptByBlack("");
		Core.takenPanel.setCaptByWhite("");
	}

	/**
	 * Clears the Move History Panel
	 */
	public static void clearHistoryPanel() {
		Core.historyPanel.updateMoveHistory(new ArrayList<String>());
	}
}