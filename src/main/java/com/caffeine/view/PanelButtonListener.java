package com.caffeine.view;

import com.caffeine.Chess;
import com.caffeine.logic.Game;
import com.caffeine.logic.Piece;
import com.caffeine.logic.FileManager;

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JFileChooser;
import java.awt.event.*;
import java.awt.*;


/**
 * 	This customized implementation of ActionListener listens for button clicks
 * 	upon the panel buttons and displays the appropriate dialog window
 */
public class PanelButtonListener implements ActionListener {

    JFileChooser fc = new JFileChooser("SavedGames/");

    public void actionPerformed(ActionEvent e) {

        Core.timerPanel.pauseTimer();

        react(e);

        Core.timerPanel.resumeTimer();

    }

    private void react(ActionEvent e) {

        JLabel statusLabel = Core.statusLabel;
        JFrame window = Core.window;
        BoardSquare[][] squares = Core.squares;

        JButton button = (JButton) e.getSource();

        if (button.getText().equals("Load")){

            if (Chess.game.gameStarted) {
                int dialogResult = JOptionPane.showConfirmDialog(window, "Would you like to save your current game first?","Warning", JOptionPane.YES_NO_CANCEL_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    int returnVal = fc.showSaveDialog(window);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        String fileName = fc.getSelectedFile().getName();
                        if (fileName.toLowerCase().endsWith(".pgn")){
                            statusLabel.setText("Saving game to file: " + fileName.toLowerCase());
                            FileManager.save(fileName.toLowerCase());
                        } else{
                            statusLabel.setText("Saving game to file: " + fileName.toLowerCase() + ".pgn");
                            FileManager.save(fileName.toLowerCase()+".pgn");
                        }
                    }
                } else if (dialogResult == JOptionPane.CANCEL_OPTION) {
                    return;
                }
            }

            int returnVal = fc.showOpenDialog(window);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String fileName = fc.getSelectedFile().getName();
                if (fileName.toLowerCase().endsWith(".pgn")){

                    statusLabel.setText("Loading game from file: " + fileName.toLowerCase());
                    FileManager.load(fileName.toLowerCase());

                } else{

                    statusLabel.setText("Loading game from file: " + fileName.toLowerCase() + ".pgn");
                    FileManager.load(fileName.toLowerCase()+".pgn");

                }
            }
        } else if (button.getText().equals("Save")) {

            if (Chess.game.gameStarted) {
                int returnVal = fc.showSaveDialog(window);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String fileName = fc.getSelectedFile().getName();
                    if (fileName.toLowerCase().endsWith(".pgn")){
                        statusLabel.setText("Saving game to file: " + fileName.toLowerCase());
                        FileManager.save(fileName.toLowerCase());
                    } else{
                        statusLabel.setText("Saving game to file: " + fileName.toLowerCase() + ".pgn");
                        FileManager.save(fileName.toLowerCase()+".pgn");
                    }
                }
            } else {
                statusLabel.setText("No current game to save");
            }

        } else if (button.getText().equals("New Game")) {

            if (Chess.game.gameStarted) {
                int dialogResult = JOptionPane.showConfirmDialog(window, "Would you like to save your current game first?","Warning", JOptionPane.YES_NO_CANCEL_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    int returnVal = fc.showSaveDialog(window);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        String fileName = fc.getSelectedFile().getName();
                        if (fileName.toLowerCase().endsWith(".pgn")){
                            statusLabel.setText("Saving game to file: " + fileName.toLowerCase());
                            FileManager.save(fileName.toLowerCase());
                        } else{
                            statusLabel.setText("Saving game to file: " + fileName.toLowerCase() + ".pgn");
                            FileManager.save(fileName.toLowerCase()+".pgn");
                        }
                    }
                } else if (dialogResult == JOptionPane.CANCEL_OPTION) {
                    return;
                }
            }

            String[] options = new String[] {"Black", "White", "Cancel"};
            int playerColor = JOptionPane.showOptionDialog(window, "Please choose a side", "Choose Side",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            // 	case -1 and 2 are X'ed out of dialog box and Cancel
            switch(playerColor){
                case -1:
                    return;
                case 0:
                    Chess.game = new Game();
                    Chess.game.startGame();
                    Chess.game.setSide("black");
                    statusLabel.setText("Game Started - Now playing as Black");
                    // CPU moves immediately
                    String cpuMove = Chess.game.cpuMove();
                    break;
                case 1:
                    Chess.game = new Game();
                    Chess.game.startGame();
                    Chess.game.setSide("white");
                    statusLabel.setText("Game Started - Now playing as White");
                    break;
                case 2:
                    return;
            }
            Core.timerPanel.restartTimer();
            ViewUtils.refreshBoard();
            ViewUtils.clearTakenPanel();
            ViewUtils.clearHistoryPanel();

        } else if (button.getText().equals("Tutorial")) {

            JOptionPane.showMessageDialog(window, "This is a simple chess game where you play against a CPU opponent.\n" +
                        "Click New Game and choose a side to get started playing.\n" +
                        "Simply click on a piece and then another square to move the piece to that square.\n",
                        "Tutorial", JOptionPane.PLAIN_MESSAGE);

        } else if (button.getText().equals("Change Piece Color")) {

            String[] options = new String[] {"Black", "White", "Cancel"};
            int playerColor = JOptionPane.showOptionDialog(window, "Choose a color to change", "Change Color",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            switch(playerColor) {
                case -1:
                	break;
                case 0:  // change black pieces
                    Color newColor = JColorChooser.showDialog(window,
                            "Choose Color", Color.BLACK);
                    if (newColor == null) return; // user clicked cancel

                    // Can't have both sides same color
                    if (newColor.equals(Core.whiteColor)) {
                        statusLabel.setText("Can't have both sides same color");
                        return;
                    }

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
                    Core.blackColor = newColor;
                    Core.takenPanel.setCaptByWhiteColor();
                    break;
                case 1:  // change white pieces
                    newColor = JColorChooser.showDialog(window,
                            "Choose Color", Color.WHITE);
                    if (newColor == null) return; // user clicked cancel

                    // Can't have both sides same color
                    if (newColor.equals(Core.blackColor)) {
                        statusLabel.setText("Can't have both sides same color");
                        return;
                    }

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
                    Core.whiteColor = newColor;
                    Core.takenPanel.setCaptByBlackColor();
                    break;
                case 2:
                	break;
            }
        } else if (button.getText().equals("Change Color Theme")) {

            String[] options = new String[] {"Grayscale", "Peppermint", "Shamrock", "Pumpkin Spice", "Iced Vanilla"};
            String theme = (String) JOptionPane.showInputDialog(window, "Choose Theme",
                    "Choose Color Theme", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (theme == null) return;

            int themeChoice = 0;
            //if (themeChoice == Core.currentTheme) return;

            if (theme.equals("Grayscale")) themeChoice = 0;
            else if (theme.equals("Peppermint")) themeChoice = 1;
            else if (theme.equals("Shamrock")) themeChoice = 2;
            else if (theme.equals("Pumpkin Spice")) themeChoice = 3;
            else if (theme.equals("Iced Vanilla")) themeChoice = 4;
            else return;

            Core.currentTheme = themeChoice;
            statusLabel.setText("Changed color theme to " + theme);

            Color backCol = Color.decode(Core.themes[Core.currentTheme][0]);
            Color panelCol = Color.decode(Core.themes[Core.currentTheme][1]);
            Color lightCol = Color.decode(Core.themes[Core.currentTheme][2]);
            Color darkCol = Color.decode(Core.themes[Core.currentTheme][3]);

            window.getContentPane().setBackground(backCol);
            Core.centerPanel.setBackground(backCol);
            Core.historyPanel.setBackground(panelCol);
            Core.takenPanel.setBackground(panelCol);
            Core.timerPanel.setBackground(panelCol);
            Core.buttonPanel.setBackground(panelCol);
            Core.statusPanel.setBackground(panelCol);

            Core.boardPanel.setBackground(backCol);
            for (int i = 0; i < 8; i++) {   // iterate through all rows and columns of board
                for (int j = 0; j < 8; j++) {
                    BoardSquare square = squares[i][j];
                    if (square.isLightSquare()) {
                        square.setBackground(lightCol);
                    } else {
                        square.setBackground(darkCol);
                    }
                }
            }
        } else if (button.getText().equals("Flip the Board")) {
            BoardPanel replacement = null;
            if (Core.boardPanel.blackOnTop()) {
                replacement = new BoardPanel("white");
            } else {
                replacement = new BoardPanel("black");
            }
            Container center = Core.boardPanel.getParent();
            Component[] components = center.getComponents();
            Component timer = components[0];
            Component buttons = components[2];
            center.removeAll();
            center.add(timer);
            center.add(replacement);
            center.add(buttons);
            center.validate();
            center.repaint();
            Core.boardPanel = replacement;
            BoardListener.selected = null;

            // Place pieces in correct board locations
            for (Piece p : Chess.game.pieces) {
                int rank = p.getRank();
                int file = p.getFile();
                if (rank == -1) continue;
                Core.squares[7-rank][file].setPiece(p);
            }

            // Redo previously done theme change
            if (Core.currentTheme != 0) {
                Color backCol = Color.decode(Core.themes[Core.currentTheme][0]);
                Color lightCol = Color.decode(Core.themes[Core.currentTheme][2]);
                Color darkCol = Color.decode(Core.themes[Core.currentTheme][3]);
                Core.boardPanel.setBackground(backCol);
                for (int i = 0; i < 8; i++) {   // iterate through all rows and columns of board
                    for (int j = 0; j < 8; j++) {
                        BoardSquare square = squares[i][j];
                        if (square.isLightSquare()) {
                            square.setBackground(lightCol);
                        } else {
                            square.setBackground(darkCol);
                        }
                    }
                }
            }

            // Also flip taken pieces panels
            Component[] takenComponents = Core.takenPanel.getComponents();
            Core.takenPanel.removeAll();
            Core.takenPanel.add(takenComponents[0]);
            Core.takenPanel.add(takenComponents[2]);
            Core.takenPanel.add(takenComponents[1]);
        }
    }
}
