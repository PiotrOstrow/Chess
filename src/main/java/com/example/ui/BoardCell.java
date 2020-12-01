package com.example.ui;

import com.example.game.Color;
import com.example.game.pieces.*;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class BoardCell extends StackPane {

	public static final int GRID_CELL_SIZE = 80;

	public enum Highlight {
		NONE, SELECTED, POSSIBLE_MOVE
	}

	private final int x;
	private final int y;
	private final Color color;

	private final ImageView chessImageView;
	private final ImageView cellImageView;

	public BoardCell(int x, int y) {
		this.x = x;
		this.y = y;
		this.color = (x + y) % 2 == 0 ? Color.WHITE : Color.BLACK;

		int y1 = 8 - y;
		char x1 = (char) ('a' + x);
		if(x1 == 'd' && y1 == 4) // missing graphic d4??
			y1 = 2;

		cellImageView = new ImageView(new Image("Chess_Artwork/Chess Board/Wood/" + x1 + y1 + ".png"));
		getChildren().add(cellImageView);

		chessImageView = new ImageView();
		getChildren().add(chessImageView);

		cellImageView.fitWidthProperty().bind(widthProperty());
		cellImageView.fitHeightProperty().bind(heightProperty());

		chessImageView.fitWidthProperty().bind(widthProperty());
		chessImageView.fitHeightProperty().bind(heightProperty());

		setMinSize(10, 10);
		setPrefSize(GRID_CELL_SIZE, GRID_CELL_SIZE);
		setMaxSize(GRID_CELL_SIZE * 4, GRID_CELL_SIZE * 4);
	}

	public void setPiece(ChessPiece piece) {
		if(piece == null) {
			chessImageView.setImage(null);
		} else {
			Image image = new Image(getImagePath(piece));
			chessImageView.setImage(image);
		}
	}

	private String getImagePath(ChessPiece piece) {
		char color = piece.getColor() == Color.BLACK ? 'B' : 'W';
		if(piece instanceof Pawn)
			return "Chess_Artwork/Chess Pieces/Wood/Pawn" + color + ".png";
		if(piece instanceof Bishop)
			return "Chess_Artwork/Chess Pieces/Wood/Bishop" + color + ".png";
		if(piece instanceof King)
			return "Chess_Artwork/Chess Pieces/Wood/King" + color + ".png";
		if(piece instanceof Knight)
			return "Chess_Artwork/Chess Pieces/Wood/Knight" + color + ".png";
		if(piece instanceof Queen)
			return "Chess_Artwork/Chess Pieces/Wood/Queen" + color + ".png";
		if(piece instanceof Rook)
			return "Chess_Artwork/Chess Pieces/Wood/Rook" + color + ".png";
		return "";
	}

	public void setHighlighted(Highlight highlight) {

		switch(highlight) {
			case NONE:
				cellImageView.setEffect(null);
				break;
			case SELECTED:
			case POSSIBLE_MOVE:
				cellImageView.setEffect(new Glow(2));
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
