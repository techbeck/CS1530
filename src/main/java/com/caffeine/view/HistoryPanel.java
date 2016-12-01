package com.caffeine.view;

import com.caffeine.Chess;

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 *  This custom JPanel displays the move history.
 */
public class HistoryPanel extends JPanel {
    private JLabel historyLabel;

    public HistoryPanel() {
        setName("historyPanel");
        setBackground(Color.decode(Core.themes[0][1]));
        setMinimumSize(Core.sidePanelDimension);
        setMaximumSize(Core.sidePanelDimension);
        setPreferredSize(Core.sidePanelDimension);
        historyLabel = new JLabel("Move History");
        historyLabel.setName("historyLabel");
        historyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        historyLabel.setVerticalAlignment(SwingConstants.TOP);
        add(historyLabel);
    }

    /**
     * Updates the move history panel based on the ArrayList passed in.
     * Two moves in the ArrayList count as one full move.
     *
     * @param moveHistory  The ArrayList holding the moves to be displayed
     */
    public void updateMoveHistory(ArrayList<String> moveHistory) {
        int currMoveNum = 1;
        String currHalfMove = null;

        historyLabel.setText("<html>Move History<br>");
        for (String halfMove : moveHistory) {
            if (currHalfMove == null) {
                currHalfMove = halfMove;
            } else {
                String fullMove = currMoveNum + ". " + currHalfMove + " " + halfMove;
                historyLabel.setText(historyLabel.getText() + fullMove + "<br>");
                currMoveNum++;
                currHalfMove = null;
            }
        }
        if (currHalfMove != null) {
            String extra = currMoveNum + ". " + currHalfMove;
            historyLabel.setText(historyLabel.getText() + extra + "<br>");
        }
        historyLabel.setText(historyLabel.getText() + "</html>");
    }
	
	/**
	 * Returns historyLabel for unit testing purposes.
	 */
	 public JLabel getHistoryLabel(){
		 return historyLabel;
	 }
}
