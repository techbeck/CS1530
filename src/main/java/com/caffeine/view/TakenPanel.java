package com.caffeine.view;

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

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

	public void setCaptByBlack(String str) {
		captByBlack.setText("<html><p>"+ str +"</p></html>");
	}

	public void setCaptByWhite(String str) {
		captByWhite.setText("<html><p>"+ str +"</p></html>");
	}

	public void setCaptByBlackColor() {
		captByBlack.setForeground(Core.whiteColor);
	}

	public void setCaptByWhiteColor() {
		captByWhite.setForeground(Core.blackColor);
	}
}