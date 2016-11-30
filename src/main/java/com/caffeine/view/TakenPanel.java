package com.caffeine.view;

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 *  This JPanel displays the pieces that were taken
 *  by each player.
 */
public class TakenPanel extends JPanel {
    private JLabel takenlabel = new JLabel("Taken Pieces");
    private JLabel captByBlack = new JLabel();
    private JLabel captByWhite = new JLabel();

    private Dimension labelDimen = new Dimension(150,260);

    /* proprietary font that Windows/Mac have, but Linux will default to
    a font that will still display the chess pieces */
    Font takenFont = new Font("Arial Unicode MS", Font.PLAIN, 25);

    public TakenPanel() {
        setName("takenPanel");
        setBackground(Color.decode(Core.themes[0][1]));
        setMinimumSize(Core.sidePanelDimension);
        setMaximumSize(Core.sidePanelDimension);
        setPreferredSize(Core.sidePanelDimension);
        takenlabel = new JLabel("Taken Pieces", SwingConstants.CENTER);
        takenlabel.setName("takenLabel");
        add(takenlabel);
        captByBlack.setPreferredSize(labelDimen);
        captByBlack.setMaximumSize(labelDimen);
        captByBlack.setMinimumSize(labelDimen);
        captByBlack.setFont(takenFont);
        captByBlack.setHorizontalAlignment(SwingConstants.CENTER);
        captByBlack.setVerticalAlignment(SwingConstants.TOP);
        setCaptByBlackColor();
        add(captByBlack);
        captByWhite.setPreferredSize(labelDimen);
        captByWhite.setMaximumSize(labelDimen);
        captByWhite.setMinimumSize(labelDimen);
        captByWhite.setFont(takenFont);
        captByWhite.setHorizontalAlignment(SwingConstants.CENTER);
        captByWhite.setVerticalAlignment(SwingConstants.TOP);
        captByWhite.setForeground(Core.blackColor);
        add(captByWhite);
    }

    /**
     *  Displays what pieces were taken by black.
     *
     *  @param str The list of white pieces taken thus far
     */
    public void setCaptByBlack(String str) {
        captByBlack.setText("<html><p>"+ str +"</p></html>");
    }

    /**
     *  Displays what pieces were taken by white.
     *
     *  @param str The list of black pieces taken thus far
     */
    public void setCaptByWhite(String str) {
        captByWhite.setText("<html><p>"+ str +"</p></html>");
    }

    /**
     *  Changes the color of the white pieces to the color
     *  requested by the user.
     */
    public void setCaptByBlackColor() {
        captByBlack.setForeground(Core.whiteColor);
    }

    /**
     *  Changes the color of the black pieces to the color
     *  requested by the user.
     */
    public void setCaptByWhiteColor() {
        captByWhite.setForeground(Core.blackColor);
    }
	
	/**
	 * Returns captByWhite for unit testing purposes.
	 */
	public String getCaptByWhite() {
		String capt = captByWhite.getText();
		return capt;
	}
	 
	 /**
	 * Returns captByBlack for unit testing purposes.
	 */
	public String getCaptByBlack() {
		String capt = captByBlack.getText();
		return capt;
	}
}
