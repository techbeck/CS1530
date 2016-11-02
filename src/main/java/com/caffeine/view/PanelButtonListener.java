package com.caffeine.view;

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;


/**
 * 	This customized implementation of ActionListener listens for button clicks
 * 	upon the panel buttons and displays the appropriate dialog window
 */
public class PanelButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        JLabel statusLabel = com.caffeine.view.Core.statusLabel;
        JFrame window = com.caffeine.view.Core.window;
        BoardSquare[][] squares = com.caffeine.view.Core.squares;

        JButton button = (JButton) e.getSource();

        if (button.getText().equals("Load")){

            String fileName = JOptionPane.showInputDialog(window, "Please enter file name to load a game: ", "Load Game", JOptionPane.PLAIN_MESSAGE);

            if (fileName != null && fileName.length() != 0) {

                if (fileName.toLowerCase().endsWith(".pgn")){

                    statusLabel.setText("[Upcoming Feature] - Loading game from file: " + fileName);

                } else{

                    statusLabel.setText("[Upcoming Feature] - Loading game from file: " + fileName + ".pgn");

                }
            }
        } else if (button.getText().equals("Save")) {

            String fileName = JOptionPane.showInputDialog(window, "Please enter a file name to save your game: ", "Save Game", JOptionPane.PLAIN_MESSAGE);

            if (fileName != null && fileName.length() != 0) {

                statusLabel.setText("[Upcoming Feature] - Saving game to file: " + fileName + ".pgn");

            }
        } else if (button.getText().equals("Choose Side")) {

            String[] options = new String[] {"Black", "White", "Cancel"};
            int playerColor = JOptionPane.showOptionDialog(window, "Please choose a side", "Choose Side",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            switch(playerColor){
                case -1: break;
                case 0: statusLabel.setText("[Upcoming Feature] - Now playing as Black");
                break;
                case 1: statusLabel.setText("[Upcoming Feature] - Now playing as White");
                break;
                case 2: break;
            }
        } else if (button.getText().equals("Tutorial")) {

            JOptionPane.showMessageDialog(window, "This is a simple walking skeleton, but does have some basic functionality.\n" +
                "Simply click on a piece and then another tile to move the piece to that tile.", "Tutorial", JOptionPane.PLAIN_MESSAGE);

        } else if (button.getText().equals("Change Piece Color")) {

            String[] options = new String[] {"Black", "White", "Cancel"};
            int playerColor = JOptionPane.showOptionDialog(window, "Choose a color to change", "Change Color",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            switch(playerColor) {
                case -1: break;
                case 0:  // change black pieces
                    Color newColor = JColorChooser.showDialog(window,
                            "Choose Color", Color.BLACK);
                    statusLabel.setText("Changed color of black pieces");
                    for (int i = 0; i < 8; i++) {   // iterate through all rows and columns of board
                        for (int j = 0; j < 8; j++) {
                            // Change color of only black pieces
                            BoardSquare square = squares[i][j];
                            if (square.getPiece() != null && !square.getPiece().isWhite()) {
                                square.setPieceColor(newColor);
                            }
                        }
                    }
                    break;
                case 1:  // change white pieces
                    newColor = JColorChooser.showDialog(window,
                            "Choose Color", Color.WHITE);
                    statusLabel.setText("Changed color of white pieces");
                    for (int i = 0; i < 8; i++) {   // iterate through all rows and columns of board
                        for (int j = 0; j < 8; j++) {
                            // Change color of only white pieces
                            BoardSquare square = squares[i][j];
                            if (square.getPiece() != null && square.getPiece().isWhite()) {
                                square.setPieceColor(newColor);
                            }
                        }
                    }
                    break;
                case 2: break;
            }
        } else if (button.getText().equals("Change Square Color")) {

            String[] options = new String[] {"Dark", "Light", "Cancel"};
            int playerColor = JOptionPane.showOptionDialog(window, "Choose squares to change", "Change Color",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            switch(playerColor) {
                case -1: break;
                case 0:  // change dark squares
                    Color newColor = JColorChooser.showDialog(window,
                            "Choose Color", Color.GRAY);
                    statusLabel.setText("Changed color of dark squares");
                    for (int i = 0; i < 8; i++) {   // iterate through all rows and columns of board
                        for (int j = 0; j < 8; j++) {
                            // Change color of only black pieces
                            BoardSquare square = squares[i][j];
                            if (!square.isLightSquare()) {
                                square.setBackground(newColor);
                            }
                        }
                    }
                    break;
                case 1:  // change light squares
                    newColor = JColorChooser.showDialog(window,
                            "Choose Color", Color.LIGHT_GRAY);
                    statusLabel.setText("Changed color of light squares");
                    for (int i = 0; i < 8; i++) {   // iterate through all rows and columns of board
                        for (int j = 0; j < 8; j++) {
                            // Change color of only white pieces
                            BoardSquare square = squares[i][j];
                            if (square.isLightSquare()) {
                                square.setBackground(newColor);
                            }
                        }
                    }
                    break;
                case 2: break;
            }
        }
    }
}
