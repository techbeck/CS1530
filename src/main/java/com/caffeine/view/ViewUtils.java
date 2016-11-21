package com.caffeine.view;

import com.caffeine.Chess;
import com.caffeine.logic.Piece;

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
}