package com.caffeine.view;

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

        JLabel statusLabel = com.caffeine.view.Core.statusLabel;
        JMenuItem menuItem = (JMenuItem) e.getSource();
        
        if (menuItem.getText().equals("Set game timer")) {

            statusLabel.setText("[Upcoming Feature] - Set game timer");

        } else if (menuItem.getText().equals("Set move timer")) {

            statusLabel.setText("[Upcoming Feature] - Set move timer");

        } else if (menuItem.getText().equals("Undo last move")) {

            statusLabel.setText("[Upcoming Feature] - Undo last move");

        } else if (menuItem.getText().equals("Show possible moves")) {

            statusLabel.setText("[Upcoming Feature] - Show possible moves");

        }
    }
}