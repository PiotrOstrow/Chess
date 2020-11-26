package com.example.game.pieces;

import com.example.game.Color;
import com.example.game.Game;

import java.util.List;

public class Knight extends ChessPiece {

    public Knight(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    protected boolean canMove(Game game, int x, int y)  {
        int x_diff = Math.abs(x - this.getPosition().getX());
        int y_diff = Math.abs(y - this.getPosition().getY());

        if((x_diff == 1 && y_diff == 2) || (x_diff == 2 && y_diff == 1))
            return true;

        return false;

    }

    @Override
    public String getImagePath() {
        if(color == Color.WHITE)
            return "Pixel_Art_Chess_DevilsWorkshop_V03\\chess\\white_knight.png";
        return "Pixel_Art_Chess_DevilsWorkshop_V03\\chess\\black_knight.png";
    }

    @Override
    public List<Position> getPossibleMoves(Game game) {
        return null;
    }
}

