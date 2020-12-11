package com.example.game.pieces;

import com.example.game.Color;
import com.example.game.Game;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PawnTest {


    private Game game;

    private Pawn whitestartingpawn = new Pawn(5, 6, Color.WHITE);
    private Pawn blackstartingpawn = new Pawn(1, 1, Color.BLACK);
    private Pawn whiteordinarypawn = new Pawn(3, 4, Color.WHITE);
    private Pawn blackordinarypawn = new Pawn(4, 4, Color.BLACK);
    private Pawn whitestrikingpawn = new Pawn(1, 5, Color.WHITE);
    private Pawn blackstrikingpawn = new Pawn(6, 5, Color.BLACK);
    private Pawn whiteimpossiblepawn = new Pawn(5, 0, Color.WHITE);
    private Pawn blackimpossiblepawn = new Pawn(5, 7, Color.BLACK);
    private Pawn whiteblockedpawn = new Pawn(5, 3, Color.WHITE);
    private Pawn blackblockedpawn = new Pawn(5, 2, Color.BLACK);
    private Pawn whitepromotingpawn = new Pawn(2, 1, Color.WHITE);
    private Pawn blackpromotingpawn = new Pawn(2, 6, Color.BLACK);
    private Pawn whiteborderpawn = new Pawn(7, 2, Color.WHITE);
    private Pawn blackborderpawn = new Pawn(0, 4, Color.BLACK);

    Pawn[] allpawns = {whiteblockedpawn, whiteborderpawn, whiteimpossiblepawn,
            whiteordinarypawn, whitepromotingpawn, whitestartingpawn, whitestrikingpawn,
            blackblockedpawn, blackborderpawn, blackimpossiblepawn, blackordinarypawn,
            blackpromotingpawn, blackstartingpawn, blackstrikingpawn};
    String[] names = {"whiteblockedpawn", "whiteborderpawn", "whiteimpossiblepawn",
            "whiteordinarypawn", "whitepromotingpawn", "whitestartingpawn", "whitestrikingpawn",
            "blackblockedpawn", "blackborderpawn", "blackimpossiblepawn", "blackordinarypawn",
            "blackpromotingpawn", "blackstartingpawn", "blackstrikingpawn"};

    int u;
    int v;

    @BeforeAll
    void init() {
        game = new Game();

        for (int i = 0; i < allpawns.length; i++) {
            game.addPiece(allpawns[i]);
        }
        u = 0;
        v = 0;

    }



    @RepeatedTest(14)
    void testGetPossibleMoves() {



            assertTrue(allpawns[u].getPossibleMoves(game).size() <= 4);
            //for bughunting
            //System.out.println(names[u] + " has " + allpawns[u].getPossibleMoves(game).size() + " move(s), that's ok");

        u++;
    }

    @RepeatedTest(14)
    void testCanMove() {

            for (int j = 0; j < allpawns[v].getPossibleMoves(game).size(); j++) {

               assertTrue(allpawns[v].canMove(game, allpawns[v].getPossibleMoves(game).get(j).getX(),
                       allpawns[v].getPossibleMoves(game).get(j).getY()));
                //for bughunting
                //System.out.println(names[v] +" can move to "+ allpawns[v].getPossibleMoves(game).get(j).getX() + ","
                //      + allpawns[v].getPossibleMoves(game).get(j).getY());

            }
            v++;
    }
}
