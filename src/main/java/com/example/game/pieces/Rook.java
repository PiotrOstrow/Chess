package com.example.game.pieces;

import com.example.game.Color;
import com.example.game.Game;
import com.example.ui.GameBoard;

import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece {

    public Rook(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    protected boolean canMove(Game game, int x, int y) {
        int dir;
        if (position.getX() == x && position.getY() != y) { // if X is the same, and Y is different -> move up and down
            // figure out direction of the move
            dir = position.getY() < y ? 1 : -1;
            // check every piece on the way, starting from position + one step ahead
            for (int y1 = position.getY() + dir; y1 != y; y1 += dir) {
                // if there is a piece, can not move
                if (game.getPiece(x, y1) != null)
                    return false;
            }
            // no piece on the way, can move
            return true;
        }
        if (position.getX() != x && position.getY() == y) {
            dir = position.getX() < x ? 1 : -1;
            for (int x1 = position.getX() + dir; x1 != x; x1 += dir) {
                if (game.getPiece(x1, y) != null)
                    return false;
            }
            return true;
        }
            return false;
    }

    @Override
    public String getImagePath() {
        if (color == Color.WHITE)
            return "Pixel_Art_Chess_DevilsWorkshop_V03\\chess\\white_rook.png";
        return "Pixel_Art_Chess_DevilsWorkshop_V03\\chess\\black_rook.png";
    }

    private boolean isValidPosition(Game game, int x, int y) {
        return (position.getX() != x || position.getY() != y) && (game.getPiece(x, y) == null || game.getPiece(x, y).color != this.color);
    }

    @Override
    public List<Position> getPossibleMoves(Game game) {
        List<Position> list = new ArrayList<>();

        for(int x1 = position.getX() - 1; x1 >= 0; x1--) {
            if (isValidPosition(game, x1, position.getY()) && canMove(game, x1, position.getY()))
                list.add(new Position(x1, position.getY()));
        }
        for(int x1 = position.getX() + 1; x1 < 8; x1++) {
            if (isValidPosition(game, x1, position.getY()) && canMove(game, x1, position.getY()))
                list.add(new Position(x1, position.getY()));
        }
        for(int y1 = position.getY() - 1; y1 >= 0; y1--) {
            if (isValidPosition(game, position.getX(), y1) && canMove(game, position.getX(), y1))
                list.add(new Position(position.getX(), y1));
        }
        for(int y1 = position.getY() + 1; y1 < 8; y1++) {
            if (isValidPosition(game, position.getX(), y1) && canMove(game, position.getX(), y1))
                list.add(new Position(position.getX(), y1));
        }
        return list;
    }
}
