package com.example.game;

import com.example.game.pieces.ChessPiece;

public class Move {

	public final ChessPiece movedPiece;
	public final int fromX;
	public final int fromY;
	public final int toX;
	public final int toY;
	public final ChessPiece captured;

	public Move(ChessPiece movedPiece, int fromX, int fromY, int toX, int toY, ChessPiece captured) {
		this.movedPiece = movedPiece;
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
		this.captured = captured;
	}
}
