package com.github.Fa2bio.Minesweeper;

import com.github.Fa2bio.Minesweeper.model.Board;
import com.github.Fa2bio.Minesweeper.vision.Console;

public class Application {
	public static void main(String[] args) {
		Board board = new Board(6,6,3);
		new Console(board);
	}
}
