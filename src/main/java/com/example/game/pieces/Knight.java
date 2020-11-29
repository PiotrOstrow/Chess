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

        if(x_diff + y_diff == 3)
            return true;

        return false;

    }

    @Override
    public String getImagePath() {
        if(color == Color.WHITE)
            return "Pixel_Art_Chess_DevilsWorkshop_V03\\chess\\white_knight.png";
        else
        return "Pixel_Art_Chess_DevilsWorkshop_V03\\chess\\black_knight.png";
    }
   
    private boolean isValidPosition(Game game, int x, int y) {
        return x >= 0 && y >= 0 && x < 8 && y < 8
                && (game.getPiece(x, y) == null || game.getPiece(x, y).color != this.color);

    }


    @Override
    public List<Position> getPossibleMoves(Game game) {
        List<Position> list = new ArrayList<>();


        if(isValidPosition(game, position.getX() + 1, position.getY() + 2))
            list.add(new Position(position.getX() + 1, position.getY() + 2));
        if(isValidPosition(game, position.getX() + 2, position.getY() + 1))
            list.add(new Position(position.getX() + 2, position.getY() + 1));
        if(isValidPosition(game, position.getX() - 1, position.getY() - 2))
            list.add(new Position(position.getX() - 1, position.getY() - 2));
        if(isValidPosition(game, position.getX() - 2, position.getY() - 1))
            list.add(new Position(position.getX() - 2, position.getY() - 1));
        if(isValidPosition(game, position.getX() - 1, position.getY() + 2))
            list.add(new Position(position.getX() - 1, position.getY() + 2));
        if(isValidPosition(game, position.getX() + 1, position.getY() - 2))
            list.add(new Position(position.getX() + 1, position.getY() - 2));
        if(isValidPosition(game, position.getX() + 2, position.getY() - 1))
            list.add(new Position(position.getX() + 2, position.getY() - 1));
        if(isValidPosition(game, position.getX() - 2, position.getY() + 1))
            list.add(new Position(position.getX() - 2, position.getY() + 1));


        return list;
    }




}

