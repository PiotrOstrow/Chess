package com.example.ui;

import com.example.game.Color;
import com.example.game.Game;
import com.example.game.pieces.ChessPiece;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Iterator;

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
				} else if(c.wasRemoved()) {
					for(ChessPiece piece : c.getRemoved())
						if(piece.getColor() == this.color)
							removePiece(piece);
				} else if (!c.wasPermutated()) {
					throw new RuntimeException("Capture pieces list change not handled");
				}
			}
		});
	}

	private void removePiece(ChessPiece piece) {
		Iterator<Node> iterator =  getChildren().iterator();

		while(iterator.hasNext()) {
			Node node = iterator.next();
			if(node instanceof ImageView) {
				ImageView imageView = (ImageView) node;
				String imagePath = (String) imageView.getUserData();
				if(imagePath.equals(piece.getImagePath())){
					iterator.remove();
					break;
				}
			}
		}
	}

	private void addPiece(ChessPiece piece) {
		Image image = new Image(piece.getImagePath());
		ImageView imageView = new ImageView(image);
		imageView.setUserData(piece.getImagePath());
		getChildren().add(imageView);
	}
}
