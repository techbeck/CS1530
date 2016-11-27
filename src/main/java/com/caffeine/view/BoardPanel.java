package com.caffeine.view;

import com.caffeine.Chess;
import com.caffeine.logic.Piece;

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    private boolean blackOnTop;

    public BoardPanel() {
        setName("boardPanel");
        setBackground(Color.decode(Core.themes[0][0]));
        blackOnTop = true;
        initializeBoard();
        initializePiecePlacement();
    }

    /*	reinitialize the board panel and check if black
    	is on top*/

    /**
     * 	Reinitialize the board panel and check if black
     * 	is currently on top
     *
     *  @param  top The current player on top (black or white)
     */
    public BoardPanel(String top) {
        setName("boardPanel");
        setBackground(Color.decode(Core.themes[0][0]));
        if (top.equals("black")) {
            blackOnTop = true;
        } else {
            blackOnTop = false;
        }
        initializeBoard();
    }

    /**
     * Returns whether or not black in at the top of the board or the bottom
     *
     * @return  true if black on top, false if black on bottom
     */
    public boolean blackOnTop() {
        return blackOnTop;
    }

    /**
     *  Initializes an 8x8 Array of buttons to serve as the Core.squares
     *  for the chess board, along with grid notation.
     *  Can initialize to black on top or white on top depending upon
     *  the blackOnTop boolean.
     */
    private void initializeBoard() {

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = Core.AVERAGE_WEIGHT;
        c.weighty = Core.AVERAGE_WEIGHT;
        c.fill = GridBagConstraints.BOTH;

        BoardListener boardListener = new BoardListener();
        boolean isWhiteSquare = true;  //  top right is white square

        if (blackOnTop) {

            for (byte i = 0; i < 8; i++) {

                // grid notation row name
                JLabel notationRow = new JLabel(String.valueOf(8-i), SwingConstants.CENTER);
                c.gridx = 0;
                c.gridy = i;
                c.insets = Core.sidePadding;
                add(notationRow, c);
                c.insets = Core.noPadding;

                //row of buttons
                for (byte j = 0; j < 8; j++) {

                    Core.squares[i][j] = new BoardSquare();
                    Core.squares[i][j].setBackgroundColor(isWhiteSquare);
                    Core.squares[i][j].setName("BoardSquare:" + (char)(j+65) + "," + (8-i));
                    Core.squares[i][j].addActionListener(boardListener);
                    c.gridx = j+1;
                    c.gridy = i;
                    add(Core.squares[i][j], c);
                    isWhiteSquare = !isWhiteSquare;
                }

                isWhiteSquare = !isWhiteSquare; // creates the checkered pattern of Core.squares
            }

            // grid notation column names
            for (byte i = 0; i < 8; i++) {

                JLabel notationColumn = new JLabel(String.valueOf((char)(i+65)), SwingConstants.CENTER);
                c.gridx = i+1;
                c.gridy = 8;
                c.insets = Core.topBottomPadding;
                add(notationColumn, c);
            }
        } else { // loops run in reverse to flip the board from standard

            for (byte i = 7; i >= 0; i--) {

                // grid notation row name
                JLabel notationRow = new JLabel(String.valueOf(8-i), SwingConstants.CENTER);
                c.gridx = 0;
                c.gridy = 8-(i+1);
                c.insets = Core.sidePadding;
                add(notationRow, c);
                c.insets = Core.noPadding;

                //row of buttons
                for (byte j = 7; j >= 0; j--) {

                    Core.squares[i][j] = new BoardSquare();
                    Core.squares[i][j].setBackgroundColor(isWhiteSquare);
                    Core.squares[i][j].setName("BoardSquare:" + (char)(j+65) + "," + (8-i));
                    Core.squares[i][j].addActionListener(boardListener);
                    c.gridx = 8-(j);
                    c.gridy = 8-(i+1);
                    add(Core.squares[i][j], c);
                    isWhiteSquare = !isWhiteSquare;
                }

                isWhiteSquare = !isWhiteSquare; // creates the checkered pattern of Core.squares
            }

            // grid notation column names
            for (byte i = 7; i >= 0; i--) {

                JLabel notationColumn = new JLabel(String.valueOf((char)(i+65)), SwingConstants.CENTER);
                c.gridx = 8-i;
                c.gridy = 8;
                c.insets = Core.topBottomPadding;
                add(notationColumn, c);
            }
        }
    }

    /**
     * Initializes board layout with black pieces on top
     */
    private void initializePiecePlacement() {
        for (int i = 0; i < 8; i++) {
            Core.squares[0][i].setPiece(Chess.game.getPieceMatching(7,i));
            Core.squares[1][i].setPiece(Chess.game.getPieceMatching(6,i));
            Core.squares[6][i].setPiece(Chess.game.getPieceMatching(1,i));
            Core.squares[7][i].setPiece(Chess.game.getPieceMatching(0,i));
        }
    }
}
