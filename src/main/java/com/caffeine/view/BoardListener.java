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

        if (!Chess.game.gameStarted) return;

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

                Core.statusLabel.setText("Selected: " + selected.getName().split(":")[1]);

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
                Core.statusLabel.setText("User Move: " + oldLoc + "," + newLoc);

                ViewUtils.refreshBoard();

                // Do CPU Move in response
                String cpuMove = Chess.game.cpuMove();

                String[] moveData = cpuMove.split("");
                moveData[0] = moveData[0].toUpperCase();
                moveData[2] = moveData[2].toUpperCase();
                Core.statusLabel.setText("CPU Move: " + moveData[0] + "" + moveData[1] + "," + moveData[2] + "" + moveData[3]);

                ViewUtils.refreshBoard();

            } else {
                Core.statusLabel.setText("Invalid move");
            }
            selected.unselectSquare();
            selected = null;
        }
    }
}