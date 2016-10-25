package com.caffeine.view;

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;

public class BoardListener implements ActionListener {
    static JButton selected = null;
    public void actionPerformed(ActionEvent e) {
        JButton squareButton = (JButton) e.getSource();
        if (selected == null) {
            if (!squareButton.getText().equals(" ")) {
                selected = squareButton;
                selected.setForeground(Color.YELLOW);
            } else {
                // no selected button and button clicked is empty
                return;
            }
        } else {
            String text = selected.getText();
            selected.setForeground(Color.BLACK);
            selected.setText(" ");
            squareButton.setText(text);
            selected = null;
        }
    }
}