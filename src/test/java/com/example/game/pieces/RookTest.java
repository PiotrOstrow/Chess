package com.example.game.pieces;

import com.example.game.Color;
import com.example.game.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RookTest {

    private Game game;
    private Rook rook;

    @BeforeAll
    void init() {
        game = new Game();
        rook = new Rook(4, 4, Color.WHITE);
        game.addPiece(rook);
    }

    @Test
    void getPossibleMoves() {
        assertEquals(rook.getPossibleMoves(game).size(), 14);
    }

    @Test
    void testCanMove() {
        for(Position possibleMovePosition : rook.getPossibleMoves(game))
            assertTrue(rook.canMove(game, possibleMovePosition.getX(), possibleMovePosition.getY()));

        for(Position possibleMovePosition : rook.getPossibleMoves(game))
            assertFalse(possibleMovePosition.isDiagonal(rook.getPosition()));
    }
}
