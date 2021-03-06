package com.example.game.pieces;

import com.example.game.Color;
import com.example.game.Game;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece{

	public King(int x, int y, Color color) {
		super(x, y, color);
	}

	@Override
	protected boolean canMove(Game game, int x, int y) {
		return position.distance(x, y) < 2;
	}

	private boolean isValidPosition(Game game, int x, int y) {
		return (position.getX() != x || position.getY() != y) && (game.getPiece(x, y) == null || game.getPiece(x, y).color != this.color);
	}

	@Override
	public List<Position> getPossibleMoves(Game game) {
		List<Position> list = new ArrayList<>();

		// TODO: check if move results in check
		// go through 3x3 area around the king, add the position as long as it's not equals to own position, and it's
		// not occupied by pieces of the same color
		for(int x = Math.max(0, position.getX() - 1); x <= Math.min(7, position.getX() + 1); x++)
			for(int y = Math.max(0, position.getY() - 1); y <= Math.min(7, position.getY() + 1); y++)
				if(isValidPosition(game, x, y))
					list.add(new Position(x, y));

		return list;
	}
}
