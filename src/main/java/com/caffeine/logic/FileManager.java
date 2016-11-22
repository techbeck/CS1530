package com.caffeine.logic;

import com.caffeine.Chess;
import com.caffeine.view.Core;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class FileManager {
	public static void save(String fileName) {
		File file = new File("SavedGames/" + fileName);
		PrintWriter output;
		try {
			output = new PrintWriter(file);
		} catch (IOException io) {
			Core.statusLabel.setText("Unable to save to file.");
			return;
		}
		output.println("[Event \"" + Chess.game.pgnTags.get("Event") + "\"]");
		output.println("[Site \"" + Chess.game.pgnTags.get("Site") + "\"]");
		output.println("[Date \"" + Chess.game.pgnTags.get("Date") + "\"]");
		output.println("[Round \"" + Chess.game.pgnTags.get("Round") + "\"]");
		output.println("[White \"" + Chess.game.pgnTags.get("White") + "\"]");
		output.println("[Black \"" + Chess.game.pgnTags.get("Black") + "\"]");
		output.print("[Result \"");
		switch (Chess.game.gameResult) {
			case 0:
				output.print("*");
				break;
			case 1:
				output.print("1-0");
				break;
			case 2:
				output.print("0-1");
				break;
			case 3:
				output.print("1/2-1/2");
				break;
		}
		output.println("\"]");
		output.println("[FEN \"" + Chess.game.pgnTags.get("FEN") + "\"]");
		output.println();
		int currMoveNum = 1;
		String currHalfMove = null;
		for (String halfMove : Chess.game.moveHistory) {
			if (currHalfMove == null) {
				currHalfMove = halfMove;
			} else {
				String fullMove = currMoveNum + ". " + currHalfMove + " " + halfMove;
				output.print(fullMove + " ");
				currMoveNum++;
				currHalfMove = null;
			}
		}
		if (currHalfMove != null) {
			String extra = currMoveNum + ". " + currHalfMove;
			output.print(extra + " ");
		}
		switch (Chess.game.gameResult) {
			case 0:
				output.println();
				break;
			case 1:
				output.println("1-0");
				break;
			case 2:
				output.println("0-1");
				break;
			case 3:
				output.println();
				break;
		}

		output.close();
	}
	public static void load(String fileName) {
		File file = new File("SavedGames/" + fileName);
		Scanner input;
		try {
			input = new Scanner(file);
		} catch (IOException io) {
			Core.statusLabel.setText("Unable to load from file.");
			return;
		}
		String fileLine = "";
		String userSide = "white";
		while (input.hasNext()) {
			fileLine = input.nextLine();
			if (fileLine.contains("User")) userSide = fileLine.split(" ")[0].substring(1);
			if (fileLine.contains("FEN")) break;
		}
		String fen = fileLine.split("\"")[1];
		Chess.game = new Game();
		Chess.game.startGame();
		Chess.game.setSide(userSide);
		Chess.game.loadFEN(fen);
		input.nextLine();
		String moveHist = input.nextLine();
		String[] moves = moveHist.split(" ");
		for (String move : moves) {
			if (move.charAt(0) > '9') {
				Chess.game.addToMoveHistory(move);
			}
		}
		input.close();
	}
}