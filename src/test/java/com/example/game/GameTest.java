package com.example.game;

import com.example.game.pieces.King;
import com.example.game.pieces.Pawn;
import com.example.game.pieces.Queen;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static org.junit.jupiter.api.Assertions.*;

@Testable
public class GameTest {

	@Test
	void testPromote() {
		Game game = new Game();
		game.setUpNormal();

		assertThrows(Exception.class, () -> game.promote(Queen.class));

		// replace black rook with white pawn to promote
		game.addPiece(new Pawn(7, 0, Color.WHITE));

		assertThrows(IllegalArgumentException.class, () -> game.promote(King.class));
	}

	@Test
	void testIsInCheckMate() {

	}

	@Test
	void getPossibleMoves() {

	}
}
