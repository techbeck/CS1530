package com.caffeine.view;

import com.caffeine.Chess;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

/**
 * The Kibitzer class attaches a JPanel to the main window and displays
 * random comments about Chess every 1-5 seconds.
 */
public class Kibitzer {

    JPanel optionPane;
    JDialog dialog;

    public Kibitzer(JFrame window) {

        //  populate a String array with all comments in the kibitzer.txt file
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

        //  JDialog needed since more flexable than JOptionPane
        dialog = new JDialog();
        dialog.setUndecorated(true);
        dialog.setTitle("Kitbitzer");
        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        dialog.setModal(false);
        dialog.setContentPane(optionPane);
        dialog.pack();

        //  when main window is moved, the kibitzer should move alongside it
        dialog.setLocation(new Point(window.getX() + window.getWidth(), window.getY() + 100));
        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                dialog.setLocation(new Point(window.getX() + window.getWidth(), window.getY() + 100));
            }
        });

        while(true) {
            if(!Chess.game.gameStarted){
                //  hidden until game is started
                dialog.setVisible(false);
            } else if (Chess.game.gameStarted & dialog.isVisible()){
                //  sleep for 1-5 seconds and then display another random comment
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
                //  only happens once when the game is first started
                dialog.setVisible(true);
            }
        }

    }

    /**
     * Formats a String so as a JLabel, it is centered
     * and has line wrapping.
     * @param  comment The raw String
     * @return         The String with html formatting
     */
    private String formatComment(String comment) {
        return "<html><body><center><p style='width: 150px;'>" + comment +
        "</p></center></body></html>";
    }

}
