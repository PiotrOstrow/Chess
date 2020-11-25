package com.example.game;

import com.example.game.pieces.ChessPiece;
import com.example.game.pieces.King;
import com.example.game.pieces.Knight;
import com.example.game.pieces.Pawn;

import java.util.ArrayList;
import java.util.List;

public class Game {

	// change to 2d array?
	private List<ChessPiece> pieces = new ArrayList<>();

	public Game() {

		pieces.add(new King(4, 4, Color.BLACK));

		setUpNormal();
	}

	public ChessPiece getPiece(int x, int y) {
		for(ChessPiece piece : pieces)
			if(piece.getPosition().getX() == x && piece.getPosition().getY() == y)
				return piece;

		return null;
	}

	public boolean move(ChessPiece chessPiece, int x, int y) {
		// TODO: check if move results in a check, update 2d array
		return chessPiece.move(this, x, y);
	}

	public void setUpNormal(){
		for (int i=0; i<=7; i++){
			pieces.add(new Pawn(i, 1, Color.WHITE));
			pieces.add(new Pawn(i, 6, Color.BLACK));
		}
		pieces.add(new King(4, 0, Color.WHITE));
		pieces.add(new King(4, 7, Color.BLACK));
		pieces.add(new Knight(1, 0, Color.WHITE));
		pieces.add(new Knight(1, 7, Color.BLACK));
		pieces.add(new Knight(6, 0, Color.WHITE));
		pieces.add(new Knight(6, 7, Color.BLACK));
		/*
		pieces.add(new Rook(0, 0, Color.WHITE));
		pieces.add(new Rook(0, 7, Color.BLACK));
		pieces.add(new Rook(7, 0, Color.WHITE));
		pieces.add(new Rook(7, 7, Color.BLACK));
		pieces.add(new Bishop(2, 0, Color.WHITE));
		pieces.add(new Bishop(2, 7, Color.BLACK));
		pieces.add(new Bishop(5, 0, Color.WHITE));
		pieces.add(new Bishop(5, 7, Color.BLACK));
		pieces.add(new Queen(3, 0, Color.WHITE));
		pieces.add(new Queen(3, 7, Color.BLACK));
		*/
	}
}
