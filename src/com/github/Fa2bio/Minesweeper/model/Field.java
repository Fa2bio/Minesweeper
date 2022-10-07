package com.github.Fa2bio.Minesweeper.model;

import java.util.ArrayList;
import java.util.List;

import com.github.Fa2bio.Minesweeper.exceptions.ExplosionException;

public class Field {
	
	//position x
	private final int line;
	//position y
	private final int column;
	
	//Has the field been opened yet?
	private boolean opened = false;
	//Does the field contain a mine?
	private boolean mined = false;
	//Has the field been marked?
	private boolean marcked = false;
	
	//Self-referential relationship one-to-many
	private List<Field> neighborhood = new ArrayList<Field>();
	
	//Class constructor
	Field(int line, int column){
		this.line = line;
		this.column = column;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}
	
	void setOpened(boolean opened) {
		this.opened = opened;
	}
	
	public boolean isOpen() {
		return opened;
	}
	
	public boolean isClosed() {
		return !isOpen();
	}
	
	public boolean isMined() {
		return mined;
	}
	
	public boolean isMarcked() {
		return marcked;
	}
	
	void mined() {
		mined = true;
	}
	
	//if the field has not yet been opened, it can be marked as a possible mine location marked is true
	void toggleMarcked() {
		if(!opened) {
			marcked = !marcked;
		}
	}
	
	void restart() {
		opened = false;
		mined = false;
		marcked = false;
	}
	
	//Open README to understand that logical
	boolean addNeighbor(Field neighbor) {
		boolean diferentLine = this.line != neighbor.line;
		boolean diferentColumn = this.column != neighbor.column;
		boolean diagonal = diferentLine && diferentColumn;
		
		int deltaLine = Math.abs(this.line - neighbor.line);
		int deltaColumn = Math.abs(this.column - neighbor.column);
		int delta = deltaLine + deltaColumn;
		
		if(delta==1 && !diagonal) {
			neighborhood.add(neighbor);
			return true;
		}else if(delta==2 && diagonal) {
			neighborhood.add(neighbor);
			return true;
		}else return false;
	}
	
	//We don't expect to find any minefields
	boolean safetedNeighborhood() {
		return neighborhood
				.stream()
				.noneMatch(n -> n.mined);
	}
	
	//We hope to find a number of mines near a neighborhood
	long mineInNeighborhood() {
		return neighborhood
				.stream()
				.filter(n -> n.mined)
				.count();
	}
	
	//function to open one field, or many empyt fields via recursive call
	boolean open() {
		if(!opened && !marcked) {
			opened = true;
			//if the chosen field is mined, throws an explosion exception
			if(mined) throw new ExplosionException();
			if(safetedNeighborhood()) neighborhood.forEach(n -> n.open());
			//return true if all the fields in the neighborhood is opened with sucess
			return true;
		//if the field is open or marked, it is not possible to open it and returns false
		}else return false;
	}
	
	//Logic to define if we can open all fields without mines and mark all mine fields
	boolean achievedGoal() {
		boolean unraveled = !mined && opened;
		boolean protecteds = mined && marcked;
		return unraveled || protecteds;
	}
	
	public String toString() {
		if(marcked) {
			return "x";
		}else if(opened && mined) {
			return "*";
		}else if(opened && (mineInNeighborhood() > 0)) {
			return Long.toString(mineInNeighborhood());
		}else if(opened) {
			return " ";
		}else {
			return "?";
		}
	}
}
