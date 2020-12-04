package com.example.game.pieces;

import com.example.game.Color;
import com.example.game.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KnightTest {
    private Game game;

    private Knight whiteKnight;
    private Knight blackKnight;


    @BeforeAll
    void init() {
        game = new Game();

        whiteKnight = new Knight(3, 3, Color.WHITE);
        blackKnight = new Knight(5, 5, Color.BLACK);


        game.addPiece(whiteKnight);
        game.addPiece(blackKnight);
    }




    @Test
    public void testGetPossibleMoves() {
    // with no pieces around, there should be 8 possible moves
        assertEquals(whiteKnight.getPossibleMoves(game).size(), 8);
        assertEquals(blackKnight.getPossibleMoves(game).size(), 8);



    }

    @Test
    void testCanMove() {
        // test every possible move
        for(Position possibleMovePosition : whiteKnight.getPossibleMoves(game))
            assertTrue(whiteKnight.canMove(game, possibleMovePosition.getX(), possibleMovePosition.getY()));

        for(Position possibleMovePosition : blackKnight.getPossibleMoves(game))
            assertTrue(blackKnight.canMove(game, possibleMovePosition.getX(), possibleMovePosition.getY()));



        // out of bounds
        whiteKnight.canMove(game,1, 1);
        assertFalse(whiteKnight.canMove(game,-1, 0));

        // same spot
        assertFalse(whiteKnight.canMove(game,3, 3));

        // empty spot, but invalid movement
        assertFalse(whiteKnight.canMove(game,4, 4));




    }
}

