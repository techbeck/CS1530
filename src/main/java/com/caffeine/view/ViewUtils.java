package com.caffeine.view;

import com.caffeine.Chess;
import com.caffeine.logic.Piece;
import com.caffeine.logic.FileManager;
import com.caffeine.logic.Utils;

import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

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
        hidePossibilities();
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

    /**
     * Unhighlight possible move squares
     */
    public static void hidePossibilities() {
        // Un-highlight all previously highlighted BoardSquares that were
        // valid positions.
        if (Core.showLegalMoves){
            for (String move : Core.possibleMoves){
                Integer[] raf = Utils.translate(move); // Rank And File
                BoardSquare square = Core.squares[7-raf[0]][raf[1]];
                square.resetSquare();
            }
            Core.possibleMoves.clear();
        }
    }

    /**
     * End-of-game view functionality
     *
     * @param gameResult the result of the game (win/lose, etc.)
     */
    public static void endGame(int gameResult) {
        String message = "";
        if (Core.timerPanel.isTimeOut()) {
            switch (gameResult) {
                case 1: // white won
                    message = "Timer Ran Out. \nWhite Won!";
                    break;
                case 2: // black won
                    message = "Timer Ran Out. \nBlack Won!";
                    break;
            }
        } else {
            switch (gameResult) {
                case 1: // white won
                    message = "White Won!";
                    break;
                case 2: // black won
                    message = "Black Won!";
                    break;
                case 3: // stalemate
                    message = "Stalemate!";
                    break;
                case 4: // draw
                    message = "Draw!";
                    break;
            }
        }
        Core.statusPanel.setText(message);
        JOptionPane.showMessageDialog(Core.window,
            message, "End of Game", JOptionPane.PLAIN_MESSAGE);
        int dialogResult = JOptionPane.showConfirmDialog(Core.window, "Would you like to save your game?",
                                        "Save?", JOptionPane.YES_NO_CANCEL_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            Core.saveButton.doClick();
        }
    }
}
