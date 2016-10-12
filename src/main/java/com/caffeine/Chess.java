package com.caffeine;

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;

public class Chess {
    private JButton selected = null;

    public static void main(String[] args){
        new Chess();
    }

    String whiteKing = "\u2654";
    String whiteQueen = "\u2655";
    String whiteBishop = "\u2656";
    String whiteKnight = "\u2657";
    String whiteRook = "\u2654";
    String whitePawn = "\u2659";

    String blackKing = "\u265A";
    String blackQueen = "\u265B";
    String blackBishop = "\u265C";
    String blackKnight = "\u265D";
    String blackRook = "\u265E";
    String blackPawn = "\u265F";

    // remove this later. I just don't want to fix tests.
    public static String echo(String phrase){
        return(phrase);
    }

    public Chess() {
        JFrame window = new JFrame("Laboon Chess");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMainPanels(window);
        window.pack();
        window.setVisible(true);
    }

    private void addMainPanels(JFrame window) {
        Container pane = window.getContentPane();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.WHITE);
        Dimension panel1Size = new Dimension(150,500);
        panel1.setMinimumSize(panel1Size);
        panel1.setMaximumSize(panel1Size);
        panel1.setPreferredSize(panel1Size);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 12;
        c.insets = new Insets(0,5,0,5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        panel1.add(new JLabel("Move History", SwingConstants.CENTER));
        pane.add(panel1, c);

        JPanel panel2 = new JPanel();
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 6;
        c.gridheight = 12;
        c.insets = new Insets(0,0,0,0);
        c.weightx = 0.7;
        c.weighty = 0.5;
        formatCenterPanel(panel2);
        pane.add(panel2, c);

        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.WHITE);
        Dimension panel3Size = new Dimension(150,500);
        panel3.setMinimumSize(panel3Size);
        panel3.setMaximumSize(panel3Size);
        panel3.setPreferredSize(panel3Size);
        c.gridx = 7;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 12;
        c.insets = new Insets(0,5,0,5);
        c.weightx = 0.5;
        c.weighty = 0.5;
        panel3.add(new JLabel("Taken Pieces", SwingConstants.CENTER));
        pane.add(panel3, c);

        JPanel panel4 = new JPanel();
        panel4.setBackground(Color.WHITE);
        Dimension panel4Size = new Dimension(800,30);
        panel4.setMinimumSize(panel4Size);
        panel4.setMaximumSize(panel4Size);
        panel4.setPreferredSize(panel4Size);
        c.gridx = 0;
        c.gridy = 12;
        c.gridwidth = 8;
        c.gridheight = 1;
        c.insets = new Insets(5,0,5,0);
        c.weightx = 0.4;
        c.weighty = 0.4;
        panel4.add(new JLabel("Status bar", SwingConstants.CENTER));
        pane.add(panel4, c);
    }

    private void formatCenterPanel(JPanel panel2) {
        panel2.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel panel2A = new JPanel();
        panel2A.setBackground(Color.WHITE);
        Dimension panel2ASize = new Dimension(200,40);
        panel2A.setMinimumSize(panel2ASize);
        panel2A.setMaximumSize(panel2ASize);
        panel2A.setPreferredSize(panel2ASize);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 13;
        c.gridheight = 3;
        c.insets = new Insets(5,0,5,0);
        c.weightx = 0.5;
        c.weighty = 0.5;
        panel2A.add(new JLabel("timer goes here", SwingConstants.CENTER));
        panel2.add(panel2A, c);

        JPanel panel2B = new JPanel();
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 13;
        c.gridheight = 12;
        c.insets = new Insets(0,0,0,0);
        c.weightx = 0.5;
        c.weighty = 0.5;
        formatBoard(panel2B);
        panel2.add(panel2B, c);

        JPanel panel2C = new JPanel();
        panel2C.setBackground(Color.WHITE);
        Dimension panel2CSize = new Dimension(500,70);
        panel2C.setMinimumSize(panel2CSize);
        panel2C.setMaximumSize(panel2CSize);
        panel2C.setPreferredSize(panel2CSize);
        c.gridx = 0;
        c.gridy = 15;
        c.gridwidth = 13;
        c.gridheight = 4;
        c.insets = new Insets(5,0,5,0);
        c.weightx = 0.5;
        c.weighty = 0.5;
        // panel2C.add(new JLabel("buttons go here", SwingConstants.CENTER));
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new PanelButtonListener());
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new PanelButtonListener());
        JButton chooseSiteButton = new JButton("Choose Side");
        chooseSiteButton.addActionListener(new PanelButtonListener());
        JButton tutorialButton = new JButton("Tutorial");
        tutorialButton.addActionListener(new PanelButtonListener());
        panel2C.add(loadButton);
        panel2C.add(saveButton);
        panel2C.add(chooseSiteButton);
        panel2C.add(tutorialButton);
        panel2.add(panel2C, c);
    }

    private void formatBoard(JPanel panel2B) {
        panel2B.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JButton[][] squares = new JButton[8][8];
        boolean cellColor = true;
        for (byte i = 0; i < 8; i++) {
            //row name
            JLabel label = new JLabel(String.valueOf(i+1), SwingConstants.CENTER);
            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0;
            c.gridy = i;
            c.insets = new Insets(0,2,0,2);
            c.weightx = 0.5;
            c.weighty = 0.5;
            panel2B.add(label, c);
            c.insets = new Insets(0,0,0,0);
            //row of buttons
            for (byte j = 0; j < 8; j++) {
                squares[i][j] = createBoardSquare(cellColor);
                squares[i][j].setName((char)(j+65) + "," + (8-i));
                c.fill = GridBagConstraints.BOTH;
                c.gridx = j+1;
                c.gridy = i;
                c.weightx = 0.5;
                c.weighty = 0.5;
                squares[i][j].addActionListener(new Listener());
                panel2B.add(squares[i][j], c);
                cellColor = !cellColor;
            }
            cellColor = !cellColor;
        }
        //column names
        for (byte i = 0; i < 8; i++) {
            JLabel label = new JLabel(String.valueOf((char)(i+65)), SwingConstants.CENTER);
            c.fill = GridBagConstraints.BOTH;
            c.gridx = i+1;
            c.gridy = 8;
            c.insets = new Insets(2,0,2,0);
            c.weightx = 0.5;
            c.weighty = 0.5;
            panel2B.add(label, c);
        }
        squares[0][7].setText(whiteRook);
        squares[0][0].setText(whiteRook);
        squares[0][1].setText(whiteKnight);
        squares[0][6].setText(whiteKnight);
        squares[0][2].setText(whiteBishop);
        squares[0][5].setText(whiteBishop);
        squares[0][3].setText(whiteQueen);
        squares[0][4].setText(whiteKing);
        for (int i = 0; i < 8; i++) {
            squares[1][i].setText(whitePawn);
            squares[6][i].setText(blackPawn);
        }
        squares[7][0].setText(blackRook);
        squares[7][7].setText(blackRook);
        squares[7][1].setText(blackKnight);
        squares[7][6].setText(blackKnight);
        squares[7][2].setText(blackBishop);
        squares[7][5].setText(blackBishop);
        squares[7][3].setText(blackQueen);
        squares[7][4].setText(blackKing);
    }

    private JButton createBoardSquare(boolean cellColor) {
        JButton button = new JButton(" ");
        button.setForeground(Color.BLACK);
        button.setOpaque(true);
        if (cellColor)
        {
            button.setBackground(Color.WHITE);
        }
        else
        {
            button.setBackground(Color.GRAY);
        }
        button.setFont(new Font("Arial", Font.PLAIN, 25));
        Border line = new LineBorder(Color.BLACK, 0);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        button.setBorder(compound);
        Dimension buttonSize = new Dimension(60,60);
        button.setMinimumSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.setPreferredSize(buttonSize);
        return button;
    }

    private class Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            /*String name = button.getName();
            if (button.isSelected()) {
                button.setSelected(false);
                button.setText(" ");
            } else {
                button.setSelected(true);
                button.setFont(new Font("Arial", Font.PLAIN, 16));
                button.setText(name);
            }*/
            if (selected == null && !button.getText().equals(" ")) {
                selected = button;
                selected.setForeground(Color.YELLOW);
            } else { //if (selected != button) {
                String text = selected.getText();
                selected.setForeground(Color.BLACK);
                selected.setText(" ");
                button.setText(text);
                selected = null;
            }
        }
    }

    private class PanelButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();

            if (button.getText().equals("Load")){
                System.out.println("LoadYo Swag");
            } else if (button.getText().equals("Save")) {

            } else if (button.getText().equals("Choose Side")) {
                
            } else if (button.getText().equals("Tutorial")) {
                
            }
        }
    }
}
