package com.example.game;

import com.example.game.pieces.ChessPiece;
import com.example.game.pieces.King;
import com.example.game.pieces.Pawn;

import java.util.ArrayList;
import java.util.List;

public class Game {

	// change to 2d array?
	private List<ChessPiece> pieces = new ArrayList<>();

	public Game() {

		pieces.add(new King(4, 4, Color.BLACK));
		pieces.add(new Pawn(4, 5, Color.BLACK));
		pieces.add(new Pawn(5, 4, Color.WHITE));
	}

	public ChessPiece getPiece(int x, int y) {
		for(ChessPiece piece : pieces)
			if(piece.getPosition().getX() == x && piece.getPosition().getY() == y)
				return piece;

		return null;
	}
}
