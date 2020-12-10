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
    void testGetPossibleMoves() {
        // with no pieces around, there should be 14 possible moves
        assertEquals(rook.getPossibleMoves(game).size(), 14);


        // all possible moves should be horizontally or vertically
        for(Position possibleMovePosition : rook.getPossibleMoves(game))
            assertFalse(possibleMovePosition.isDiagonal(rook.getPosition()));
    }

    @Test
    void testCanMove() {
        // test every possible move
        for(Position possibleMovePosition : rook.getPossibleMoves(game))
            assertTrue(rook.canMove(game, possibleMovePosition.getX(), possibleMovePosition.getY()));

    }
}
