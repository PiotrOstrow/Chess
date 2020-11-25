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
	}
}
