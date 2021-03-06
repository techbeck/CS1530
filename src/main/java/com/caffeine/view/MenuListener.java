package com.caffeine.view;

import com.caffeine.Chess;
import com.caffeine.view.Core;

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;

/**
 *  This customized implementation of ActionListener listens for clicks
 *  upon the main window's menu and performs the proper action
 *  given the user's choice.
 */
public class MenuListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {

        Core.timerPanel.pauseTimer();

        react(e);

        Core.timerPanel.resumeTimer();

    }

    private void react(ActionEvent e) {

        JFrame window = Core.window;

        StatusPanel statusPanel = Core.statusPanel;
        JMenuItem menuItem = (JMenuItem) e.getSource();
        String menuText = menuItem.getText();

        if (menuText.equals("Change CPU Mode")) {

            if (Chess.game.gameStarted) {
                statusPanel.setText("Can't change mode after starting a game.");
                return;
            }

            String[] options = new String[] {"Easy", "Medium", "Hard", "Cancel"};
            int mode = JOptionPane.showOptionDialog(window,
                "Please choose a mode", "Choose Mode", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            //  case -1 and 2 are X'ed out of dialog box and Cancel
            switch(mode){
                case -1:
                    return;
                case 0:
                    Chess.game.setMode(mode);
                    statusPanel.setText("Mode now Easy");
                    break;
                case 1:
                    Chess.game.setMode(mode);
                    statusPanel.setText("Mode now Medium");
                    break;
                case 2:
                    Chess.game.setMode(mode);
                    statusPanel.setText("Mode now Hard");
                    break;
                case 3:
                    return;
            }

        } else if (menuText.equals("Undo last move")) {

            statusPanel.setText("Undo last move");
            Chess.game.undoMove();

        } else if (menuText.equals("Set move timer")) {

            if (Chess.game.gameStarted) {
                statusPanel.setText("Can't change timer after starting a game.");
                return;
            }

            String[] options = new String[] {"1", "2", "3", "4", "5","10", "15",
            "20", "25", "30", "45", "60", "90"};
            String minutes = (String) JOptionPane.showInputDialog(window,
                "Time Limit (in minutes)",
                "Choose Time Limit", JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);
            if (minutes == null) return;

            Core.timerPanel.setTimer(Integer.parseInt(minutes));

            statusPanel.setText("Set timer to " + minutes + " minutes.");

        } else if (menuText.contains("Show legal moves")){

            if (menuText.contains("\u2713")){
                menuItem.setText("Show legal moves");
                statusPanel.setText("Hiding legal moves.");
                Core.showLegalMoves = false;
            } else {
                menuItem.setText("\u2713 Show legal moves");
                statusPanel.setText("Showing legal moves.");
                Core.showLegalMoves = true;
            }

        }

    }
}
