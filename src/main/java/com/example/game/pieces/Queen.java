package com.example.game.pieces;

import com.example.game.Color;
import com.example.game.Game;

import java.util.ArrayList;
import java.util.List;

public class Queen extends ChessPiece {

	public Queen(int x, int y, Color color) {
		super(x, y, color);
	}

	@Override
	protected boolean canMove(Game game, int x, int y) {
		if (!position.isDiagonal(x, y) && (position.getX() != x && position.getY() != y))
			return false;

		int xDir = getPosition().getX() == x ? 0 : getPosition().getX() - x > 0 ? -1 : 1;
		int yDir = getPosition().getY() == y ? 0 : getPosition().getY() - y > 0 ? -1 : 1;

		for (int x1 = position.getX() + xDir, y1 = position.getY() + yDir; x1 != x || y1 != y; x1 += xDir, y1 += yDir)
			if (game.getPiece(x1, y1) != null)
				return false;

		return true;
	}

	@Override
	public String getImagePath() {
		if(color == Color.WHITE)
			return "Pixel_Art_Chess_DevilsWorkshop_V03/chess/white_queen.png";
		return "Pixel_Art_Chess_DevilsWorkshop_V03/chess/black_queen.png";
	}

	@Override
	public List<Position> getPossibleMoves(Game game) {
		List<Position> list = new ArrayList<>();

		int[] directions = new int[]{-1, 0, 1};

		for(Integer xDir : directions) {
			for(Integer yDir : directions) {
				if(xDir == 0 && yDir == 0)
					continue;

				for(int i = 1; i <= maxTravelDistance(game, xDir, yDir); i++)
					list.add(new Position(getPosition().getX() + i * xDir, getPosition().getY() + i * yDir));
			}
		}
		return list;
	}
}
