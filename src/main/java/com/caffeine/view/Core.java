package com.caffeine.view;

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;

public class Core {
    private JButton selected = null;
    private JLabel statusBar = new JLabel("Status Bar");
    private JFrame window;

    // Unicode Pieces
    public static final String whiteKing = "\u2654";
    public static final String whiteQueen = "\u2655";
    public static final String whiteRook = "\u2656";
    public static final String whiteBishop = "\u2657";
    public static final String whiteKnight = "\u2658";
    public static final String whitePawn = "\u2659";
    public static final String blackKing = "\u265A";
    public static final String blackQueen = "\u265B";
    public static final String blackRook = "\u265C";
    public static final String blackBishop = "\u265D";
    public static final String blackKnight = "\u265E";
    public static final String blackPawn = "\u265F";

    // proprietary font that windows/mac have, but Linux will default to 
    // a font that will still display the chess pieces
    Font defaultFont = new Font("Arial Unicode MS", Font.PLAIN, 25);

    // GridBagLayout Values
    // Insets are padding between components
    Insets sidePadding = new Insets(0,2,0,2);
    Insets noPadding = new Insets(0,0,0,0);
    Insets topBottomPadding = new Insets(2,0,2,0);
    final double AVERAGE_WEIGHT = 0.5;

    public Core() {
        window = new JFrame("Laboon Chess");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMenu(window);
        addMainPanels(window);
        window.pack();
        window.setVisible(true);
    }

    private void addMenu(JFrame window) {
        JMenuBar menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);
        JMenuItem setGameTimer = new JMenuItem("Set game timer");
        JMenuItem setMoveTimer = new JMenuItem("Set move timer");
        JMenuItem undo = new JMenuItem("Undo last move");
        JMenuItem showPossMoves = new JMenuItem("Show possible moves");
        menu.add(setGameTimer);
        menu.add(setMoveTimer);
        menu.add(undo);
        menu.add(showPossMoves);
        setGameTimer.addActionListener(new MenuListener());
        setMoveTimer.addActionListener(new MenuListener());
        undo.addActionListener(new MenuListener());
        showPossMoves.addActionListener(new MenuListener());
    }

    private void addMainPanels(JFrame window) {
        Container pane = window.getContentPane();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel historyPanel = new JPanel();
        historyPanel.setBackground(Color.WHITE);
        Dimension historyPanelSize = new Dimension(150,500);
        historyPanel.setMinimumSize(historyPanelSize);
        historyPanel.setMaximumSize(historyPanelSize);
        historyPanel.setPreferredSize(historyPanelSize);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 12;
        c.insets = sidePadding;
        c.weightx = AVERAGE_WEIGHT;
        c.weighty = AVERAGE_WEIGHT;
        historyPanel.add(new JLabel("<html>[Upcoming Feature]<br>Move History</html>", SwingConstants.CENTER));
        // eventually, formatHistoryPanel(historyPanel);
        pane.add(historyPanel, c);

        JPanel centerPanel = new JPanel();
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 6;
        c.gridheight = 12;
        c.insets = noPadding;
        c.weightx = AVERAGE_WEIGHT;
        c.weighty = AVERAGE_WEIGHT;
        formatCenterPanel(centerPanel);
        pane.add(centerPanel, c);

        JPanel takenPanel = new JPanel();
        takenPanel.setBackground(Color.WHITE);
        Dimension takenPanelSize = new Dimension(150,500);
        takenPanel.setMinimumSize(takenPanelSize);
        takenPanel.setMaximumSize(takenPanelSize);
        takenPanel.setPreferredSize(takenPanelSize);
        c.gridx = 7;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 12;
        c.insets = sidePadding;
        c.weightx = AVERAGE_WEIGHT;
        c.weighty = AVERAGE_WEIGHT;
        takenPanel.add(new JLabel("<html>[Upcoming Feature]<br>Taken Pieces</html>", SwingConstants.CENTER));
        // eventually, formatTakenPanel(takenPanel);
        pane.add(takenPanel, c);

        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(Color.WHITE);
        Dimension statusPanelSize = new Dimension(800,30);
        statusPanel.setMinimumSize(statusPanelSize);
        statusPanel.setMaximumSize(statusPanelSize);
        statusPanel.setPreferredSize(statusPanelSize);
        c.gridx = 0;
        c.gridy = 12;
        c.gridwidth = 8;
        c.gridheight = 1;
        c.insets = topBottomPadding;
        c.weightx = AVERAGE_WEIGHT;
        c.weighty = AVERAGE_WEIGHT;
        statusPanel.add(statusBar, SwingConstants.CENTER);
        pane.add(statusPanel, c);
    }

    private void formatCenterPanel(JPanel centerPanel) {
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel timerPanel = new JPanel();
        timerPanel.setBackground(Color.WHITE);
        Dimension timerPanelSize = new Dimension(200,40);
        timerPanel.setMinimumSize(timerPanelSize);
        timerPanel.setMaximumSize(timerPanelSize);
        timerPanel.setPreferredSize(timerPanelSize);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 13;
        c.gridheight = 3;
        c.insets = topBottomPadding;
        c.weightx = AVERAGE_WEIGHT;
        c.weighty = AVERAGE_WEIGHT;
        timerPanel.add(new JLabel("[Upcoming Feature] - Timer", SwingConstants.CENTER));
        // eventually, formatTimerPanel(timerPanel);
        centerPanel.add(timerPanel, c);

        JPanel boardPanel = new JPanel();
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 13;
        c.gridheight = 12;
        c.insets = noPadding;
        c.weightx = AVERAGE_WEIGHT;
        c.weighty = AVERAGE_WEIGHT;
        formatBoard(boardPanel);
        centerPanel.add(boardPanel, c);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        Dimension buttonPanelSize = new Dimension(500,70);
        buttonPanel.setMinimumSize(buttonPanelSize);
        buttonPanel.setMaximumSize(buttonPanelSize);
        buttonPanel.setPreferredSize(buttonPanelSize);
        c.gridx = 0;
        c.gridy = 15;
        c.gridwidth = 13;
        c.gridheight = 4;
        c.insets = topBottomPadding;
        c.weightx = AVERAGE_WEIGHT;
        c.weighty = AVERAGE_WEIGHT;
        formatButtonPanel(buttonPanel);
        centerPanel.add(buttonPanel, c);
    }

    private void formatBoard(JPanel boardPanel) {
        boardPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JButton[][] squares = new JButton[8][8];
        boolean cellColor = true;
        for (byte i = 0; i < 8; i++) {
            // grid notation row name
            JLabel label = new JLabel(String.valueOf(i+1), SwingConstants.CENTER);
            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0;
            c.gridy = i;
            c.insets = sidePadding;
            c.weightx = AVERAGE_WEIGHT;
            c.weighty = AVERAGE_WEIGHT;
            boardPanel.add(label, c);
            c.insets = noPadding;
            //row of buttons
            for (byte j = 0; j < 8; j++) {
                squares[i][j] = createBoardSquare(cellColor);
                squares[i][j].setName((char)(j+65) + "," + (8-i));
                c.fill = GridBagConstraints.BOTH;
                c.gridx = j+1;
                c.gridy = i;
                c.weightx = AVERAGE_WEIGHT;
                c.weighty = AVERAGE_WEIGHT;
                squares[i][j].addActionListener(new BoardListener());
                boardPanel.add(squares[i][j], c);
                cellColor = !cellColor;
            }
            cellColor = !cellColor;
        }
        // grid notation column names
        for (byte i = 0; i < 8; i++) {
            JLabel notationColumn = new JLabel(String.valueOf((char)(i+65)), SwingConstants.CENTER);
            c.fill = GridBagConstraints.BOTH;
            c.gridx = i+1;
            c.gridy = 8;
            c.insets = topBottomPadding;
            c.weightx = AVERAGE_WEIGHT;
            c.weighty = AVERAGE_WEIGHT;
            boardPanel.add(notationColumn, c);
        }

        // initialize piece placement
        squares[0][7].setText(blackRook);
        squares[0][0].setText(blackRook);
        squares[0][1].setText(blackKnight);
        squares[0][6].setText(blackKnight);
        squares[0][2].setText(blackBishop);
        squares[0][5].setText(blackBishop);
        squares[0][3].setText(blackQueen);
        squares[0][4].setText(blackKing);
        for (int i = 0; i < 8; i++) {
            squares[1][i].setText(blackPawn);
            squares[6][i].setText(whitePawn);
        }
        squares[7][0].setText(whiteRook);
        squares[7][7].setText(whiteRook);
        squares[7][1].setText(whiteKnight);
        squares[7][6].setText(whiteKnight);
        squares[7][2].setText(whiteBishop);
        squares[7][5].setText(whiteBishop);
        squares[7][3].setText(whiteQueen);
        squares[7][4].setText(whiteKing);

    }

    private void formatButtonPanel(JPanel buttonPanel) {
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new PanelButtonListener());
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new PanelButtonListener());
        JButton chooseSiteButton = new JButton("Choose Side");
        chooseSiteButton.addActionListener(new PanelButtonListener());
        JButton tutorialButton = new JButton("Tutorial");
        tutorialButton.addActionListener(new PanelButtonListener());
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(chooseSiteButton);
        buttonPanel.add(tutorialButton);
    }

    private JButton createBoardSquare(boolean cellColor) {
        JButton squareButton = new JButton(" ");
        squareButton.setForeground(Color.BLACK);
        squareButton.setOpaque(true);
        if (cellColor)
        {
            squareButton.setBackground(Color.WHITE);
        }
        else
        {
            squareButton.setBackground(Color.GRAY);
        }
        squareButton.setFont(defaultFont);
        Border line = new LineBorder(Color.BLACK, 0);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        squareButton.setBorder(compound);
        Dimension buttonSize = new Dimension(60,60);
        squareButton.setMinimumSize(buttonSize);
        squareButton.setMaximumSize(buttonSize);
        squareButton.setPreferredSize(buttonSize);
        return squareButton;
    }

    private class BoardListener implements ActionListener {
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

    private class PanelButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();

            if (button.getText().equals("Load")){
                String fileName = JOptionPane.showInputDialog(window, "Please enter file name to load a game: ", "Load Game", JOptionPane.PLAIN_MESSAGE);
                if (fileName != null && fileName.length() != 0) {
                    if(fileName.toLowerCase().endsWith(".pgn")){
                        statusBar.setText("[Upcoming Feature] - Loading game from file: " + fileName);
                    }
                    else{
                        statusBar.setText("[Upcoming Feature] - Loading game from file: " + fileName + ".pgn");
                    }
                }
            } else if (button.getText().equals("Save")) {
                String fileName = JOptionPane.showInputDialog(window, "Please enter a file name to save your game: ", "Save Game", JOptionPane.PLAIN_MESSAGE);
                if (fileName != null && fileName.length() != 0) {
                    statusBar.setText("[Upcoming Feature] - Saving game to file: " + fileName + ".pgn");
                }
            } else if (button.getText().equals("Choose Side")) {
                String[] options = new String[] {"Black", "White", "Cancel"};
                int playerColor = JOptionPane.showOptionDialog(window, "Please choose a side", "Choose Side",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);
                switch(playerColor){
                    case -1: break;
                    case 0: statusBar.setText("[Upcoming Feature] - Now playing as Black");
                    break;
                    case 1: statusBar.setText("[Upcoming Feature] - Now playing as White");
                    break;
                    case 2: break;
                }
            } else if (button.getText().equals("Tutorial")) {
                JOptionPane.showMessageDialog(window, "This is a simple walking skeleton, but does have some basic functionality.\n" +
                    "Simply click on a piece and then another tile to move the piece to that tile.", "Tutorial", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    private class MenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JMenuItem menuItem = (JMenuItem) e.getSource();
            if (menuItem.getText().equals("Set game timer")) {
                statusBar.setText("[Upcoming Feature] - Set game timer");
            } else if (menuItem.getText().equals("Set move timer")) {
                statusBar.setText("[Upcoming Feature] - Set move timer");
            } else if (menuItem.getText().equals("Undo last move")) {
                statusBar.setText("[Upcoming Feature] - Undo last move");
            } else if (menuItem.getText().equals("Show possible moves")) {
                statusBar.setText("[Upcoming Feature] - Show possible moves");
            }
        }
    }

}
