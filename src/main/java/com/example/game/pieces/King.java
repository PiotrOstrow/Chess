package com.example.game.pieces;

import com.example.game.Color;
import com.example.game.Game;

public class King extends ChessPiece{

	public King(int x, int y, Color color) {
		super(x, y, color);
	}

	@Override
	protected boolean canMove(Game game, int x, int y) {
		return position.distance(x, y) < 2;
	}
}
