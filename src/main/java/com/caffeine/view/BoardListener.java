package com.caffeine.view;

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;

import com.caffeine.Chess;
import com.caffeine.logic.Piece;

/**
 *  This ActionListener listens for clicks upon the board and first saves
 *  the chess icon of the square clicked (if there is one), then sets the text
 *  of the next clicked square to the stored icon.
 */
public class BoardListener implements ActionListener {

    static BoardSquare selected = null;

    public void actionPerformed(ActionEvent e) {

        BoardSquare squareButton = (BoardSquare) e.getSource();

        if (selected == null) {

            // if no previous board square selected, save the one clicked
            if (squareButton.getPiece() != null) {

                // Can't select opponent's pieces
                Character userColor = Chess.game.getUserColor();
                boolean pieceIsWhite = squareButton.getPiece().isWhite();
                if (pieceIsWhite && !userColor.equals('b')) return;
                if (!pieceIsWhite && userColor.equals('w')) return;

                selected = squareButton;
                selected.selectSquare();

            } else {

                // no previously selected button and button clicked is empty
                return;
            }
        } else {

            // else move the previously selected chess piece to the clicked square
            String oldPos = selected.getName().split(":")[1];
            String newPos = squareButton.getName().split(":")[1];
            String moveString = oldPos + newPos;
            String statusMessage;

            Piece piece = selected.getPiece();

            if(Chess.game.move(moveString)){
                statusMessage = String.format("Moved: {}", moveString);
            } else {
                statusMessage = "Invalid move";
            }

            // // TODO: This shouldn't be needed if GUI is polling Game state
            // {
            //     selected.removePiece();
            //     squareButton.setPiece(piece);
            //     String oldLoc = (char)(oldFile+65) + "" + (oldRank+1);
            //     String newLoc = (char)(newFile+65) + "" + (newRank+1);
            //     Core.statusLabel.setText("Move: " + oldLoc + "," + newLoc);

            //     // to be changed when computer is opponent
            //     if (Chess.game.userWhite()) Chess.game.setSide("black");
            //     else Chess.game.setSide("white");
            // } else {
            //     Core.statusLabel.setText("Invalid move");
            // }

            selected.unselectSquare();
            selected = null;

        }
    }
}
