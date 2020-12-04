package com.example.ui;

import com.example.game.Color;
import com.example.game.Game;
import com.example.game.pieces.*;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.List;

public class CapturedPiecesBar extends HBox {

	private Color color;

	public CapturedPiecesBar(Color color) {
		this.color = color;

		setMinHeight(40);
		setSpacing(4);
		setAlignment(Pos.CENTER);
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void set(Game game) {
		set(game.getCapturedPieces());
		game.getCapturedPieces().addListener((ListChangeListener<? super ChessPiece>) c -> set(game.getCapturedPieces()));
	}

	private void set(List<ChessPiece> pieces) {
		getChildren().clear();
		for(ChessPiece captured : pieces)
			if(color == captured.getColor())
				addPiece(captured);
	}

	private void addPiece(ChessPiece piece) {
		Image image = new Image(getImagePath(piece));
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(30);
		imageView.setFitHeight(30);
		getChildren().add(imageView);
	}

	private String getImagePath(ChessPiece piece) {
		char color = piece.getColor() == Color.BLACK ? 'B' : 'W';
		if(piece instanceof Pawn)
			return "Chess_Artwork/Chess Symbols/Wood/Pawn" + color + ".png";
		if(piece instanceof Bishop)
			return "Chess_Artwork/Chess Symbols/Wood/Bishop" + color + ".png";
		if(piece instanceof King)
			return "Chess_Artwork/Chess Symbols/Wood/King" + color + ".png";
		if(piece instanceof Knight)
			return "Chess_Artwork/Chess Symbols/Wood/Knight" + color + ".png";
		if(piece instanceof Queen)
			return "Chess_Artwork/Chess Symbols/Wood/Queen" + color + ".png";
		if(piece instanceof Rook)
			return "Chess_Artwork/Chess Symbols/Wood/Rook" + color + ".png";
		return "";
	}
}
