package com.example.game;

import com.example.game.pieces.ChessPiece;
import com.example.game.pieces.King;
import com.example.game.pieces.Pawn;

public class Move {

	// in case of castling, movedPiece is the King, capturedPiece is the Rook
	public final ChessPiece movedPiece;
	public final int fromX;
	public final int fromY;
	public final int toX;
	public final int toY;
	public final ChessPiece captured;

	public Class<? extends ChessPiece> promote = null;

	public Move(ChessPiece movedPiece, int fromX, int fromY, int toX, int toY, ChessPiece captured) {
		this.movedPiece = movedPiece;
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
		this.captured = captured;
	}

	public boolean isCastleMove() {
		return movedPiece instanceof King && Math.abs(fromX - toX) > 1;
	}

	public boolean isEnPassant() {
		return movedPiece instanceof Pawn && fromX != toX && captured.getPosition().getY() == fromY;
	}

	@Override
	public String toString() {
		char x1 = (char) ('a' + fromX);
		char x2 = (char) ('a' + toX);
		int y1 = 8 - fromY;
		int y2 = 8 - toY;
		return movedPiece.getClass().getSimpleName() + " moved " + x1 + y1 + " -> " + x2 + y2;
	}
}