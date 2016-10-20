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

// ...
// ...
// ...

    public Core() {
        window = new JFrame("Laboon Chess");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMenu(window);
        addMainPanels(window);
        window.pack();
        window.setVisible(true);
    }

}
