package com.example.game.pieces;

import com.example.game.Color;
import com.example.game.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BishopTest {

	private Game game;

	private Bishop bishop;

	@BeforeAll
	void init() {
		game = new Game();
		bishop = new Bishop(2, 2, Color.WHITE);
		game.addPiece(bishop);
	}

	@Test
	void testGetPossibleMoves() {
		// all possible moves should be diagonal
		for(Position possibleMovePosition : bishop.getPossibleMoves(game))
			assertTrue(possibleMovePosition.isDiagonal(bishop.getPosition()));
	}

	@Test
	void testCanMove() {
		// canMove should return true for every possible move
		for(Position possibleMovePosition : bishop.getPossibleMoves(game))
			assertTrue(bishop.canMove(game, possibleMovePosition.getX(), possibleMovePosition.getY()));
	}
}
