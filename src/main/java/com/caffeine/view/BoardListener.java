package com.caffeine.view;

import com.caffeine.Chess;
import com.caffeine.logic.Piece;

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;

/**
 * 	This ActionListener listens for clicks upon the board and first saves
 * 	the chess icon of the square clicked (if there is one), then sets the text
 * 	of the next clicked square to the stored icon, if a valid move.
 */
public class BoardListener implements ActionListener {

    static BoardSquare selected = null;

    public void actionPerformed(ActionEvent e) {

        BoardSquare squareButton = (BoardSquare) e.getSource();

        if (selected == null) {

        	// if no previous board square selected, save the one clicked
            if (squareButton.getPiece() != null) {

                // Can't select opponent's pieces
                if (squareButton.getPiece().isWhite() && !Chess.game.userWhite())
                    return;
                if (!squareButton.getPiece().isWhite() && Chess.game.userWhite())
                    return;

                selected = squareButton;
                selected.selectSquare();

            } else {

                // no previously selected button and button clicked is empty
                return;
            }
        } else {
        	// else move the previously selected chess piece to the clicked square 
            int oldRank = Integer.parseInt(selected.getName().split(":")[1].split(",")[1]) - 1;
            int oldFile = ((int) selected.getName().split(":")[1].split(",")[0].toCharArray()[0]) - 65;
            int newRank = Integer.parseInt(squareButton.getName().split(":")[1].split(",")[1]) - 1;
            int newFile = ((int) squareButton.getName().split(":")[1].split(",")[0].toCharArray()[0]) - 65;

            Piece piece = selected.getPiece();
            if (Chess.game.move(oldRank,oldFile,newRank,newFile))
            {
                selected.removePiece();
                squareButton.setPiece(piece);
                String oldLoc = (char)(oldFile+65) + "" + (oldRank+1);
                String newLoc = (char)(newFile+65) + "" + (newRank+1);
                Core.statusLabel.setText("Move: " + oldLoc + "," + newLoc);

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (Chess.game.getPieceMatching(7-i,j) != null) {
                            Core.squares[i][j].setPiece(Chess.game.getPieceMatching(7-i,j));
                        } else {
                            Core.squares[i][j].removePiece();
                        }
                    }
                }

                // Do CPU Move in response
                Chess.game.cpuMove();

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (Chess.game.getPieceMatching(7-i,j) != null) {
                            Core.squares[i][j].setPiece(Chess.game.getPieceMatching(7-i,j));
                        } else {
                            Core.squares[i][j].removePiece();
                        }
                    }
                }

            } else {
                Core.statusLabel.setText("Invalid move");
            }
            selected.unselectSquare();
            selected = null;
        }
    }
}