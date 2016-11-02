package com.caffeine.view;

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
        	// else set the clicked square to the previously selected chess piece
            com.caffeine.logic.Piece piece = selected.getPiece();
            selected.unselectSquare();
            selected.removePiece();
            squareButton.setPiece(piece);
            selected = null;
        }
    }
}