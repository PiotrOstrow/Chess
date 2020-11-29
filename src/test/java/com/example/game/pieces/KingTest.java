package com.example.game.pieces;

import com.example.game.Color;
import com.example.game.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KingTest {

	private Game game;

	private King whiteKing;
	private King blackKing;

	@BeforeAll
	void init() {
		game = new Game();

		whiteKing = new King(2, 2, Color.WHITE);
		blackKing = new King(4, 4, Color.BLACK);

		game.addPiece(whiteKing);
		game.addPiece(blackKing);
	}

	@Test
	void testGetPossibleMoves() {
		// with no pieces around, there should be 8 possible moves
		assertEquals(whiteKing.getPossibleMoves(game).size(), 8);
		assertEquals(blackKing.getPossibleMoves(game).size(), 8);

		// move distance for king should be less or equal to square root of 2
		for(Position possibleMovePosition : whiteKing.getPossibleMoves(game))
			assertTrue(possibleMovePosition.distance(whiteKing.getPosition()) <= Math.sqrt(2));
		for(Position possibleMovePosition : blackKing.getPossibleMoves(game))
			assertTrue(possibleMovePosition.distance(blackKing.getPosition()) <= Math.sqrt(2));
	}

	@Test
	void testCanMove() {
		// canMove should return true for every possible move
		for(Position possibleMovePosition : whiteKing.getPossibleMoves(game))
			assertTrue(whiteKing.canMove(game, possibleMovePosition.getX(), possibleMovePosition.getY()));

		for(Position possibleMovePosition : blackKing.getPossibleMoves(game))
			assertTrue(blackKing.canMove(game, possibleMovePosition.getX(), possibleMovePosition.getY()));
	}
}
