package com.caffeine;

/**
 * This is the main class of the entire program.
 * It will instantiate and configure the View, Engine, and Logic layers.
 */
public class Chess {

	public static com.caffeine.engine.Core engine;
	public static com.caffeine.logic.Game game;
	public static com.caffeine.view.Core mainWindow;

    public static void main(String[] args) {
        engine = new com.caffeine.engine.Core();
        game = new com.caffeine.logic.Game();
        mainWindow = new com.caffeine.view.Core();
    }
}
