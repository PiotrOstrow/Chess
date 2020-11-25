package com.example.game.pieces;

import com.example.game.Color;
import com.example.game.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ChessPiece {

	protected final Color color;
	protected final Position position;

	public ChessPiece(int x, int y, Color color) {
		this.color = color;
		this.position = new Position(x, y);
	}

	public final boolean move(Game game, int x, int y) {
		if (!canMoveInternal(game, x, y))
			return false;

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

	protected abstract boolean canMove(Game game, int x, int y);

	public abstract String getImagePath();

	public abstract List<Position> getPossibleMoves(Game game);

	public final Position getPosition() {
		return position;
	}
}
