package com.example.game;

import com.example.game.pieces.*;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testable
public class GameTest {

	@Test
	void testPromote() {
		Game game = new Game(false);
		game.setUpNormal();

		// nothing to promote
		assertThrows(IllegalStateException.class, () -> game.promote(Queen.class));

		// replace black pawn with white pawn and move up to promote
		Pawn pawn = new Pawn(7, 1, Color.WHITE);
		game.addPiece(pawn);
		assertTrue(game.move(pawn, 6, 0));

		// can't promote to a king or a pawn
		assertThrows(IllegalArgumentException.class, () -> game.promote(King.class));
		assertThrows(IllegalArgumentException.class, () -> game.promote(Pawn.class));

		// should not throw exception, otherwise test will fail as intended
		game.promote(Bishop.class);
	}

	@Test
	void testIsInCheckMate() {
		Game game = new Game(false);

		King whiteKing = new King(4,7, Color.WHITE);
		Rook whiteRook = new Rook(2, 6, Color.WHITE);
		King blackKing = new King(2, 2, Color.BLACK);

		game.addPiece(whiteKing);
		game.addPiece(whiteRook);
		game.addPiece(blackKing);

		// black king in check

		assertFalse(game.isInCheckMate(Color.BLACK));

		Game game2 = new Game(false);
		game2.addPiece(new King(0, 0, Color.BLACK));
		game2.addPiece(new King(4, 7, Color.WHITE));
		game2.addPiece(new Queen(0, 7, Color.WHITE));
		game2.addPiece(new Rook(1, 5, Color.WHITE));

		assertTrue(game2.isInCheckMate(Color.BLACK));
		assertFalse(game2.isInCheckMate(Color.WHITE));

		Game game3 = new Game(false);
		game3.addPiece(new King(0, 0, Color.BLACK));
		game3.addPiece(new Bishop(2, 2, Color.BLACK));
		game3.addPiece(new King(4, 7, Color.WHITE));
		game3.addPiece(new Queen(0, 7, Color.WHITE));
		game3.addPiece(new Rook(1, 5, Color.WHITE));

		assertFalse(game3.isInCheckMate(Color.BLACK));
		assertFalse(game3.isInCheckMate(Color.WHITE));
	}

	@Test
	void testGetPossibleMoves() {
		Game game = new Game(false);

		King whiteKing = new King(0, 0, Color.WHITE);
		King blackKing = new King(7, 7, Color.BLACK);
		Rook blackRook = new Rook(1, 7, Color.BLACK);

		game.addPiece(whiteKing);
		game.addPiece(blackKing);
		game.addPiece(blackRook);

		List<Position> whiteKingPossibleMoves = game.getPossibleMoves(whiteKing);
		List<Position> blackRookPossibleMoves = game.getPossibleMoves(blackRook);

		// white king should not have any possible moves on squares that are controlled by opponents piece
		for(Position p1 : whiteKingPossibleMoves)
			for(Position p2 : blackRookPossibleMoves)
				assertFalse(p1.getX() == p2.getX() && p1.getY() == p2.getY());
	}
}
