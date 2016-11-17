package com.caffeine.view;

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import com.caffeine.Chess;
import com.caffeine.logic.Utils;

public class BoardPanel extends JPanel {

    private boolean orientationFlipped;

    // Default Constructor
    public BoardPanel(boolean flipped){
        setName("boardPanel");
        setBackground(Color.decode(Core.themes[0][0]));
        orientationFlipped = flipped;
        drawBoard();
    }

    // Shorthand Constructor
    public BoardPanel(){
        this(false);
    }



    // Public Methods
    public boolean isFlipped(){
        return orientationFlipped;
    }



    // Private Methods
    private void drawBoard(){
        // 1. Get a String of length 64 representing the board. Represents each
        //    piece using Standard Algebraic Notation (PRNBQKprnbrqk).
        // 2. Initialize and configure the BoardPanel.
        // 3. Iterate over String, creating new BoardSquares as we do.
        //        If board orientation is normal (A1 at bottom left), iterate forward over String.
        //        If board orientation is flipped (A1 top right), iterate backward over String.

        // Get Board as String, turn into an Array.
        String boardString = Chess.game.getBoardAsString();

        // Prepare BoardPanel.
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        BoardListener boardListener = new BoardListener();
        boolean isWhiteSquare = true;

        // Create 8x8 Array representation of the Board based on our rowStrings.
        //     Top Left square is A8 ([0,0]).
        //     Bottom Right square is H1 ([8,8]).
        // Each piece marked by the SAN equivalent.
        // Empty board squares represented by '-'.
        if (isFlipped()){

            // Iterate over String backwards.
            for (int i = 7; i >= 0; i--){
                // Configure Row
                JLabel notationRow = new JLabel(String.valueOf( 8 - i ), SwingConstants.CENTER);
                c.fill = GridBagConstraints.BOTH;
                c.gridx = 0;
                c.gridy = 8 - ( i + 1 );
                c.insets = Core.sidePadding;
                c.weightx = Core.AVERAGE_WEIGHT;
                c.weighty = Core.AVERAGE_WEIGHT;
                add(notationRow, c);
                c.insets = Core.noPadding;
                // Configure each BoardSquare and add it.
                for (int j = 7; j >= 0; j--){
                    int boardStringIdx = (i * 8) + j;
                    Core.squares[i][j] = new BoardSquare();
                    Core.squares[i][j].setBackgroundColor(isWhiteSquare);
                    Core.squares[i][j].setName("BoardSquare:" + Utils.translateCoordinate(new Integer[]{i, j}));
                    Core.squares[i][j].addActionListener(boardListener);
                    Core.squares[i][j].setPiece(boardString.charAt(boardStringIdx)); // SAN notation
                    c.fill = GridBagConstraints.BOTH;
                    c.gridx = 8 - j;
                    c.gridy = 8 - ( i + 1 );
                    c.weightx = Core.AVERAGE_WEIGHT;
                    c.weighty = Core.AVERAGE_WEIGHT;
                    add(Core.squares[i][j], c);
                    isWhiteSquare = !isWhiteSquare;
                }
            }

            // Last row are the board's File markers (A, B, C, ..., H).
            for (int i = 7; i >= 0; i--) {
                JLabel notationColumn = new JLabel(String.valueOf((char)( i + 65 )), SwingConstants.CENTER);
                c.fill = GridBagConstraints.BOTH;
                c.gridx = 8 - i;
                c.gridy = 8;
                c.insets = Core.topBottomPadding;
                c.weightx = Core.AVERAGE_WEIGHT;
                c.weighty = Core.AVERAGE_WEIGHT;
                add(notationColumn, c);
            }

        } else {

            // Iterate over String forwards.
            for (int i = 0; i <= 7; i++){
                // Row Labelling
                JLabel notationRow = new JLabel(String.valueOf( 8 - i ), SwingConstants.CENTER);
                c.fill = GridBagConstraints.BOTH;
                c.gridx = 0;
                c.gridy = i;
                c.insets = Core.sidePadding;
                c.weightx = Core.AVERAGE_WEIGHT;
                c.weighty = Core.AVERAGE_WEIGHT;
                add(notationRow, c);
                c.insets = Core.noPadding;
                for (int j = 0; j <= 7; j++){
                    int boardStringIdx = (i * 8) + j;
                    Core.squares[i][j] = new BoardSquare();
                    Core.squares[i][j].setBackgroundColor(isWhiteSquare);
                    Core.squares[i][j].setName("BoardSquare:" + Utils.translateCoordinate(new Integer[]{i, j}));
                    Core.squares[i][j].addActionListener(boardListener);
                    Core.squares[i][j].setPiece(boardString.charAt(boardStringIdx)); // SAN notation
                    c.fill = GridBagConstraints.BOTH;
                    c.gridx = j + 1;
                    c.gridy = i;
                    c.weightx = Core.AVERAGE_WEIGHT;
                    c.weighty = Core.AVERAGE_WEIGHT;
                    add(Core.squares[i][j], c);
                    isWhiteSquare = !isWhiteSquare;
                }
            }

            // Last row are the board's File markers (A, B, C, ..., H).
            for (int i = 0; i <= 7; i++) {
                JLabel notationColumn = new JLabel(String.valueOf((char)(i + 65)), SwingConstants.CENTER);
                c.fill = GridBagConstraints.BOTH;
                c.gridx = i + 1;
                c.gridy = 8;
                c.insets = Core.topBottomPadding;
                c.weightx = Core.AVERAGE_WEIGHT;
                c.weighty = Core.AVERAGE_WEIGHT;
                add(notationColumn, c);
            }

        }
    }
}
