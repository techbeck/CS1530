package com.caffeine.view;

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
     *  Can initialize to black on top or white on top depending on boolean.
     *
     *  @param boardPanel the JPanel upon which to place the game Core.squares on
     */
    private void initializeBoard() {

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        BoardListener boardListener = new BoardListener();
        boolean isWhiteSquare = true;

        if (blackOnTop) {

            for (byte i = 0; i < 8; i++) {

                // grid notation row name
                JLabel notationRow = new JLabel(String.valueOf(8-i), SwingConstants.CENTER);
                c.fill = GridBagConstraints.BOTH;
                c.gridx = 0;
                c.gridy = i;
                c.insets = Core.sidePadding;
                c.weightx = Core.AVERAGE_WEIGHT;
                c.weighty = Core.AVERAGE_WEIGHT;
                add(notationRow, c);
                c.insets = Core.noPadding;

                //row of buttons
                for (byte j = 0; j < 8; j++) {
                    
                    Core.squares[i][j] = new BoardSquare();
                    Core.squares[i][j].setBackgroundColor(isWhiteSquare);
                    Core.squares[i][j].setName("BoardSquare:" + (char)(j+65) + "," + (8-i));
                    Core.squares[i][j].addActionListener(boardListener);
                    c.fill = GridBagConstraints.BOTH;
                    c.gridx = j+1;
                    c.gridy = i;
                    c.weightx = Core.AVERAGE_WEIGHT;
                    c.weighty = Core.AVERAGE_WEIGHT;
                    add(Core.squares[i][j], c);
                    isWhiteSquare = !isWhiteSquare;
                }

                isWhiteSquare = !isWhiteSquare; // creates the checkered pattern of Core.squares
            }

            // grid notation column names
            for (byte i = 0; i < 8; i++) {

                JLabel notationColumn = new JLabel(String.valueOf((char)(i+65)), SwingConstants.CENTER);
                c.fill = GridBagConstraints.BOTH;
                c.gridx = i+1;
                c.gridy = 8;
                c.insets = Core.topBottomPadding;
                c.weightx = Core.AVERAGE_WEIGHT;
                c.weighty = Core.AVERAGE_WEIGHT;
                add(notationColumn, c);
            }
        } else { // loops run in reverse to flip the board from standard

            for (byte i = 7; i >= 0; i--) {

                // grid notation row name
                JLabel notationRow = new JLabel(String.valueOf(8-i), SwingConstants.CENTER);
                c.fill = GridBagConstraints.BOTH;
                c.gridx = 0;
                c.gridy = 8-(i+1);
                c.insets = Core.sidePadding;
                c.weightx = Core.AVERAGE_WEIGHT;
                c.weighty = Core.AVERAGE_WEIGHT;
                add(notationRow, c);
                c.insets = Core.noPadding;

                //row of buttons
                for (byte j = 7; j >= 0; j--) {
                    
                    Core.squares[i][j] = new BoardSquare();
                    Core.squares[i][j].setBackgroundColor(isWhiteSquare);
                    Core.squares[i][j].setName("BoardSquare:" + (char)(j+65) + "," + (8-i));
                    Core.squares[i][j].addActionListener(boardListener);
                    c.fill = GridBagConstraints.BOTH;
                    c.gridx = 8-(j);
                    c.gridy = 8-(i+1);
                    c.weightx = Core.AVERAGE_WEIGHT;
                    c.weighty = Core.AVERAGE_WEIGHT;
                    add(Core.squares[i][j], c);
                    isWhiteSquare = !isWhiteSquare;
                }

                isWhiteSquare = !isWhiteSquare; // creates the checkered pattern of Core.squares
            }

            // grid notation column names
            for (byte i = 7; i >= 0; i--) {

                JLabel notationColumn = new JLabel(String.valueOf((char)(i+65)), SwingConstants.CENTER);
                c.fill = GridBagConstraints.BOTH;
                c.gridx = 8-i;
                c.gridy = 8;
                c.insets = Core.topBottomPadding;
                c.weightx = Core.AVERAGE_WEIGHT;
                c.weighty = Core.AVERAGE_WEIGHT;
                add(notationColumn, c);
            }
        }
    }

    /** 
     * Initializes board layout with black pieces on top
     */
    private void initializePiecePlacement() {
        Core.squares[0][7].setPiece(Core.game.pieces[0]);
        Core.squares[0][0].setPiece(Core.game.pieces[1]);
        Core.squares[0][1].setPiece(Core.game.pieces[2]);
        Core.squares[0][6].setPiece(Core.game.pieces[3]);
        Core.squares[0][2].setPiece(Core.game.pieces[4]);
        Core.squares[0][5].setPiece(Core.game.pieces[5]);
        Core.squares[0][3].setPiece(Core.game.pieces[6]);
        Core.squares[0][4].setPiece(Core.game.pieces[7]);
        for (int i = 0; i < 8; i++) {
            Core.squares[1][i].setPiece(Core.game.pieces[i+8]);
            Core.squares[6][i].setPiece(Core.game.pieces[i+16]);
        }
        Core.squares[7][0].setPiece(Core.game.pieces[24]);
        Core.squares[7][7].setPiece(Core.game.pieces[25]);
        Core.squares[7][1].setPiece(Core.game.pieces[26]);
        Core.squares[7][6].setPiece(Core.game.pieces[27]);
        Core.squares[7][2].setPiece(Core.game.pieces[28]);
        Core.squares[7][5].setPiece(Core.game.pieces[29]);
        Core.squares[7][3].setPiece(Core.game.pieces[30]);
        Core.squares[7][4].setPiece(Core.game.pieces[31]);
    }
}