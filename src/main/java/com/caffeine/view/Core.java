package com.caffeine.view;

import com.caffeine.Chess;
import com.caffeine.logic.Piece;
import com.caffeine.logic.Game;

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 *  This is the core View processor that initializes the GUI components
 *  of the chess program.
 */
public class Core {

    //  Unicode chess pieces
    public static final String king = "\u265A";
    public static final String queen = "\u265B";
    public static final String rook = "\u265C";
    public static final String bishop = "\u265D";
    public static final String knight = "\u265E";
    public static final String pawn = "\u265F";

    //  Track colors of white pieces and black pieces
    public static Color whiteColor = Color.WHITE;
    public static Color blackColor = Color.BLACK;

    // Constants for color theme hex Values
    public static final String[][] themes = {
        {"0xFFFFFF", "0xE0E0E0", "0xC0C0C0", "0x808080"}, // Grayscale
        {"0xFFAFC2", "0xFFDFE6", "0xFE73A6", "0xFE3C74"}, // Peppermint
        {"0xaeffd4", "0x8CD0A1", "0x7dfa92", "0x34a762"}, // Shamrock
        {"0xB07147", "0xf4d095", "0xfea645", "0x8F4120"}, // Pumpkin Spice
        {"0xC4B5AF", "0xeeeeff", "0xE5F1FF", "0x9A7C60"}  // Iced Vanilla
    //   background, panels,     light,      dark
    };
    public static int currentTheme = 0; // match indices above

    //  GUI Layout Values
    public static final Dimension sidePanelDimension = new Dimension(150,550);
    public static final Dimension centerPanelDimension = new Dimension(500,630);

    //  Insets are padding between components
    public static final Insets sidePadding = new Insets(0,5,0,5);
    public static final Insets noPadding = new Insets(0,0,0,0);
    public static final Insets topBottomPadding = new Insets(5,0,5,0);

    //  For layout to perform correctly, components need weight > 0
    public static final double AVERAGE_WEIGHT = 0.5;

    // Keep Track of legal moves and whether or not to show them.
    public static boolean showLegalMoves = false;
    public static ArrayList<String> possibleMoves = new ArrayList<String>();


    /**
     *  grid x/y and grid width/height are component specific for their
     *  placements within the outer component they are in.
     *  (0,0) is the upper left corner
     */

    public static JFrame window = new JFrame("Laboon Chess");
    public static BoardSquare[][] squares = new BoardSquare[8][8];
    public static TakenPanel takenPanel;
    public static HistoryPanel historyPanel;
    public static TimerPanel timerPanel;
    public static StatusPanel statusPanel;

    protected static JPanel centerPanel;
    protected static JPanel buttonPanel;
    protected static BoardPanel boardPanel;
    protected static Kibitzer KibitzerPane;
    protected static JMenuItem changeMode;
    protected static JMenuItem setMoveTimer;
    protected static JButton saveButton;

    public Core() {
        window.setName("frame");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMenu(window);
        addMainPanels(window);
        window.pack();
        window.setResizable(false);
        window.setVisible(true);

        //  Windows doesn't permit a VM to initially bring a window to focus,
        //  so this forces the chess window to be focused
        window.setAlwaysOnTop(true);
        window.setAlwaysOnTop(false);

        Thread kibitzer = new Thread(() -> {
            KibitzerPane = new Kibitzer(window);
        });
        kibitzer.start();

    }

    /**
     *  Initializes a menu for a given JFrame.
     *
     *  @param window The JFrame to create a menu for
     */
    private void addMenu(JFrame window) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setName("menuBar");
        window.setJMenuBar(menuBar);
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);
        changeMode = new JMenuItem("Change CPU Mode");
        changeMode.setName("menuChangeMode");
        JMenuItem undo = new JMenuItem("Undo last move");
        undo.setName("menuUndo");
        setMoveTimer = new JMenuItem("Set move timer");
        setMoveTimer.setName("menuSetMoveTimer");
        JMenuItem toggleShowLegalMoves = new JMenuItem("Show legal moves");
        toggleShowLegalMoves.setName("menuToggleShowLegalMoves");
        menu.add(changeMode);
        menu.add(undo);
        menu.add(setMoveTimer);
        menu.add(toggleShowLegalMoves);
        changeMode.addActionListener(new MenuListener());
        undo.addActionListener(new MenuListener());
        setMoveTimer.addActionListener(new MenuListener());
        toggleShowLegalMoves.addActionListener(new MenuListener());
    }

    /**
     *  Initializes panels for a given JFrame to display move history,
     *  the game board, chess pieces taken, and a status indicator.
     *
     *  @param window The JFrame to create panels for
     */
    private void addMainPanels(JFrame window) {
        Container pane = window.getContentPane();
        pane.setBackground(Color.decode(themes[0][0]));
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = AVERAGE_WEIGHT;
        c.weighty = AVERAGE_WEIGHT;

        historyPanel = new HistoryPanel();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 12;
        c.insets = sidePadding;
        pane.add(historyPanel, c);

        centerPanel = new JPanel();
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 6;
        c.gridheight = 12;
        c.insets = noPadding;
        formatCenterPanel();
        pane.add(centerPanel, c);

        takenPanel = new TakenPanel();
        c.gridx = 7;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 12;
        c.insets = sidePadding;
        pane.add(takenPanel, c);

        statusPanel = new StatusPanel();
        c.gridx = 0;
        c.gridy = 12;
        c.gridwidth = 8;
        c.gridheight = 1;
        c.insets = topBottomPadding;
        pane.add(statusPanel, c);
    }

    /**
     *  Initializes sub-panels to display the current game time,
     *  the chess board, and commonly used gameplay options.
     */
    private void formatCenterPanel() {

        centerPanel.setName("centerPanel");
        centerPanel.setBackground(Color.decode(themes[0][0]));
        centerPanel.setMinimumSize(centerPanelDimension);
        centerPanel.setMaximumSize(centerPanelDimension);
        centerPanel.setPreferredSize(centerPanelDimension);

        centerPanel.setLayout(new FlowLayout());

        timerPanel = new TimerPanel();
        centerPanel.add(timerPanel);

        boardPanel = new BoardPanel();
        centerPanel.add(boardPanel);

        buttonPanel = new JPanel();
        formatButtonPanel();
        centerPanel.add(buttonPanel);
    }

    /**
     *  Initializes buttons for the user to readily access
     *  common gameplay options.
     */
    private void formatButtonPanel() {

        buttonPanel.setName("buttonPanel");
        buttonPanel.setBackground(Color.decode(themes[0][1]));
        Dimension buttonPanelSize = new Dimension(490,70);
        buttonPanel.setMinimumSize(buttonPanelSize);
        buttonPanel.setMaximumSize(buttonPanelSize);
        buttonPanel.setPreferredSize(buttonPanelSize);

        PanelButtonListener buttonListener = new PanelButtonListener();

        JButton loadButton = new JButton("Load");
        loadButton.setName("loadButton");
        loadButton.addActionListener(buttonListener);
        buttonPanel.add(loadButton);

        saveButton = new JButton("Save");
        saveButton.setName("saveButton");
        saveButton.addActionListener(buttonListener);
        buttonPanel.add(saveButton);

        JButton chooseSideButton = new JButton("New Game");
        chooseSideButton.setName("newGameButton");
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

        JButton colorThemeButton = new JButton("Change Color Theme");
        colorThemeButton.setName("colorThemeButton");
        colorThemeButton.addActionListener(buttonListener);
        buttonPanel.add(colorThemeButton);

        JButton flipButton = new JButton("Flip the Board");
        flipButton.setName("flipButton");
        flipButton.addActionListener(buttonListener);
        buttonPanel.add(flipButton);
    }
}
