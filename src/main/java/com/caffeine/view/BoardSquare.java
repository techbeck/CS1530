package com.caffeine.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import com.caffeine.logic.Piece;
import com.caffeine.logic.Utils;

/**
 * This customized implementation of JButton makes buttons that appear as a game square
 */
public class BoardSquare extends JButton {
    Piece piece = null;
    boolean isLightSquare;

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
            setBackground(Color.decode(Core.themes[0][2]));
            isLightSquare = true;
        }
        else
        {
            setBackground(Color.decode(Core.themes[0][3]));
            isLightSquare = false;
        }
    }

    /**
     * Returns whether or not piece is light
     *
     * @return  true if light square, otherwise false
     */
    public boolean isLightSquare() {
        return isLightSquare;
    }

    /**
     * Returns piece on square, or null if no piece found
     *
     * @return  The Piece on the square
     */
    public Piece getPiece() {
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
     * @param piece - The Piece
     */
    public void setPiece(Piece piece) {
        if (piece == null) return;
        this.piece = piece;
        setText(piece.getUnicode());
        if (piece.isWhite()) {
            setForeground(Core.whiteColor);
        } else {
            setForeground(Core.blackColor);
        }
    }

    public void setPiece(Character pieceSAN){
        String validSAN = "PRNBQKprnbqk";
        if (!validSAN.contains(pieceSAN.toString())) return;
        this.piece = new Piece(pieceSAN, null);
        setText(Utils.getPieceAsUnicode(pieceSAN));
        if (Character.isUpperCase(pieceSAN)){
            setForeground(Core.whiteColor);
        }
        else {
            setForeground(Core.blackColor);
        }
    }

    /**
     * Resets the square to empty
     */
    public void removePiece() {
        setText(" ");
        piece = null;
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
        if (piece.isWhite()) {
            setForeground(Core.whiteColor);
        } else {
            setForeground(Core.blackColor);
        }
    }

    /**
     * Changes the color of the piece
     *
     * @param color  The color to set the piece
     */
    public void setPieceColor(Color color) {
        setForeground(color);
    }

    /**
     * When a piece is taken, this returns the taken piece
     * and sets the square to the new piece
     *
     * @param piece  The piece that is taking the square
     * @return  the piece that was taken
     */
    public Piece takePiece(Piece piece) {
        Piece taken = this.piece;
        setPiece(piece);    // both sets this.piece and foreground color
        return taken;
    }
}
