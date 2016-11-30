package com.caffeine.view;

import com.caffeine.Chess;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    JLabel statusLabel = new JLabel("Status Bar");

    String oldStatus = null;
    String newStatus = "Status Bar";
    String turn = "White's turn";
    String spacer = "          |           ";

    public StatusPanel() {
        setName("statusPanel");
        setBackground(Color.decode(Core.themes[0][1]));
        Dimension statusPanelSize = new Dimension(800,30);
        setMinimumSize(statusPanelSize);
        setMaximumSize(statusPanelSize);
        setPreferredSize(statusPanelSize);
        statusLabel.setName("statusLabel");
        add(statusLabel, SwingConstants.CENTER);
    }

    public void setText(String text) {
        oldStatus = newStatus;
        newStatus = text;
        statusLabel.setText(oldStatus + spacer + newStatus + spacer + turn);
    }
    
}
