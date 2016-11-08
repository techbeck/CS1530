package com.caffeine;

import com.caffeine.view.Core;

/**
 * This is the main class of the entire program.
 * It will instantiate and configure the View, Engine, and Logic layers.
 */
public class Chess {

    public static void main(String[] args) {
        JockfishEngine engine = new JockfishEngine();
        com.caffeine.logic.Game game = new com.caffeine.logic.Game();
        Core mainWindow = new Core(game);
    }
}
