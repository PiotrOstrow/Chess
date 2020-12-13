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
        if ((Math.abs(position.getX()-x)==1) && position.getY()==y-dir && game.getPiece(x,y)!=null)
            return true;

        if ((y==3.5+ 1.5*dir)&&(game.getPiece(x,y-dir) instanceof Pawn)&&(game.getPiece(x,y-dir).color != this.color)){
            if (game.getMoveLogStack().lastElement().fromX==x && game.getMoveLogStack().lastElement().fromY==y+dir
                    && game.getMoveLogStack().lastElement().toY ==y-dir)
                return true;}

        /*
        if ((y==3.5+ 0.5*dir)&&(game.getPiece(x+1,y) instanceof Pawn)&&(game.getPiece(x+1,y).color != this.color)){
            if (game.getMoveLogStack().lastElement().fromX==x+1 && game.getMoveLogStack().lastElement().fromY==y+dir*2
                    && game.getMoveLogStack().lastElement().toY ==y)
                return true;}
        if ((y==3.5+ 0.5*dir)&&(game.getPiece(x-1,y) instanceof Pawn)&&(game.getPiece(x-1,y).color != this.color)) {
            if (game.getMoveLogStack().lastElement().fromX == x - 1 && game.getMoveLogStack().lastElement().fromY == y + dir * 2
                    && game.getMoveLogStack().lastElement().toY == y)
                return true;
        }

         */

        return false;
    }

    @Override
    public List<Position> getPossibleMoves(Game game) {
        List<Position> list = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        int dir=1;
        if (this.color==color.WHITE) {dir = -1;}

        if (y == 3.5 + 3.5*dir){
            return list;
        }

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



        //en passant
        if (game.getMoveLogStack().size()>0) {
            if ((y == 3.5 + 0.5 * dir) && (game.getPiece(x - 1, y) instanceof Pawn) && (game.getPiece(x - 1, y).color != this.color)) {
                if (game.getMoveLogStack().lastElement().fromX == x - 1 && game.getMoveLogStack().lastElement().fromY == y + dir * 2
                        && game.getMoveLogStack().lastElement().toY == y)
                    //TODO before implementing: check if that was last move
                    list.add(new Position(x - 1, y + dir));
            }
            if ((y == 3.5 + 0.5 * dir) && (game.getPiece(x + 1, y) instanceof Pawn) && (game.getPiece(x + 1, y).color != this.color)) {
                if (game.getMoveLogStack().lastElement().fromX == x + 1 && game.getMoveLogStack().lastElement().fromY == y + dir * 2
                        && game.getMoveLogStack().lastElement().toY == y)
                    //TODO before implementing: check if that was last move
                    list.add(new Position(x + 1, y + dir));
            }
        }





        return list;
    }

    @Override
    public boolean isPromoteAble(){
        return true;
    }


}
