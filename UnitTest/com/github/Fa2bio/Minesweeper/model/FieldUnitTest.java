package com.github.Fa2bio.Minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.Fa2bio.Minesweeper.exceptions.ExplosionException;

class FieldUnitTest {
	
	private Field field;
	
	@BeforeEach
	void startField() {
		field = new Field(2, 2);
	}
	
	@Test
	void neighborSameLineLeft() {
		Field neighbor = new Field(2, 1);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void neighborSameLineRight() {
		Field neighbor = new Field(2, 3);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void neighborSameColumnOnTop() {
		Field neighbor = new Field(1, 2);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void neighborSameColumnBelow() {
		Field neighbor = new Field(3, 2);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void neighborDiagonal() {
		Field neighbor = new Field(3, 3);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void notNeighbor() {
		Field neighbor = new Field(4, 3);
		boolean result = field.addNeighbor(neighbor);
		assertFalse(result);
	}
	
	@Test
	void testMarckedAtribute() {
		assertFalse(field.isMarcked());
	}
	
	@Test
	void testToggleMarcked() {
		field.toggleMarcked();
		assertTrue(field.isMarcked());
	}
	
	@Test
	void testOpenUnminedAndUnmarked() {
		assertTrue(field.open());
	}
	
	@Test
	void testOpenUnminedAndMarked() {
		field.toggleMarcked();
		assertFalse(field.open());
	}
	
	@Test
	void testOpenMinedAndMarked() {
		field.toggleMarcked();
		field.mined();
		assertFalse(field.open());
	}
	
	@Test
	void testOpenMinedAndUnmarked() {
		field.mined();
		assertThrows(ExplosionException.class,()->{
			field.open();
		});
	}
	
	@Test
	void openWithNeighbors() {
		
		//Invalid neighbor
		Field field00 = new Field(0,0);
		//Diagonally neighbor
		Field field33 = new Field(1,1);
		
		field33.addNeighbor(field00);
		
		field.addNeighbor(field33);
		field.open();
		assertTrue(field33.isOpen() && field00.isOpen());
	}
	
	@Test
	void testeAbrirComVizinhos2() {
		
		//Invalid neighbors
		Field field00 = new Field(0,0);
		Field field02 = new Field(0,2);
		
		field02.mined();
		
		//Diagonally neighbor
		Field field22 = new Field(3,3);
		
		field22.addNeighbor(field00);
		field22.addNeighbor(field02);
		
		field.addNeighbor(field22);
		field.open();
		
		assertTrue(field22.isOpen() && field00.isClosed());
	}

}
