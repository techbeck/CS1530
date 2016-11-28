package com.caffeine.view;

import com.caffeine.Chess;
import com.caffeine.logic.Piece;
import com.caffeine.logic.Utils;

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;

/**
 *  This ActionListener listens for clicks upon the board and first saves
 *  the chess icon of the square clicked (if there is one), then sets the text
 *  of the next clicked square to the stored icon, if a valid move.
 */
public class BoardListener implements ActionListener {

    static BoardSquare selected = null;
    static ArrayList<String> possibleMoves = new ArrayList();

    public void actionPerformed(ActionEvent e) {

        if (!Chess.game.gameStarted) return;

        BoardSquare squareButton = (BoardSquare) e.getSource();

        if (selected == null) {

            // if no previous board square selected, save the one clicked
            Piece selectedPiece = squareButton.getPiece();

            if (selectedPiece != null) {
                // Can't select opponent's pieces
                if (selectedPiece.isWhite() && !Chess.game.userWhite()){ return; }
                if (!selectedPiece.isWhite() && Chess.game.userWhite()){ return; }

                selected = squareButton;
                selected.selectSquare();
                Core.statusLabel.setText("Selected: " + selected.getName().split(":")[1]);

                // Get list of positions the Piece on this BoardSquare can
                // be moved to. Visually indicates with green background.
                possibleMoves = selected.getPossibleMoves();
                for (String move : possibleMoves){
                    Integer[] raf = Utils.translate(move); // Rank And File
                    BoardSquare square = Core.squares[7-raf[0]][raf[1]];
                    square.indicateValidDestination();
                }
            } else {
                // no previously selected button and button clicked is empty
                return;
            }
        } else {

            if (squareButton != selected){
                // else move the previously selected chess piece to the clicked square
                int oldRank = Integer.parseInt(selected.getName().split(":")[1].split(",")[1]) - 1;
                int oldFile = ((int) selected.getName().split(":")[1].split(",")[0].toCharArray()[0]) - 65;
                int newRank = Integer.parseInt(squareButton.getName().split(":")[1].split(",")[1]) - 1;
                int newFile = ((int) squareButton.getName().split(":")[1].split(",")[0].toCharArray()[0]) - 65;
                Piece piece = selected.getPiece();
                if (Chess.game.move(oldRank,oldFile,newRank,newFile)) {
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
                } else { Core.statusLabel.setText("Invalid move"); }
            }

            // Un-highlight all previously highlighted BoardSquares that were
            // valid positions.
            for (String move : possibleMoves){
                Integer[] raf = Utils.translate(move); // Rank And File
                BoardSquare square = Core.squares[7-raf[0]][raf[1]];
                square.resetSquare();
            }
            possibleMoves.clear();

            selected.unselectSquare();
            selected = null;
            return;
        }
    }
}
