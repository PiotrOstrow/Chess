package com.example.game;

import com.example.game.pieces.ChessPiece;
import com.example.game.pieces.King;

import java.util.ArrayList;
import java.util.List;

public class Game {

	// change to 2d array?
	private List<ChessPiece> pieces = new ArrayList<>();

	public Game() {
		pieces.add(new King(4, 4, Color.BLACK));
	}

	public ChessPiece getPiece(int x, int y) {
		for(ChessPiece piece : pieces)
			if(piece.getPosition().getX() == x && piece.getPosition().getY() == y)
				return piece;

		return null;
	}
}
