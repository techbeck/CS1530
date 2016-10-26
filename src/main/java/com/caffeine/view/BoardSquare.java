package com.caffeine.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * This customized implementation of JButton makes buttons that appear as a game square
 */
public class BoardSquare extends JButton {
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
		setForeground(Color.BLACK);
        setOpaque(true);
        setFont(defaultFont);
        setBorder(compound);
        setMinimumSize(buttonSize);
        setMaximumSize(buttonSize);
        setPreferredSize(buttonSize);
	}
	
	/**
	 * Colors game square either white or gray
	 * @param  squareColor a Boolean to determine whether the button should be colored gray or not
	 */
	public void setColor(boolean squareColor) {
        if (squareColor)
        {
            setBackground(Color.WHITE);
        }
        else
        {
            setBackground(Color.GRAY);
        }
	}

	/**
	 * Returns piece on square, or null if no piece found
	 * 
	 * @return  The String of the Unicode character for the chess piece
	 */
	public String getPiece() {
		if (getText().equals(" ")) return null;
		else return getText();
	}

	/**
	 * Determines if the square has a piece on it
	 *
	 * @return True if piece found, false if not
	 */
	public boolean hasPiece() {
		if (getText().equals(" ")) return false;
		else return true;
	}

	/**
	 * Changes the text of the square to the Unicode chess piece
	 */
	public void setPiece(String piece) {
		setText(piece);
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
        selected.setForeground(Color.YELLOW);
	}

	/**
	 * Returns a square to having a black foreground (text)
	 */
	public void unselectSquare() {
        selected.setForeground(Color.BLACK);
	}
}