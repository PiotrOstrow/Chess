package com.example.game.pieces;

import com.example.game.Color;
import com.example.game.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QueenTest {

	private Game game;
	private Queen queen;

	@BeforeAll
	void init() {
		game = new Game();
		queen = new Queen(3, 3, Color.WHITE);
		game.addPiece(queen);
	}

	@Test
	void testGetPossibleMoves() {
		List<Position> possibleMoves = queen.getPossibleMoves(game);

		for(Position p : possibleMoves)
			queen.canMove(game, p.getX(), p.getY());
	}

	@Test
	void testCanMove() {
		int x = queen.getPosition().getX();
		int y = queen.getPosition().getY();

		assertTrue(queen.canMove(game, x + 1, y + 1));
		assertTrue(queen.canMove(game, x + 1, y - 1));
		assertTrue(queen.canMove(game, x - 2, y - 2));
		assertTrue(queen.canMove(game, x + 1, y));
		assertTrue(queen.canMove(game, x, y + 3));
		assertFalse(queen.canMove(game, x + 1, y + 2));
	}
}
