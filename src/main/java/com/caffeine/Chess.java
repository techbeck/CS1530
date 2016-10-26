package com.caffeine;

import com.jwarner.jockfish.JockfishEngine;

/**
 * This is the main class of the entire program.
 * It will instantiate and configure the View, Engine, and Logic layers.
 */
public class Chess {

    public static void main(String[] args) {
        com.caffeine.view.Core mainWindow = new com.caffeine.view.Core();
        JockfishEngine engine = new JockfishEngine();
    }

}
