package com.example.game.pieces;

import com.example.game.Color;
import com.example.game.Game;
import java.util.List;

public abstract class ChessPiece {

	protected final Color color;
	protected final Position position;

	private boolean hasMoved = false;

	public ChessPiece(int x, int y, Color color) {
		this.color = color;
		this.position = new Position(x, y);
	}

	public final boolean move(Game game, int x, int y) {
		if (!canMoveInternal(game, x, y))
			return false;

		hasMoved = true;
		position.set(x, y);

		return true;
	}

	private boolean canMoveInternal(Game game, int x, int y) {
		if (x < 0 || y < 0 || x >= 8 || y >= 8)
			return false;

		// chess piece at target location, can not take out piece of the same color
		ChessPiece target = game.getPiece(x, y);
		if (target != null && target.color == this.color)
			return false;

		return canMove(game, x, y);
	}

	/**
	 * returns max travel distance, until the edge of the board or the position of a piece that can be captured
	 */
	protected int maxTravelDistance(Game game, int xDir, int yDir){
		int distance = 0;
		for (int x = getPosition().getX() + xDir, y = getPosition().getY() + yDir; x < 8 && y < 8 && x >= 0 && y >= 0; x += xDir, y += yDir) {
			if (game.getPiece(x, y) != null) {
				if (game.getPiece(x, y).color != this.color)
					distance++;
				break;
			} else {
				distance++;
			}
		}
		return distance;
	}

	public boolean hasMoved() {
		return hasMoved;
	}

	protected abstract boolean canMove(Game game, int x, int y);

	public abstract List<Position> getPossibleMoves(Game game);

	public final Position getPosition() {
		return position;
	}

	public Color getColor(){
		return color;
	}

	public boolean isPromoteAble(){
		return false;
	}

	public boolean getCanMove(Game game, int x, int y){ return canMove(game,x,y);}
}
