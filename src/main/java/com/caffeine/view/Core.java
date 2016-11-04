package com.caffeine.view;

import com.caffeine.logic.Piece;

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 * 	This is the core View processor that initializes the GUI components
 * 	of the chess program.
 */
public class Core {

    // Unicode chess pieces
    public static final String king = "\u265A";
    public static final String queen = "\u265B";
    public static final String rook = "\u265C";
    public static final String bishop = "\u265D";
    public static final String knight = "\u265E";
    public static final String pawn = "\u265F";

    // Track colors of white pieces and black pieces
    public static Color whiteColor = Color.WHITE;
    public static Color blackColor = Color.BLACK;

    /** GUI Layout Values **/
    public static final Dimension sidePanelDimension = new Dimension(150,550);
    // Insets are padding between components
    public static final Insets sidePadding = new Insets(0,2,0,2);
    public static final Insets noPadding = new Insets(0,0,0,0);
    public static final Insets topBottomPadding = new Insets(2,0,2,0);
    // For layout to perform correctly, components need weight > 0
    public static final double AVERAGE_WEIGHT = 0.5;
    /**
     *	grid x/y and grid width/height are component specific for their
     * 	placements within the outer component they are in.
	 *  (0,0) is the upper left corner
     */

    public static JFrame window = new JFrame("Laboon Chess");
    public static JLabel statusLabel = new JLabel("Status Bar");
    public static BoardSquare[][] squares = new BoardSquare[8][8];
    public static BoardPanel boardPanel = new BoardPanel();

    public Core() {
        window.setName("frame");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMenu(window);
        addMainPanels(window);
        window.pack();
        window.setVisible(true);
        window.setResizable(false);
    }

    /**
     * 	Initializes a menu for a given JFrame.
     *
     *  @param window the JFrame to create a menu for
     */
    private void addMenu(JFrame window) {

        JMenuBar menuBar = new JMenuBar();
        menuBar.setName("menuBar");
        window.setJMenuBar(menuBar);
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);
        JMenuItem setGameTimer = new JMenuItem("Set game timer");
        setGameTimer.setName("menuSetGameTimer");
        JMenuItem setMoveTimer = new JMenuItem("Set move timer");
        setMoveTimer.setName("menuSetMoveTimer");
        JMenuItem undo = new JMenuItem("Undo last move");
        undo.setName("menuUndo");
        JMenuItem showPossMoves = new JMenuItem("Show possible moves");
        showPossMoves.setName("menuShowPossMoves");
        menu.add(setGameTimer);
        menu.add(setMoveTimer);
        menu.add(undo);
        menu.add(showPossMoves);
        setGameTimer.addActionListener(new MenuListener());
        setMoveTimer.addActionListener(new MenuListener());
        undo.addActionListener(new MenuListener());
        showPossMoves.addActionListener(new MenuListener());
    }

    /**
     * 	Initializes panels for a given JFrame to display move history,
     * 	the game board, chess pieces taken, and a status indicator.
     *
     *  @param window the JFrame to create panels for
     */
    private void addMainPanels(JFrame window) {
        Container pane = window.getContentPane();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel historyPanel = new JPanel();
        historyPanel.setName("historyPanel");
        historyPanel.setBackground(Color.WHITE);
        historyPanel.setMinimumSize(sidePanelDimension);
        historyPanel.setMaximumSize(sidePanelDimension);
        historyPanel.setPreferredSize(sidePanelDimension);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 12;
        c.insets = sidePadding;
        c.weightx = AVERAGE_WEIGHT;
        c.weighty = AVERAGE_WEIGHT;
        JLabel historyLabel = new JLabel("<html>[Upcoming Feature]<br>Move History</html>", SwingConstants.CENTER);
        historyLabel.setName("historyLabel");
        historyPanel.add(historyLabel);
        // eventually, formatHistoryPanel(historyPanel);
        pane.add(historyPanel, c);

        JPanel centerPanel = new JPanel();
        centerPanel.setName("centerPanel");
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
        takenPanel.setName("takenPanel");
        takenPanel.setBackground(Color.WHITE);
        takenPanel.setMinimumSize(sidePanelDimension);
        takenPanel.setMaximumSize(sidePanelDimension);
        takenPanel.setPreferredSize(sidePanelDimension);
        c.gridx = 7;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 12;
        c.insets = sidePadding;
        c.weightx = AVERAGE_WEIGHT;
        c.weighty = AVERAGE_WEIGHT;
        JLabel takenLabel = new JLabel("<html>[Upcoming Feature]<br>Taken Pieces</html>", SwingConstants.CENTER);
        takenLabel.setName("takenLabel");
        takenPanel.add(takenLabel);
        // eventually, formatTakenPanel(takenPanel);
        pane.add(takenPanel, c);

        JPanel statusPanel = new JPanel();
        statusPanel.setName("statusPanel");
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
        statusLabel.setName("statusLabel");
        statusPanel.add(statusLabel, SwingConstants.CENTER);
        pane.add(statusPanel, c);
    }

    /**
     * 	Initializes sub-panels to display the current game time,
     * 	the chess board, and commonly used gameplay options.
     *
     *  @param centerPanel the JPanel upon which to create sub-panels
     */
    private void formatCenterPanel(JPanel centerPanel) {

        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel timerPanel = new JPanel();
        timerPanel.setName("timerPanel");
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
        JLabel timerLabel = new JLabel("[Upcoming Feature] - Timer", SwingConstants.CENTER);
        timerLabel.setName("timerLabel");
        timerPanel.add(timerLabel);
        // eventually, formatTimerPanel(timerPanel);
        centerPanel.add(timerPanel, c);

        boardPanel.setName("boardPanel");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 13;
        c.gridheight = 12;
        c.insets = noPadding;
        c.weightx = AVERAGE_WEIGHT;
        c.weighty = AVERAGE_WEIGHT;
        //formatBoard(boardPanel);
        centerPanel.add(boardPanel, c);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setName("buttonPanel");
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

    /**
     * 	Initializes an 8x8 Array of buttons to serve as the squares
     * 	for the chess board, along with grid notation.
     *
     *  @param boardPanel the JPanel upon which to place the game squares on
     */
    /*private void formatBoard(JPanel boardPanel) {

        boardPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        BoardListener boardListener = new BoardListener();
        boolean isWhiteSquare = true;

        for (byte i = 0; i < 8; i++) {

            // grid notation row name
            JLabel label = new JLabel(String.valueOf(8-i), SwingConstants.CENTER);
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

                squares[i][j] = new BoardSquare();
                squares[i][j].setBackgroundColor(isWhiteSquare);
                squares[i][j].setName("BoardSquare:" + (char)(j+65) + "," + (8-i));

                c.fill = GridBagConstraints.BOTH;
                c.gridx = j+1;
                c.gridy = i;
                c.weightx = AVERAGE_WEIGHT;
                c.weighty = AVERAGE_WEIGHT;
                squares[i][j].addActionListener(boardListener);
                boardPanel.add(squares[i][j], c);
                isWhiteSquare = !isWhiteSquare;
            }

            isWhiteSquare = !isWhiteSquare; // creates the checkered pattern of squares
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
        squares[0][7].setPiece(new Piece(rook, 0, "black"));
        squares[0][0].setPiece(new Piece(rook, 1, "black"));
        squares[0][1].setPiece(new Piece(knight, 0, "black"));
        squares[0][6].setPiece(new Piece(knight, 1, "black"));
        squares[0][2].setPiece(new Piece(bishop, 0, "black"));
        squares[0][5].setPiece(new Piece(bishop, 1, "black"));
        squares[0][3].setPiece(new Piece(queen, 0, "black"));
        squares[0][4].setPiece(new Piece(king, 0, "black"));

        for (int i = 0; i < 8; i++) {
            squares[1][i].setPiece(new Piece(pawn, i, "black"));
            squares[6][i].setPiece(new Piece(pawn, i, "white"));
        }

        squares[7][0].setPiece(new Piece(rook, 0, "white"));
        squares[7][7].setPiece(new Piece(rook, 1, "white"));
        squares[7][1].setPiece(new Piece(knight, 0, "white"));
        squares[7][6].setPiece(new Piece(knight, 1, "white"));
        squares[7][2].setPiece(new Piece(bishop, 0, "white"));
        squares[7][5].setPiece(new Piece(bishop, 1, "white"));
        squares[7][3].setPiece(new Piece(queen, 0, "white"));
        squares[7][4].setPiece(new Piece(king, 0, "white"));

    }*/

    /**
     * 	Initializes buttons for the user to readily access
     * 	common gameplay options.
     *
     *
     *  @param buttonPanel  the JPanel upon which to place option buttons
     */
    private void formatButtonPanel(JPanel buttonPanel) {
        PanelButtonListener buttonListener = new PanelButtonListener();

        JButton loadButton = new JButton("Load");
        loadButton.setName("loadButton");
        loadButton.addActionListener(buttonListener);
        buttonPanel.add(loadButton);

        JButton saveButton = new JButton("Save");
        saveButton.setName("saveButton");
        saveButton.addActionListener(buttonListener);
        buttonPanel.add(saveButton);

        JButton chooseSideButton = new JButton("Choose Side");
        chooseSideButton.setName("chooseSideButton");
        chooseSideButton.addActionListener(buttonListener);
        buttonPanel.add(chooseSideButton);

        JButton tutorialButton = new JButton("Tutorial");
        tutorialButton.setName("tutorialButton");
        tutorialButton.addActionListener(buttonListener);
        buttonPanel.add(tutorialButton);

        JButton pieceColorButton = new JButton("Change Piece Color");
        pieceColorButton.setName("pieceColorButton");
        pieceColorButton.addActionListener(buttonListener);
        buttonPanel.add(pieceColorButton);

        JButton squareColorButton = new JButton("Change Square Color");
        squareColorButton.setName("squareColorButton");
        squareColorButton.addActionListener(buttonListener);
        buttonPanel.add(squareColorButton);

        JButton flipButton = new JButton("Flip the Board");
        flipButton.setName("flipButton");
        flipButton.addActionListener(buttonListener);
        buttonPanel.add(flipButton);
    }
}