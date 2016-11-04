package com.caffeine.view;

import com.caffeine.logic.Piece;

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class BoardPanel extends JPanel {
	public boolean blackOnTop;
	public BoardSquare[][] squares = Core.squares;
	
	public BoardPanel() {
		blackOnTop = true;
		formatBoard();
		initializePiecePlacement();
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
	 * Flips the board
	 */
	public void flip() {

	}

	/**
     * 	Initializes an 8x8 Array of buttons to serve as the squares
     * 	for the chess board, along with grid notation.
     *
     *  @param boardPanel the JPanel upon which to place the game squares on
     */
    private void formatBoard() {

        setLayout(new GridLayout(9,9));
        BoardListener boardListener = new BoardListener();
        boolean isWhiteSquare = true;

        for (byte i = 0; i < 8; i++) {

            // grid notation row name
            JLabel label = new JLabel(String.valueOf(8-i), SwingConstants.CENTER);
            add(label);

            //row of buttons
            for (byte j = 0; j < 8; j++) {
                
                squares[i][j] = new BoardSquare();
                squares[i][j].setBackgroundColor(isWhiteSquare);
                squares[i][j].setName("BoardSquare:" + (char)(j+65) + "," + (8-i));
                squares[i][j].addActionListener(boardListener);
                add(squares[i][j]);
                isWhiteSquare = !isWhiteSquare;
            }

            isWhiteSquare = !isWhiteSquare; // creates the checkered pattern of squares
        }

        // grid notation column names
        for (byte i = 0; i < 8; i++) {

            JLabel notationColumn = new JLabel(String.valueOf((char)(i+65)), SwingConstants.CENTER);
            add(notationColumn);
        }

    }

    /** 
     * Initializes board layout with black pieces on top
     */
    public void initializePiecePlacement() {
        squares[0][7].setPiece(new Piece(Core.rook, 0, "black"));
        squares[0][0].setPiece(new Piece(Core.rook, 1, "black"));
        squares[0][1].setPiece(new Piece(Core.knight, 0, "black"));
        squares[0][6].setPiece(new Piece(Core.knight, 1, "black"));
        squares[0][2].setPiece(new Piece(Core.bishop, 0, "black"));
        squares[0][5].setPiece(new Piece(Core.bishop, 1, "black"));
        squares[0][3].setPiece(new Piece(Core.queen, 0, "black"));
        squares[0][4].setPiece(new Piece(Core.king, 0, "black"));

        for (int i = 0; i < 8; i++) {
            squares[1][i].setPiece(new Piece(Core.pawn, i, "black"));
            squares[6][i].setPiece(new Piece(Core.pawn, i, "white"));
        }

        squares[7][0].setPiece(new Piece(Core.rook, 0, "white"));
        squares[7][7].setPiece(new Piece(Core.rook, 1, "white"));
        squares[7][1].setPiece(new Piece(Core.knight, 0, "white"));
        squares[7][6].setPiece(new Piece(Core.knight, 1, "white"));
        squares[7][2].setPiece(new Piece(Core.bishop, 0, "white"));
        squares[7][5].setPiece(new Piece(Core.bishop, 1, "white"));
        squares[7][3].setPiece(new Piece(Core.queen, 0, "white"));
        squares[7][4].setPiece(new Piece(Core.king, 0, "white"));
    }
}