package com.caffeine.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * This customized implementation of JButton makes buttons that appear as a game square
 */
public class BoardSquare extends JButton {
	com.caffeine.logic.Piece piece = null;

	// formatting-related objects
	final Border line = new LineBorder(Color.BLACK, 0);
    final Border margin = new EmptyBorder(5, 15, 5, 15);
    final Border compound = new CompoundBorder(line, margin);
	final Dimension buttonSize = new Dimension(60,60);

    /* proprietary font that Windows/Mac have, but Linux will default to 
    a font that will still display the chess pieces */
    Font defaultFont = new Font("Arial Unicode MS", Font.PLAIN, 25);

	/**
	 * Initializes board square to the set up that appears the same across platforms
	 * Static dimension forces the button to be square
	 */ 
	public BoardSquare() {
        setOpaque(true);
        setFont(defaultFont);
        setBorder(compound);
        setMinimumSize(buttonSize);
        setMaximumSize(buttonSize);
        setPreferredSize(buttonSize);
	}
	
	/**
	 * Colors game square either white or gray
	 *
	 * @param  squareColor a Boolean to determine whether the button should be colored gray or not
	 */
	public void setBackgroundColor(boolean squareColor) {
        if (squareColor)
        {
            setBackground(Color.LIGHT_GRAY);
        }
        else
        {
            setBackground(Color.GRAY);
        }
	}

	/**
	 * Returns piece on square, or null if no piece found
	 * 
	 * @return  The Piece on the square
	 */
	public com.caffeine.logic.Piece getPiece() {
		return piece;
	}

	/**
	 * Determines if the square has a piece on it
	 *
	 * @return True if piece found, false if not
	 */
	public boolean hasPiece() {
		if (piece == null) return false;
		else return true;
	}

	/**
	 * Changes the text of the square to the Unicode chess piece
	 *
	 * @param piece the unicode chess piece 
	 */
	public void setPiece(com.caffeine.logic.Piece piece) {
		this.piece = piece;
		setText(piece.getType());
		setForeground(piece.getColor());
	}

	/**
	 * Resets the square to empty
	 */
	public void removePiece() {
		setText(" ");
	}

	/**
	 * Selects a square by setting the foreground (text) to yellow
	 */
	public void selectSquare() {
        setForeground(Color.YELLOW);
	}

	/**
	 * Returns a square to the regular foreground (text) color of the piece
	 */
	public void unselectSquare() {
        if (piece == null) return;
        setForeground(piece.getColor());
	}

	/**
	 * Changes the color of the piece 
	 * 
	 * @param color  The color to set the piece
	 */
	public void setPieceColor(Color color) {
		setForeground(color);
		piece.setColor(color);
	}

	/**
	 * When a piece is taken, this returns the taken piece
	 * and sets the square to the new piece
	 *
	 * @param piece  The piece that is taking the square
	 * @return  the piece that was taken
	 */
	public com.caffeine.logic.Piece takePiece(com.caffeine.logic.Piece piece) {
		com.caffeine.logic.Piece taken = this.piece;
		setPiece(piece);	// both sets this.piece and foreground color
		return taken;
	}
}