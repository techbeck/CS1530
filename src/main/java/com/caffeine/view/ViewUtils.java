package com.caffeine.view;

import com.caffeine.Chess;
import com.caffeine.logic.Piece;

import java.util.*;

public class ViewUtils {
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
	public static void refreshTakenPanel() {
		Core.takenPanel.setCaptByBlack("");
		Core.takenPanel.setCaptByWhite("");
	}
	public static void refreshHistoryPanel() {
		Core.historyPanel.updateMoveHistory(new ArrayList<String>());
	}
}