package com.example.game.pieces;

import com.example.game.Color;
import com.example.game.Game;

public class Pawn extends ChessPiece{

    public Pawn(int x, int y, Color color) { super (x, y, color); }

    @Override
    protected boolean canMove(Game game, int x, int y) {
        if (this.color == color.BLACK) {

            return position.distance(x, y) == 1;
        }
    else return position.distance(x, y) == 2;
    }

}
