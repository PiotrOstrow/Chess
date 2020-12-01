package com.example.game;

import com.example.game.pieces.ChessPiece;
import com.example.game.pieces.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Computer extends Player implements GameCallback {

	private final Game game;
	private final Random random = new Random();

	public Computer(Game game, Color color) {
		super(color);
		this.game = game;
		game.addGameCallback(this);
	}

	@Override
	public void onMoved() {
		if (game.getCurrentMovePlayer() == this.color) {
			// random piece, random move
			List<ChessPiece> pieces = new ArrayList<>();
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					ChessPiece piece = game.getPiece(x, y);
					if (piece != null && piece.getColor() == this.color)
						pieces.add(piece);
				}
			}

			if(pieces.size() == 0)
				return;

			// shuffle piece list, first one that can move will be random
			Collections.shuffle(pieces);

			ChessPiece piece = null;
			List<Position> possibleMoves = null;
			for(ChessPiece p : pieces){
				possibleMoves = p.getPossibleMoves(game);
				if(possibleMoves.size() > 0){
					piece = p;
					 break;
				}
			}

			if(possibleMoves == null || possibleMoves.size() == 0)
				return;

			Position position = possibleMoves.get(random.nextInt(possibleMoves.size()));
			game.move(piece, position.getX(), position.getY());
		}
	}
}
