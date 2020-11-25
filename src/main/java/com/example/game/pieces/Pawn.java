package com.example.game.pieces;

import com.example.game.Color;
import com.example.game.Game;

import java.util.Collections;
import java.util.List;

public class Pawn extends ChessPiece{

    public Pawn(int x, int y, Color color) { super (x, y, color); }

    @Override
    protected boolean canMove(Game game, int x, int y) {
        if (this.color == color.BLACK) {

            return position.distance(x, y) == 1;
        }
    else return position.distance(x, y) == 2;
    }

    @Override
    public String getImagePath() {
        return "Pixel_Art_Chess_DevilsWorkshop_V03\\chess\\white_king.png";
    }

    @Override
    public List<Position> getPossibleMoves(Game game) {
        return Collections.emptyList();
    }

}
