package com.example.game.pieces;

import com.example.game.Color;
import com.example.game.Game;

import java.util.List;

public class Rook extends ChessPiece {

    public Rook(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    protected boolean canMove(Game game, int x, int y) {
        return false;
    }

    @Override
    public String getImagePath() {
        return null;
    }

    @Override
    public List<Position> getPossibleMoves(Game game) {
        return null;
    }
}
