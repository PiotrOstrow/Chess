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
        if(color == Color.WHITE)
            return "Pixel_Art_Chess_DevilsWorkshop_V03\\chess\\white_rook.png";
        return "Pixel_Art_Chess_DevilsWorkshop_V03\\chess\\black_rook.png";
    }

    @Override
    public List<Position> getPossibleMoves(Game game) {
        return null;
    }
}
