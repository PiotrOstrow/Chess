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

		assertThrows(Exception.class, () -> game.promote(Queen.class));

		// replace black rook with white pawn to promote
		game.addPiece(new Pawn(7, 0, Color.WHITE));

		assertThrows(IllegalArgumentException.class, () -> game.promote(King.class));

		// should not throw exception
		game.promote(Bishop.class);
	}

	@Test
	void testIsInCheckMate() {

	}

	@Test
	void getPossibleMoves() {
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
