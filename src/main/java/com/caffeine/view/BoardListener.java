package com.caffeine.view;

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;

/**
 * 	This ActionListener listens for clicks upon the board and first saves
 * 	the chess icon of the square clicked (if there is one), then sets the text
 * 	of the next clicked square to the stored icon.
 */
public class BoardListener implements ActionListener {

    static JButton selected = null;
    public void actionPerformed(ActionEvent e) {

        JButton squareButton = (JButton) e.getSource();
        if (selected == null) {
        	// if no previous chess piece stored, save the one clicked
        	
            if (!squareButton.getText().equals(" ")) {

                selected = squareButton;
                selected.setForeground(Color.YELLOW);

            } else {

                // no previously selected button and button clicked is empty
                return;
            }
        } else {
        	// else set the clicked square to the saved chess piece
        	
            String text = selected.getText();
            selected.setForeground(Color.BLACK);
            selected.setText(" ");
            squareButton.setText(text);
            selected = null;
        }
    }
}