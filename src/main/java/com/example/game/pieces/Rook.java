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
        int direction;
        if(position.getX() == x && position.getY() != y) { // if X is the same, and Y is different -> move up and down
            // figure out direction of the move
            direction = position.getY() < y ? 1 : -1;
            // check every piece on the way, starting from position + one step ahead
            for(int y1 = position.getY() + direction; y1 != y; y1 += direction) {
                // if there is a piece, can not move
                if(game.getPiece(x, y1) != null)
                    return false;
            }
            // no piece on the way, can move
            return true;
        } else if(position.getX() != x && position.getY() == y) {
            direction = position.getX() < x ? 1 : -1;
            for(int x1 = position.getX() + direction; x1 != x; x1 += direction) {
                if(game.getPiece(x1, y) != null)
                    return false;
            }
            return true;
        }
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
