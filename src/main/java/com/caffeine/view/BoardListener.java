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
                Core.statusPanel.setText("Selected: " + selected.getName().split(":")[1]);

                // Get list of positions the Piece on this BoardSquare can
                // be moved to. Visually indicates with green background.
                if (Core.showLegalMoves){
                    Core.possibleMoves = selected.getPossibleMoves();
                    for (String move : Core.possibleMoves){
                        Integer[] raf = Utils.translate(move); // Rank And File
                        BoardSquare square = Core.squares[7-raf[0]][raf[1]];
                        square.indicateValidDestination();
                    }
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
                    Core.statusPanel.setText("User Move: " + oldLoc + "," + newLoc);
                    ViewUtils.refreshBoard();
                    //Check that game has not ended
                    int gameState = Chess.game.getGameEndStatus();
                    if(gameState != 0){
                        Chess.game.endGame(gameState);
                    } else {
                        // Do CPU Move in response
                        String cpuMove = Chess.game.cpuMove();
                        String[] moveData = cpuMove.split("");
                        moveData[0] = moveData[0].toUpperCase();
                        moveData[2] = moveData[2].toUpperCase();
                        Core.statusPanel.setText("CPU Move: " + moveData[0] + "" + moveData[1] + "," +
                                                        moveData[2] + "" + moveData[3]);
                        ViewUtils.refreshBoard();
                        //Check that game has not ended
                        gameState = Chess.game.getGameEndStatus();
                        if(gameState != 0){
                            Chess.game.endGame(gameState);
                        }
                    }
                    
                } else if (piece.getType().equals(Core.pawn)) {
                    if (Chess.game.userWhite() && newRank == 7 || !Chess.game.userWhite() && newRank == 0) {
                        // Edge-case: Promotion
                        if (Chess.game.tryPromotion(oldRank,oldFile,newRank,newFile,'r')) {
                            // only offers promotion if it would be a valid move
                            String[] options = new String[] {"Queen", "Knight", "Rook", "Bishop"};
                            String choice = (String) JOptionPane.showInputDialog(Core.window, "Choose Type",
                                    "Choose Type for Promotion", JOptionPane.QUESTION_MESSAGE,
                                    null, options, options[0]);
                            if (choice != null) {
                                char type;
                                if (choice.equals("Queen")) type = 'q';
                                else if (choice.equals("Knight")) type = 'n';
                                else if (choice.equals("Rook")) type = 'r';
                                else type = 'b';
                                Core.statusPanel.setText("Promotion to " + choice);
                                Chess.game.moveP(oldRank,oldFile,newRank,newFile,type);
                                ViewUtils.refreshBoard();
                                //Check that game has not ended
                                int gameState = Chess.game.getGameEndStatus();
                                if(gameState != 0){
                                    Chess.game.endGame(gameState);
                                } else {
                                    // Do CPU Move in response
                                    String cpuMove = Chess.game.cpuMove();
                                    String[] moveData = cpuMove.split("");
                                    moveData[0] = moveData[0].toUpperCase();
                                    moveData[2] = moveData[2].toUpperCase();
                                    Core.statusPanel.setText("CPU Move: " + moveData[0] + "" +
                                     moveData[1] + "," + moveData[2] + "" + moveData[3]);
                                    ViewUtils.refreshBoard();
                                    //Check that game has not ended
                                    gameState = Chess.game.getGameEndStatus();
                                    if(gameState != 0){
                                        Chess.game.endGame(gameState);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Core.statusPanel.setText("Invalid move");
                }
            }

            ViewUtils.hidePossibilities();

            selected.unselectSquare();
            selected = null;
            return;
        }
    }
}
