package com.caffeine;

/**
 * This is the main class of the entire program.
 * It will instantiate and configure the View, Engine, and Logic layers.
 */
public class Chess {

    public static void main(String[] args) {
        com.caffeine.engine.Core engine = new com.caffeine.engine.Core();
        com.caffeine.logic.Game game = new com.caffeine.logic.Game();
        com.caffeine.view.Core view = new com.caffeine.view.Core(game);
    }
}
