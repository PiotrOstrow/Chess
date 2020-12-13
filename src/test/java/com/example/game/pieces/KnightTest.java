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



    @BeforeAll
    void init() {
        game = new Game();

        whiteKnight = new Knight(3, 3, Color.WHITE);



        game.addPiece(whiteKnight);

    }




    @Test
    public void testGetPossibleMoves() {
    // with no pieces around, there should be 8 possible moves
        assertEquals(whiteKnight.getPossibleMoves(game).size(), 8);




    }

    @Test
    void testCanMove() {
        // test every possible move
        for(Position possibleMovePosition : whiteKnight.getPossibleMoves(game))
            assertTrue(whiteKnight.canMove(game, possibleMovePosition.getX(), possibleMovePosition.getY()));





        // empty spot, but invalid movement
        assertFalse(whiteKnight.canMove(game,4, 4));




    }
}

