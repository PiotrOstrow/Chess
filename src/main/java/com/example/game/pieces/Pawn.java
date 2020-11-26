package com.example.game.pieces;

import com.example.game.Color;
import com.example.game.Game;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece{

    public Pawn(int x, int y, Color color) { super (x, y, color); }

    @Override
    protected boolean canMove(Game game, int x, int y) {
        int dir=1;
        if (this.color==color.WHITE) dir = -1;

        if (game.getPiece(x,y)==null)
            if (position.getX()==x)
                if (position.getY()==y-dir)
                    return true;
                else if (position.getY()==y-2*dir && position.getY()==3.5 - 2.5 * dir && game.getPiece(x,y-dir)==null)
                    return true;
        if ((Math.abs(position.getX()-x)==1) && position.getY()==y-dir)
            return true;

        return false;
    }

    @Override
    public String getImagePath() {
        if (this.color == color.WHITE) {
            return "Pixel_Art_Chess_DevilsWorkshop_V03\\chess\\white_pawn.png";
        }
        else return "Pixel_Art_Chess_DevilsWorkshop_V03\\chess\\black_pawn.png";
    }

    @Override
    public List<Position> getPossibleMoves(Game game) {
        List<Position> list = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        int dir=1;
        if (this.color==color.WHITE) {dir = -1;}

        if (game.getPiece(x, y+dir)==null) {
            list.add(new Position(x, y + dir));
            if ((y == 3.5 - 2.5 * dir) && game.getPiece(x, y + 2 * dir) == null) {
                list.add(new Position(x, y + 2 * dir));
            }
        }
        if (game.getPiece(x-1, y+dir)!=null && game.getPiece(x-1, y+dir).color != this.color)
            list.add(new Position(x-1,y+dir));
        if (game.getPiece(x+1, y+dir)!=null && game.getPiece(x+1, y+dir).color != this.color)
            list.add(new Position(x+1,y+dir));

        return list;
    }


}
