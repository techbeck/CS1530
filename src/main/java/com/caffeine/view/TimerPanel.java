package com.caffeine.view;

import com.caffeine.Chess;

import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
* This custom JPanel displays the current game time.
*/
public class TimerPanel extends JPanel {

    /* note that 30 minutes is actually 1800000 milliseconds,
    but since we decrement every 10 milliseconds, or 1
    centisecond, we cut off 1 zero */
    private static final int ONE_CENTISECOND = 10;
    private static final int HALF_HOUR = 180000;
    private int countDown = 0;
    private int intialCountDown = 0;
    private Timer timer;

    private Boolean timeOut = false;
    private Boolean paused = false;

    private JLabel timerLabel = new JLabel("Timer",
    SwingConstants.CENTER);

    public TimerPanel() {

        //  initialize timerPanel's GUI formatting
        setName("timerPanel");
        setBackground(Color.decode(Core.themes[0][1]));
        Dimension timerPanelSize = new Dimension(200,40);
        setMinimumSize(timerPanelSize);
        setMaximumSize(timerPanelSize);
        setPreferredSize(timerPanelSize);
        timerLabel.setName("timerLabel");
        add(timerLabel);

        //  default to 30 minutes to count down
        intialCountDown = HALF_HOUR;
        countDown = HALF_HOUR;
        timer = new Timer(ONE_CENTISECOND, new TimerListener());
        timer.start();
        timerLabel.setText("Timer set to " +
            String.format("%02d", countDown / 6000) + " minutes");

    }

    /**
     * Every 1/100th of a second (centisecond) this ActionListener
     * decrements the timer.
     */
    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if(!Chess.game.gameStarted) {
                // do nothing
            } else if (paused) {
                //  do nothing
            } else if(countDown > 0) {
                countDown--;
                timerLabel.setText("Time remaining: " + formatTime(countDown));
            } else {
                timer.stop();
                timeOut = true;
                timerLabel.setText("Time's Out!");
            }

        }
    }

    /**
     * Formats the timer so it displays time as mm:ss.cs,
     * such as 15:34.67
     * @param  centisecond The total number of centisecond
     * @return             the formatted time as a String
     */
    private String formatTime(int centisecond) {
        String milSeconds = String.format("%02d", centisecond % 100);
        String seconds = String.format("%02d", (centisecond / 100) % 60);
        String minutes = String.format("%02d", centisecond / 6000);
        return minutes + ":" + seconds + "." + milSeconds;
    }

    /**
     * Restarts the timer with its current configuration.
     */
    public void restartTimer() {
        timeOut = false;
        timer.restart();
        countDown = intialCountDown;
    }

    /**
     * A Boolean publically available to check whether the current
     * timer is finished.
     * @return true if timer has reached 0, false otherwise
     */
    public Boolean isTimeOut() {
        return timeOut;
    }

    /**
     * Sets the timer to the given number of minutes.
     * Note since time is in centiseconds, we multiply by
     * 6000, not 60000
     * @param minutes How many minutes the timer should last.
     */
    public void setTimer(int minutes) {
        intialCountDown = minutes * 6000;
        countDown = intialCountDown;
        timerLabel.setText("Timer set to " +
            String.format("%02d", countDown / 6000) + " minutes");
    }

    /**
     * Pauses the timer.
     */
    public void pauseTimer() {
        paused = true;
    }

    /**
     * Resumes the timer.
     */
    public void resumeTimer() {
        paused = false;
    }

    /**
     * Get the current amount of time left,
     * in centiseconds.
     * @return The number of centiseconds left in the game
     */
    public int getTimeLeft() {
        return countDown;
    }

    /**
     * Set the timer to the specified number of centiseconds.
     * @param  centiseconds How long the timer should last
     */
    public void setTimeLeft(String centiseconds) {
        countDown = Integer.parseInt(centiseconds);
    }

}
