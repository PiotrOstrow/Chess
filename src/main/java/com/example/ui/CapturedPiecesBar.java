package com.example.ui;

import com.example.game.Color;
import com.example.game.Game;
import com.example.game.pieces.*;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

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
		getChildren().clear();
		game.getCapturedPieces().addListener((ListChangeListener<? super ChessPiece>) c -> {
			while(c.next()) {
				if (c.wasAdded()) {
					for (ChessPiece piece : c.getAddedSubList())
						if (piece.getColor() == this.color)
							addPiece(piece);
				} else if (!c.wasPermutated()) {
					throw new RuntimeException("Capture pieces list was modified incorrectly");
				}
			}
		});
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
			return "Chess_Artwork/Chess_Symbols/Wood/Pawn" + color + ".png";
		if(piece instanceof Bishop)
			return "Chess_Artwork/Chess_Symbols/Wood/Bishop" + color + ".png";
		if(piece instanceof King)
			return "Chess_Artwork/Chess_Symbols/Wood/King" + color + ".png";
		if(piece instanceof Knight)
			return "Chess_Artwork/Chess_Symbols/Wood/Knight" + color + ".png";
		if(piece instanceof Queen)
			return "Chess_Artwork/Chess_Symbols/Wood/Queen" + color + ".png";
		if(piece instanceof Rook)
			return "Chess_Artwork/Chess_Symbols/Wood/Rook" + color + ".png";
		return "";
	}
}
