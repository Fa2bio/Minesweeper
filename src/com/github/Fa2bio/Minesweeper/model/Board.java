package com.github.Fa2bio.Minesweeper.model;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.github.Fa2bio.Minesweeper.exceptions.ExplosionException;

public class Board {
	
	private final int lines;
	private final int columns;
	private final int mines;
	
	private List<Field> fields = new ArrayList<Field>();
	public Board(int lines, int columns, int mines){
		this.lines = lines;
		this.columns = columns;
		this.mines = mines;
		
		generateFields();
		associateNeighbors();
		drawMines();
	}
	
	public void open(int lines, int columns) {
		try {
			fields.parallelStream()
				.filter(f -> f.getLine() == lines && f.getColumn() == columns)
				.findFirst()
				.ifPresent(f -> f.open());
		} catch (ExplosionException explosion) {
			fields.forEach(f -> f.setOpened(true));
			throw explosion;
		}
	}
	
	public void toggleMarcked(int lines, int columns) {
		fields.parallelStream()
		.filter(f -> f.getLine() == lines && f.getColumn() == columns)
		.findFirst()
		.ifPresent(f -> f.toggleMarcked());
	}
	
	public void restart() {
		fields.stream().forEach(c -> c.restart());
		drawMines();
	}
	
	public boolean achievedGoal() {
		return fields.stream().allMatch(f -> f.achievedGoal());
	}
	
	private void generateFields() {
		for (int line = 0; line < lines; line++) {
			for (int column = 0; column < columns; column++) {
				fields.add(new Field(line, column));
			}
		}
	}

	private void associateNeighbors() {
		for (Field f1 : fields) {
			for (Field f2 : fields) {
				f1.addNeighbor(f2);
			}
		}
		
	}

	private void drawMines() {
		long armedMines = 0;
		Predicate<Field> mined = m -> m.isMined();
		
 		do {
 			
 			int random = (int)(Math.random()*fields.size());
 			fields.get(random).mined();
 			armedMines = fields.stream().filter(mined).count();
			
		}while(armedMines < mines);	
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("  ");
		for (int column = 0; column < columns; column++) {
			sb.append(" ");
			sb.append(column);
			sb.append(" ");
		}
		sb.append("\n");
		
		int i = 0;
		
		for (int line = 0; line < lines; line++) {
			sb.append(line);
			sb.append(" ");
			for (int column = 0; column < columns; column++) {
				sb.append(" ");
				sb.append(fields.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
