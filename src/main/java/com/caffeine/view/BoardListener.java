package com.caffeine.view;

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
 * 	of the next clicked square to the stored icon.
 */
public class BoardListener implements ActionListener {

    static BoardSquare selected = null;

    public void actionPerformed(ActionEvent e) {

        BoardSquare squareButton = (BoardSquare) e.getSource();

        int i = 8 - Integer.parseInt(squareButton.getName().split(":")[1].split(",")[1]);
        int j = ((int) squareButton.getName().split(":")[1].split(",")[0].toCharArray()[0]) - 65;

        if (selected == null) {
        	// if no previous board square selected, save the one clicked
            if (squareButton.getPiece() != null) {
                selected = squareButton;
                selected.selectSquare();

            } else {

                // no previously selected button and button clicked is empty
                return;
            }
        } else {
        	// else move the previously selected chess piece to the clicked square 
            Piece piece = selected.getPiece();
            piece.moveTo(i,j);
            selected.unselectSquare();
            selected.removePiece();
            squareButton.setPiece(piece);
            selected = null;
        }
    }
}