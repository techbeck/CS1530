package com.caffeine.view;

import com.caffeine.Chess;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class Kibitzer extends JOptionPane {

    JPanel optionPane;
    JDialog dialog;

    public Kibitzer(JFrame window) {

        String[] arrayOfComments;
        try {
            Scanner scanner = new Scanner(new File("res/kibitzer.txt"));
            java.util.List<String> comments = new ArrayList<String>();
            while (scanner.hasNextLine()) {
                comments.add(scanner.nextLine());
            }
            arrayOfComments = comments.toArray(new String[0]);
        } catch (FileNotFoundException e) {
            arrayOfComments = null;
        }

        optionPane = new JPanel(new GridBagLayout());
        optionPane.setBorder(new EmptyBorder(8, 8, 8, 8));
        JLabel label = new JLabel(formatComment("Why hello there!"));
        optionPane.add(label);

        dialog = new JDialog();
        dialog.setUndecorated(true);
        dialog.setTitle("Kitbitzer");
        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        dialog.setModal(false);
        dialog.setContentPane(optionPane);
        dialog.pack();

        dialog.setLocation(new Point(window.getX() + window.getWidth(), window.getY() + 100));
        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                dialog.setLocation(new Point(window.getX() + window.getWidth(), window.getY() + 100));
            }
        });

        while(true) {
            if(!Chess.game.gameStarted){
                dialog.setVisible(false);
            } else if (Chess.game.gameStarted & dialog.isVisible()){
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1, 6) * 1000);
                } catch (InterruptedException e) {
                    assert false;
                }
                String nextComment = arrayOfComments[ThreadLocalRandom.current()
                .nextInt(0, arrayOfComments.length - 1)];
                label.setText(formatComment(nextComment));
                dialog.pack();
            } else {
                dialog.setVisible(true);
            }
        }

    }

    private String formatComment(String comment) {
        return "<html><body><center><p style='width: 150px;'>" + comment +
        "</p></center></body></html>";
    }

}
