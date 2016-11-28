package com.caffeine.view;

import com.caffeine.Chess;

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;

/**
 * 	This customized implementation of ActionListener listens for clicks upon the main window's
 * 	menu and performs the proper action given the user's choice.
 */
public class MenuListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        JFrame window = Core.window;

        JLabel statusLabel = com.caffeine.view.Core.statusLabel;
        JMenuItem menuItem = (JMenuItem) e.getSource();

        if (menuItem.getText().equals("Change CPU Mode")) {

            if (Chess.game.gameStarted) {
                statusLabel.setText("Can't change mode after starting game.");
                return;
            }

            String[] options = new String[] {"Easy", "Medium", "Hard", "Cancel"};
            int mode = JOptionPane.showOptionDialog(window, "Please choose a mode", "Choose Mode",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            //  case -1 and 2 are X'ed out of dialog box and Cancel
            switch(mode){
                case -1:
                    return;
                case 0:
                    Chess.game.setMode(mode);
                    statusLabel.setText("Mode now Easy");
                    break;
                case 1:
                    Chess.game.setMode(mode);
                    statusLabel.setText("Mode now Medium");
                    break;
                case 2:
                    Chess.game.setMode(mode);
                    statusLabel.setText("Mode now Hard");
                    break;
                case 3:
                    return;
            }

        } else if (menuItem.getText().equals("Undo last move")) {

            statusLabel.setText("Undo last move");
            Chess.game.undoMove();

        } else if (menuItem.getText().equals("Set move timer")) {

            statusLabel.setText("[Upcoming Feature] - Set move timer");

        }
    }
}
