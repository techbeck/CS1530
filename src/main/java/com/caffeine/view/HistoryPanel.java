package com.caffeine.view;

import com.caffeine.Chess;

import java.util.*; 
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 * 	This custom JPanel displays the move history.
 */
public class HistoryPanel extends JPanel {
	private JLabel historylabel;
	private JLabel moveHistoryLabel = new JLabel();

	public HistoryPanel() {
        setName("historyPanel");
        setBackground(Color.decode(Core.themes[0][1]));
        setMinimumSize(Core.sidePanelDimension);
        setMaximumSize(Core.sidePanelDimension);
        setPreferredSize(Core.sidePanelDimension);
        historylabel = new JLabel("Move History", SwingConstants.CENTER);
        historylabel.setName("historyLabel");
        add(historylabel);
        moveHistoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        moveHistoryLabel.setVerticalAlignment(SwingConstants.TOP);
        add(moveHistoryLabel);
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

		moveHistoryLabel.setText("<html>");
		for (String halfMove : moveHistory) {
			if (currHalfMove == null) {
				currHalfMove = halfMove;
			} else {
				String fullMove = currMoveNum + ". " + currHalfMove + " " + halfMove;
				moveHistoryLabel.setText(moveHistoryLabel.getText() + fullMove + "<br>");
				currMoveNum++;
				currHalfMove = null;
			}
		}
		moveHistoryLabel.setText(moveHistoryLabel.getText() + "</html>");
	}
}