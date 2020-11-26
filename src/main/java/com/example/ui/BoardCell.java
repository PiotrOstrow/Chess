package com.example.ui;

import com.example.game.Color;
import com.example.game.pieces.ChessPiece;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class BoardCell extends StackPane {

	public static final int GRID_CELL_SIZE = 64;

	public enum Highlight {
		NONE, SELECTED, POSSIBLE_MOVE
	}

	private final int x;
	private final int y;
	private final Color color;

	private Highlight highlight = Highlight.NONE;

	private final ImageView imageView;

	public BoardCell(int x, int y) {
		this.x = x;
		this.y = y;
		this.color = (x + y) % 2 == 0 ? Color.WHITE : Color.BLACK;

		if(color == Color.BLACK)
			setStyle("-fx-background-color: black");
		else
			setStyle("-fx-background-color: white");

		imageView = new ImageView();
		getChildren().add(imageView);

		setMinSize(GRID_CELL_SIZE, GRID_CELL_SIZE);
	}

	public void setPiece(ChessPiece piece) {
		if(piece == null) {
			imageView.setImage(null);
		} else {
			Image image = new Image(piece.getImagePath());
			imageView.setImage(image);
		}
	}

	public void setHighlighted(Highlight highlight) {
		switch(highlight) {
			case NONE:
				if(color == Color.BLACK)
					setStyle("-fx-background-color: black");
				else
					setStyle("-fx-background-color: white");
				break;
			case SELECTED:
				setStyle("-fx-background-color: yellow");
				break;
			case POSSIBLE_MOVE:
				if(color == Color.BLACK)
					setStyle("-fx-background-color: rgb(128, 128, 0)");
				else
					setStyle("-fx-background-color: rgb(255, 255, 191)");
				break;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
