package com.caffeine.view;

import java.util.ArrayList;

import com.caffeine.Chess;
import com.caffeine.view.Core;
import com.caffeine.logic.Piece;
import com.caffeine.logic.Utils;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 *  This customized implementation of JButton makes buttons that appear as a game square
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
     *  Initializes board square to the set up that appears the same across platforms.
     *  Static dimension forces the button to be square
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
     *  Colors game square either white or gray
     *
     *  @param  squareColor A Boolean to determine whether the button should be colored gray or not
     */
    public void setBackgroundColor(boolean squareColor) {
        if (squareColor)
        {
            setBackground(Color.decode(Core.themes[Core.currentTheme][2]));
            isLightSquare = true;
        }
        else
        {
            setBackground(Color.decode(Core.themes[Core.currentTheme][3]));
            isLightSquare = false;
        }
    }

    /**
     *  Returns whether or not piece is light
     *
     *  @return  true if light square, otherwise false
     */
    public boolean isLightSquare() {
        return isLightSquare;
    }

    /**
     *  Returns piece on square, or null if no piece found
     *
     *  @return  The Piece on the square
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     *  Determines if the square has a piece on it
     *
     *  @return True if piece found, false if not
     */
    public boolean hasPiece() {
        if (piece == null) return false;
        else return true;
    }

    /**
     *  Changes the text of the square to a chess piece's unicode character
     *
     *  @param piece The chess piece
     */
    public void setPiece(Piece piece) {
        if (piece == null) return;
        this.piece = piece;
        setText(piece.getType());
        if (piece.isWhite()) {
            setForeground(Core.whiteColor);
        } else {
            setForeground(Core.blackColor);
        }
    }

    /**
     *  Resets the square to empty
     */
    public void removePiece() {
        setText(" ");
        piece = null;
    }

    /**
     *  Changes the text color of a square to the indicated color.
     */
    public void highlightSquareText(Color newColor){
        setForeground(newColor);
    }

    /**
     *  Changes the background color of a square to the indicated color.
     */
    public void highlightSquareBackground(Color newColor){
        setBackground(newColor);
    }

    /**
     *  Selects a square by setting the foreground (text) to yellow
     */
    public void selectSquare() {
        highlightSquareText(Color.YELLOW);
    }

    /**
     *  Visually indicates that a square is a valid destination for a Piece.
     */
    public void indicateValidDestination(){
        if (isLightSquare()){
            highlightSquareBackground(Color.GREEN);
        } else {
            highlightSquareBackground(Color.GREEN.darker());
        }
    }

    /**
     *  Removes new colors from a square, returning it to its default (background) color.
     */
    public void resetSquare(){
        if (isLightSquare()){
            setBackground(Color.decode(Core.themes[Core.currentTheme][2]));
        } else {
            setBackground(Color.decode(Core.themes[Core.currentTheme][3]));
        }
    }

    /**
     *  Returns a square to the regular foreground (text) color of the piece
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
     *  Changes the color of the piece
     *
     *  @param color The color to set the piece
     */
    public void setPieceColor(Color color) {
        setForeground(color);
    }

    /**
     *  Determines (based on the Piece at this square) which other squares on
     *      the board are eligible destinations.
     *
     *  @return ArrayList of Strings denoting positions this square's Piece
     *      can move to. List will be empty if
     */
    public ArrayList<String> getPossibleMoves(){
        ArrayList<String> result, tmpDest;
        String castling, src;
        int rank, file;
        Piece piece;

        result = new ArrayList<String>();

        if (!hasPiece()){ return result; }

        piece = getPiece();
        rank = piece.getRank();
        file = piece.getFile();

        tmpDest = new ArrayList<String>();
        castling = Chess.engine.getFEN().split(" ")[3];
        switch (piece.getType()){
        case Core.king:
            // King can move 1 space in any direction.
            tmpDest.add(Utils.translate(rank-1, file-1));
            tmpDest.add(Utils.translate(rank-1, file+1));
            tmpDest.add(Utils.translate(rank-1, file));
            tmpDest.add(Utils.translate(rank, file-1));
            tmpDest.add(Utils.translate(rank, file+1));
            tmpDest.add(Utils.translate(rank+1, file-1));
            tmpDest.add(Utils.translate(rank+1, file+1));
            tmpDest.add(Utils.translate(rank+1, file));
            // Special Case: Castling
            if (castling.equals("-")){ break; }
            else if (piece.isWhite()){
                if (castling.contains("K")){ tmpDest.add(Utils.translate(rank, file+2)); }
                if (castling.contains("Q")){ tmpDest.add(Utils.translate(rank, file-2)); }
            }
            else if (!piece.isWhite()){
                if (castling.contains("k")){ tmpDest.add(Utils.translate(rank, file+2)); }
                if (castling.contains("q")){ tmpDest.add(Utils.translate(rank, file-2)); }
            }
            break;
        case Core.queen:
            // Queen can move N spaces in any one direction.
            for (int i = 1; i <= 7; i++){
                tmpDest.add(Utils.translate(rank-i, file)); // N
                tmpDest.add(Utils.translate(rank, file+i)); // E
                tmpDest.add(Utils.translate(rank, file-i)); // W
                tmpDest.add(Utils.translate(rank+i, file)); // S
                tmpDest.add(Utils.translate(rank-i, file-i)); // NW
                tmpDest.add(Utils.translate(rank-i, file+i)); // NE
                tmpDest.add(Utils.translate(rank+i, file-i)); // SW
                tmpDest.add(Utils.translate(rank+i, file+i)); // SE
            }
            break;
        case Core.rook:
            // Rook can move N spaces horizontally or vertically.
            for (int i = 1; i <= 7; i++){
                tmpDest.add(Utils.translate(rank-i, file)); // N
                tmpDest.add(Utils.translate(rank, file+i)); // E
                tmpDest.add(Utils.translate(rank, file-i)); // W
                tmpDest.add(Utils.translate(rank+i, file)); // S
            }
            break;
        case Core.bishop:
            // Bishop can move N spaces diagonally.
            for (int i = 1; i <= 7; i++){
                tmpDest.add(Utils.translate(rank-i, file-i)); // NW
                tmpDest.add(Utils.translate(rank-i, file+i)); // NE
                tmpDest.add(Utils.translate(rank+i, file-i)); // SW
                tmpDest.add(Utils.translate(rank+i, file+i)); // SE
            }
            break;
        case Core.knight:
            // Knight can move:
            //     2 Horizontally and 1 Vertically, or
            //     2 Vertically and 1 Horizontally.
            tmpDest.add(Utils.translate(rank+2, file+1));
            tmpDest.add(Utils.translate(rank+2, file-1));
            tmpDest.add(Utils.translate(rank+1, file+2));
            tmpDest.add(Utils.translate(rank+1, file-2));
            tmpDest.add(Utils.translate(rank-2, file+1));
            tmpDest.add(Utils.translate(rank-2, file-1));
            tmpDest.add(Utils.translate(rank-1, file+2));
            tmpDest.add(Utils.translate(rank-1, file-2));
            break;
        case Core.pawn:
            // Pawn can move 1 space "forward" or take diagonal pieces.
            // Note: Accidentally handles EnPassant, so good.
            if (piece.isWhite()){
                tmpDest.add(Utils.translate(rank+1, file));
                tmpDest.add(Utils.translate(rank+1, file-1));
                tmpDest.add(Utils.translate(rank+1, file+1));
                // Special Case: In default spot.
                if (rank == 1){ tmpDest.add(Utils.translate(rank+2, file)); }
            } else {
                tmpDest.add(Utils.translate(rank-1, file));
                tmpDest.add(Utils.translate(rank-1, file-1));
                tmpDest.add(Utils.translate(rank-1, file+1));
                // Special Case: In default spot.
                if (rank == 6){ tmpDest.add(Utils.translate(rank+2, file)); }
            }
            break;
        default:
            break;
        }

        src = Utils.translate(rank, file);
        for (String d : tmpDest){
            if (Chess.game.tryMove(src, d)){ result.add(d); }
        }

        return result;
    }
}
