package com.example.game;

import com.example.game.pieces.ChessPiece;
import com.example.game.pieces.King;
import com.example.game.pieces.Knight;
import com.example.game.pieces.Pawn;
import com.example.game.pieces.Rook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Game {

	private final ChessPiece[][] pieces = new ChessPiece[8][8];
	private final ObservableList<ChessPiece> capturedPieces = FXCollections.observableArrayList();

	public Game() {
		setUpNormal();
	}

	public ChessPiece getPiece(int x, int y) {
		if(x < 0 || y < 0 || x >= 8 || y >= 8)
			 return null;
		return pieces[x][y];
	}

	public boolean move(ChessPiece chessPiece, int x, int y) {
		// TODO: check if move results in a check
		// save the location before moving
		int previousX = chessPiece.getPosition().getX();
		int previousY = chessPiece.getPosition().getY();

		if(chessPiece.move(this, x, y)){
			// check if a piece was captured
			if(pieces[x][y] != null)
				capturedPieces.add(pieces[x][y]);

			pieces[x][y] = chessPiece;
			pieces[previousX][previousY] = null;
			return true;
		}
		return false;
	}

	private void addPiece(ChessPiece piece){
		pieces[piece.getPosition().getX()][piece.getPosition().getY()] = piece;
	}

	public void setUpNormal(){
		for (int i=0; i<=7; i++){
			addPiece(new Pawn(i, 6, Color.WHITE));
			addPiece(new Pawn(i, 1, Color.BLACK));
		}
		addPiece(new King(4, 7, Color.WHITE));
		addPiece(new King(4, 0, Color.BLACK));
		addPiece(new Knight(1, 7, Color.WHITE));
		addPiece(new Knight(1, 0, Color.BLACK));
		addPiece(new Knight(6, 7, Color.WHITE));
		addPiece(new Knight(6, 0, Color.BLACK));
		addPiece(new Rook(0, 7, Color.WHITE));
		addPiece(new Rook(0, 0, Color.BLACK));
		addPiece(new Rook(7, 7, Color.WHITE));
		addPiece(new Rook(7, 0, Color.BLACK));
		/*
		addPiece(new Bishop(2, 7, Color.WHITE));
		addPiece(new Bishop(2, 0, Color.BLACK));
		addPiece(new Bishop(5, 7, Color.WHITE));
		addPiece(new Bishop(5, 0, Color.BLACK));
		addPiece(new Queen(3, 7, Color.WHITE));
		addPiece(new Queen(3, 0, Color.BLACK));
		*/
	}

	public ObservableList<ChessPiece> getCapturedPieces() {
		return FXCollections.unmodifiableObservableList(capturedPieces);
	}
}
