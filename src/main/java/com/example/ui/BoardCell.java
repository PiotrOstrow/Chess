package com.example.ui;

import com.example.game.Color;
import com.example.game.pieces.*;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class BoardCell extends StackPane {

	public static final int GRID_CELL_SIZE = 80;

	public enum Highlight {
		NONE, SELECTED, POSSIBLE_MOVE, CHECK
	}

	private final int x;
	private final int y;
	private final Color color;

	private final ImageView chessImageView;
	private final ImageView cellImageView;
	private final Rectangle rectangle;

	public BoardCell(int x, int y) {
		this.x = x;
		this.y = y;
		this.color = (x + y) % 2 == 0 ? Color.WHITE : Color.BLACK;

		int y1 = 8 - y;
		char x1 = (char) ('a' + x);
		if(x1 == 'd' && y1 == 4) // missing graphic d4??
			y1 = 2;

		cellImageView = new ImageView(new Image("Chess_Artwork/Chess_Board/Wood/" + x1 + y1 + ".png"));
		getChildren().add(cellImageView);

		rectangle = new Rectangle(cellImageView.getImage().getWidth(), cellImageView.getImage().getHeight());
		rectangle.setFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0));
		getChildren().add(rectangle);

		chessImageView = new ImageView();
		getChildren().add(chessImageView);

		setMinSize(GRID_CELL_SIZE, GRID_CELL_SIZE);
		setPrefSize(GRID_CELL_SIZE, GRID_CELL_SIZE);
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
			return "Chess_Artwork/Chess_Pieces/Wood/Pawn" + color + ".png";
		if(piece instanceof Bishop)
			return "Chess_Artwork/Chess_Pieces/Wood/Bishop" + color + ".png";
		if(piece instanceof King)
			return "Chess_Artwork/Chess_Pieces/Wood/King" + color + ".png";
		if(piece instanceof Knight)
			return "Chess_Artwork/Chess_Pieces/Wood/Knight" + color + ".png";
		if(piece instanceof Queen)
			return "Chess_Artwork/Chess_Pieces/Wood/Queen" + color + ".png";
		if(piece instanceof Rook)
			return "Chess_Artwork/Chess_Pieces/Wood/Rook" + color + ".png";
		return "";
	}

	public void setHighlighted(Highlight highlight) {
		switch(highlight) {
			case NONE:
				cellImageView.setEffect(null);
				rectangle.setFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0));
				break;
			case SELECTED:
			case POSSIBLE_MOVE:
				cellImageView.setEffect(new Glow(2));

				if(color == Color.WHITE)
					rectangle.setFill(javafx.scene.paint.Color.rgb(128 + 64, 128 + 64, 0, 0.55));
				//else
				//	rectangle.setFill(javafx.scene.paint.Color.rgb(255, 255, 255, 0.25));
				break;
			case CHECK:
				cellImageView.setEffect(new Glow(2));
				rectangle.setFill(javafx.scene.paint.Color.rgb(192, 0, 0, 0.5));
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
