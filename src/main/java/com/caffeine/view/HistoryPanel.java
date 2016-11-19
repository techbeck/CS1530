package com.caffeine.view;

import com.caffeine.Chess;

import java.util.*; 
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 * 	This JPanel displays the move history.
 */
public class HistoryPanel extends JPanel {
	private JLabel historylabel;
	private JLabel moveHistoryLabel = new JLabel();

	private String currHalfMove;
	private int currMoveNum = 0;

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

	public void updateMoveHistory() {
		ArrayList<String> moveHistory = Chess.game.moveHistory;
		moveHistoryLabel.setText("<html>");
		for (String halfMove : moveHistory) {
			if (currHalfMove == null) {
				currHalfMove = halfMove;
			} else {
				String fullMove = currMoveNum + ". " + currHalfMove + " " + halfMove;
				moveHistoryLabel.setText(moveHistoryLabel.getText() + fullMove + "<br>");
				currMoveNum++;
			}
		}
		moveHistoryLabel.setText(moveHistoryLabel.getText() + "</html>");
	}
}