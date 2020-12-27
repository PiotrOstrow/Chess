package com.example.game;

import com.example.game.pieces.ChessPiece;
import com.example.game.pieces.Pawn;
import com.example.game.pieces.Position;
import com.example.game.pieces.Queen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Computer extends Player {

	private final Game game;
	private final Random random = new Random();

	// marked true when a pawn is moved up to promote, next move() must be a promotion
	private boolean promote;

	public Computer(Game game, Color color) {
		super(color);
		this.game = game;

		Timeline timeline = new Timeline(new KeyFrame(
				Duration.millis(200),
				ae -> move()));

		game.addGameCallback((move) -> {
			timeline.setDelay(Duration.millis(Math.random() * 1500));
			timeline.stop();
			timeline.play();
		});
	}

	private void move() {
		if (game.getCurrentMovePlayer() == this.color) {
			if (promote) {
				game.promote(Queen.class);
				promote = false;
			} else {
				// random piece, random move
				List<ChessPiece> pieces = new ArrayList<>();
				for (int x = 0; x < 8; x++) {
					for (int y = 0; y < 8; y++) {
						ChessPiece piece = game.getPiece(x, y);
						if (piece != null && piece.getColor() == this.color)
							pieces.add(piece);
					}
				}

				if (pieces.size() == 0)
					return;

				// shuffle piece list, first one that can move will be random
				Collections.shuffle(pieces);

				ChessPiece piece = null;
				List<Position> possibleMoves = null;
				for (ChessPiece p : pieces) {
					possibleMoves = game.getPossibleMoves(p);
					if (possibleMoves.size() > 0) {
						piece = p;
						break;
					}
				}

				if (possibleMoves == null || possibleMoves.size() == 0)
					return;

				Position position = possibleMoves.get(random.nextInt(possibleMoves.size()));
				game.move(piece, position.getX(), position.getY());

				promote = piece instanceof Pawn && (piece.getPosition().getY() == 0 || piece.getPosition().getY() == 7);
			}
		}
	}
}
