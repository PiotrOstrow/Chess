package com.example.game.pieces;

import com.example.game.Color;
import com.example.game.Game;

public class Rook extends ChessPiece {

    public Rook(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    protected boolean canMove(Game game, int x, int y) {
        return false;
    }
}
