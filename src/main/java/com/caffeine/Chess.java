package com.caffeine;

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;

public class Chess {
	private static JButton[][] squares;

    public static void main(String[] args){
        createGUI();
    }

    // remove this later. I just don't want to fix tests.
    public static String echo(String phrase){
        return(phrase);
    }

    public static void createGUI() {
    	JFrame window = new JFrame("Laboon Chess");
    	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	addMainPanels(window);
    	window.pack();
    	window.setVisible(true);
    }

    public static void addMainPanels(JFrame window) {
    	Container pane = window.getContentPane();
    	pane.setLayout(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();
    	
    	JPanel panel1 = new JPanel();
    	panel1.setBackground(Color.BLUE);
    	c.gridx = 0;
    	c.gridy = 0;
    	c.gridwidth = 1;
    	c.gridheight = 12;
    	c.weightx = 0.5;
    	c.weighty = 0.5;
    	panel1.add(new JLabel("Move History", SwingConstants.CENTER));
    	panel1.setPreferredSize(new Dimension(120, 800));
    	pane.add(panel1, c);

    	JPanel panel2 = new JPanel();
    	panel2.setBackground(Color.WHITE);
    	c.gridx = 1;
    	c.gridy = 0;
    	c.gridwidth = 6;
    	c.gridheight = 12;
    	c.weightx = 0.7;
    	c.weighty = 0.5;
    	panel2.setPreferredSize(new Dimension(800, 800));
    	formatCenterPanel(panel2);
    	pane.add(panel2, c);

    	JPanel panel3 = new JPanel();
    	panel3.setBackground(Color.RED);
    	c.gridx = 7;
    	c.gridy = 0;
    	c.gridwidth = 1;
    	c.gridheight = 12;
    	c.weightx = 0.5;
    	c.weighty = 0.5;
    	panel3.add(new JLabel("Taken Pieces", SwingConstants.CENTER));
    	panel3.setPreferredSize(new Dimension(120, 800));
    	pane.add(panel3, c);

    	JPanel panel4 = new JPanel();
    	c.gridx = 0;
    	c.gridy = 12;
    	c.gridwidth = 8;
    	c.gridheight = 1;
    	c.weightx = 0.4;
    	c.weighty = 0.4;
    	panel4.add(new JLabel("Status bar", SwingConstants.CENTER));
    	panel4.setPreferredSize(new Dimension(1040, 50));
    	pane.add(panel4, c);
    }

    public static void formatCenterPanel(JPanel panel2) {
    	panel2.setLayout(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();

    	JPanel panel2A = new JPanel();
    	panel2A.setBackground(Color.BLUE);
    	c.gridx = 0;
    	c.gridy = 0;
    	c.gridwidth = 13;
    	c.gridheight = 3;
    	c.weightx = 0.5;
    	c.weighty = 0.5;
    	panel2A.add(new JLabel("timer goes here", SwingConstants.CENTER));
    	panel2A.setPreferredSize(new Dimension(800, 100));
    	panel2.add(panel2A, c);

    	JPanel panel2B = new JPanel();
    	panel2B.setBackground(Color.RED);
    	c.gridx = 0;
    	c.gridy = 3;
    	c.gridwidth = 13;
    	c.gridheight = 12;
    	c.weightx = 0.5;
    	c.weighty = 0.5;
    	//panel2B.add(new JLabel("board goes here", SwingConstants.CENTER));
    	panel2B.setPreferredSize(new Dimension(800, 100));
    	formatBoard(panel2B);
    	panel2.add(panel2B, c);

    	JPanel panel2C = new JPanel();
    	c.gridx = 0;
    	c.gridy = 15;
    	c.gridwidth = 13;
    	c.gridheight = 4;
    	c.weightx = 0.5;
    	c.weighty = 0.5;
    	panel2C.add(new JLabel("buttons go here", SwingConstants.CENTER));
    	panel2C.setPreferredSize(new Dimension(800, 100));
    	panel2.add(panel2C, c);
    }

    public static void formatBoard(JPanel panel2B) {
    	panel2B.setLayout(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();

    	squares = new JButton[8][8];
    	for (byte i = 0; i < 8; i++) {
    		for (byte j = 0; j < 8; j++) {
    			squares[i][j] = createSimpleButton((char)(j+65) + "," + i+1);
    			c.fill = GridBagConstraints.BOTH;
    			c.gridx = j;
    			c.gridy = i;
    			c.weightx = 0.5;
    			c.weighty = 0.5;
    			panel2B.add(squares[i][j], c);
    		}
    	}
    }

    private static JButton createSimpleButton(String text) {
		JButton button = new JButton(text);
		button.setForeground(Color.BLACK);
		button.setBackground(Color.WHITE);
		Border line = new LineBorder(Color.BLACK);
		Border margin = new EmptyBorder(5, 15, 5, 15);
		Border compound = new CompoundBorder(line, margin);
		button.setBorder(compound);
		return button;
    }
}
