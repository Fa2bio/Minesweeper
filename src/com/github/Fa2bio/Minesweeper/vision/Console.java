package com.github.Fa2bio.Minesweeper.vision;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import com.github.Fa2bio.Minesweeper.exceptions.ExitException;
import com.github.Fa2bio.Minesweeper.exceptions.ExplosionException;
import com.github.Fa2bio.Minesweeper.model.Board;

public class Console {
	
	private Board board;
	private Scanner entry = new Scanner(System.in);
	
	public Console(Board board) {
		this.board = board;
		runGame();
	}

	private void runGame() {
		
		try {
			boolean continueStatus = true;
			while(continueStatus) {
				gameCycle();
				System.out.print("Another game? (S/n): ");
				String answer = entry.nextLine();
				if(answer.equalsIgnoreCase("n")) {
					continueStatus = false;
				}else {
					board.restart();
				}
			}
		} catch (ExitException exit) {
			System.out.println("Over");
		} finally {
			entry.close();
		}
		
	}

	private void gameCycle() {
		try {
			
			while(!board.achievedGoal()) {
				
				System.out.println(board);
				String digitado = captureTypedValue("Type (x,y): ");
				
				Iterator<Integer> xy = Arrays.stream(digitado.split(","))
						.map(e -> Integer.parseInt(e.trim())).iterator();
			
				digitado = captureTypedValue("1 - Open or 2 - Mark(OFF): ");
				if(digitado.equals("1")) {
					board.open(xy.next(), xy.next());
				}else if(digitado.equals("2")){
					board.toggleMarcked(xy.next(), xy.next());
				}
			}
			
			System.out.println(board);
			System.out.println("You won !");
			
		}catch(ExplosionException explosao) {
			System.out.println(board);
			System.out.println("You lost !");
		}
		
	}
	
	private String captureTypedValue(String text) {
		System.out.print(text);
		String Typed = entry.nextLine();
		
		if(Typed.equalsIgnoreCase("exit")) {
			throw new ExitException();
		}
		
		return Typed;
	}
	
}
