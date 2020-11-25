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

	@Override
	public String getImagePath() {
		if(color == Color.WHITE)
			return "Pixel_Art_Chess_DevilsWorkshop_V03\\chess\\white_king.png";
		return "Pixel_Art_Chess_DevilsWorkshop_V03\\chess\\black_king.png";
	}

	@Override
	public List<Position> getPossibleMoves(Game game) {
		List<Position> list = new ArrayList<>();

		// TODO: check if position taken by same color, if move results in check
		// go through 3x3 area around the king, add the position as long as it's not equals to own position
		for(int x = Math.max(0, position.getX() - 1); x <= Math.min(7, position.getX() + 1); x++)
			for(int y = Math.max(0, position.getY() - 1); y <= Math.min(7, position.getY() + 1); y++)
				if(position.getX() != x || position.getY() != y)
					list.add(new Position(x, y));

		return list;
	}
}
