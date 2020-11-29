package com.example.game.pieces;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static org.junit.jupiter.api.Assertions.*;

@Testable
public class PositionTest {

	@Test
	void testIsDiagonal() {
		Position pos1 = new Position(0, 0);
		Position pos2 = new Position(7, 7);

		assertTrue(pos1.isDiagonal(pos2));
		assertFalse(pos1.isDiagonal(1, 0));
		assertTrue(pos1.isDiagonal(4, 4));
	}

	@Test
	void testDistance() {
		Position pos1 = new Position(4, 4);

		assertEquals(pos1.distance(5, 4), 1.0);
		assertTrue(pos1.distance(5, 5) < 2.0);
	}
}
