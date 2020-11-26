package com.example.ui;

import com.example.game.Color;
import com.example.game.Game;
import com.example.game.pieces.ChessPiece;
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

	public void setGame(Game game) {
		getChildren().clear();

		for(ChessPiece capturedPiece : game.getCapturedPieces())
			if(capturedPiece.getColor() == this.color)
				addPiece(capturedPiece);

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
		Image image = new Image(piece.getImagePath());
		ImageView imageView = new ImageView(image);
		getChildren().add(imageView);
	}
}
