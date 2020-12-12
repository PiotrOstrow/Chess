package com.example.ui;

import com.example.game.Color;
import com.example.game.pieces.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;

public class BoardCell extends StackPane {

	public static final int GRID_CELL_SIZE = 80;

	public enum Highlight {
		NONE, SELECTED, POSSIBLE_MOVE
	}

	public static final HashMap<Theme, Glow> glowHashMapWhite = new HashMap<>();
	public static final HashMap<Theme, Glow> glowHashMapBlack = new HashMap<>();
	public static final HashMap<Theme, javafx.scene.paint.Color> colorHashMapWhite = new HashMap<>();
	public static final HashMap<Theme, javafx.scene.paint.Color> colorHashMapBlack = new HashMap<>();

	static {
		for(Theme theme : Theme.values()) {
			glowHashMapWhite.put(theme, new Glow(2));
			glowHashMapBlack.put(theme, new Glow(2));

			colorHashMapWhite.put(theme, javafx.scene.paint.Color.rgb(0, 0, 0, 0));
			colorHashMapBlack.put(theme, javafx.scene.paint.Color.rgb(0, 0, 0, 0));
		}

		colorHashMapWhite.put(Theme.WOOD, javafx.scene.paint.Color.rgb(128 + 64, 128 + 64, 0, 0.55));
		colorHashMapWhite.put(Theme.BLACK_STONE, javafx.scene.paint.Color.rgb(128 + 64, 128 + 64, 0, 0.30));
		colorHashMapWhite.put(Theme.GREY_STONE, javafx.scene.paint.Color.rgb(128 + 64, 128 + 64, 0, 0.30));
		colorHashMapWhite.put(Theme.BLUE_STONE, javafx.scene.paint.Color.rgb(255, 255, 0, 0.15));

		colorHashMapBlack.put(Theme.BLACK_STONE, javafx.scene.paint.Color.rgb(255, 255, 255, 0.1));
	}

	private final int x;
	private final int y;
	private final Color color;

	private final ImageView chessImageView;
	private final ImageView cellImageView;
	private final Rectangle rectangle;

	private Highlight highlight = Highlight.NONE;
	private boolean checked;

	private Theme theme = Theme.BLACK_STONE;
	private ChessPiece chessPiece;

	public BoardCell(int x, int y) {
		this.x = x;
		this.y = y;
		this.color = (x + y) % 2 == 0 ? Color.WHITE : Color.BLACK;

		int y1 = 8 - y;
		char x1 = (char) ('a' + x);
		if(x1 == 'd' && y1 == 4) // missing graphic d4??
			y1 = 2;

		cellImageView = new ImageView(new Image("Chess_Artwork/Chess_Board/Stone_Black/" + x1 + y1 + ".png"));
		getChildren().add(cellImageView);

		rectangle = new Rectangle(cellImageView.getImage().getWidth(), cellImageView.getImage().getHeight());
		rectangle.setFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0));
		getChildren().add(rectangle);

		chessImageView = new ImageView();
		getChildren().add(chessImageView);

		setMinSize(GRID_CELL_SIZE, GRID_CELL_SIZE);
		setPrefSize(GRID_CELL_SIZE, GRID_CELL_SIZE);
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
		int y1 = 8 - y;
		char x1 = (char) ('a' + x);
		if(x1 == 'd' && y1 == 4) // missing graphic d4??
			y1 = 2;

		cellImageView.setImage(new Image("Chess_Artwork/Chess_Board/" + theme.getBoardFolder() + "/" + x1 + y1 + ".png"));

		if(chessPiece != null)
			setPiece(chessPiece); // adjusts piece image
	}

	public void setPiece(ChessPiece piece) {
		this.chessPiece = piece;

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
			return "Chess_Artwork/Chess_Pieces/" + theme.getSymbolsFolder() + "/Pawn" + color + ".png";
		if(piece instanceof Bishop)
			return "Chess_Artwork/Chess_Pieces/" + theme.getSymbolsFolder() + "/Bishop" + color + ".png";
		if(piece instanceof King)
			return "Chess_Artwork/Chess_Pieces/" + theme.getSymbolsFolder() + "/King" + color + ".png";
		if(piece instanceof Knight)
			return "Chess_Artwork/Chess_Pieces/" + theme.getSymbolsFolder() + "/Knight" + color + ".png";
		if(piece instanceof Queen)
			return "Chess_Artwork/Chess_Pieces/" + theme.getSymbolsFolder() + "/Queen" + color + ".png";
		if(piece instanceof Rook)
			return "Chess_Artwork/Chess_Pieces/" + theme.getSymbolsFolder() + "/Rook" + color + ".png";
		return "";
	}

	public void setHighlighted(Highlight highlight) {
		this.highlight = highlight;
		switch(highlight) {
			case NONE:
				if(checked) {
					cellImageView.setEffect(new Glow(2));
					rectangle.setFill(javafx.scene.paint.Color.rgb(255, 0, 0, 0.75));
				} else {
					cellImageView.setEffect(null);
					rectangle.setFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0));
				}
				break;
			case SELECTED:
			case POSSIBLE_MOVE:
				cellImageView.setEffect(new Glow(2));

				if(color == Color.WHITE) {
					switch (theme) {
						case WOOD:
							rectangle.setFill(javafx.scene.paint.Color.rgb(128 + 64, 128 + 64, 0, 0.55));
							break;
						case BLACK_STONE:
						case GREY_STONE:
							rectangle.setFill(javafx.scene.paint.Color.rgb(128 + 64, 128 + 64, 0, 0.30));
							break;
						case BLUE_STONE:
							rectangle.setFill(javafx.scene.paint.Color.rgb(255, 255, 0, 0.15));
							break;
					}
					rectangle.setFill(colorHashMapWhite.get(theme));
					cellImageView.setEffect(glowHashMapWhite.get(theme));
				} else {
					if (theme == Theme.BLACK_STONE ) {
						rectangle.setFill(javafx.scene.paint.Color.rgb(255, 255, 255, 0.1));
					} else {
						rectangle.setFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0));
					}
					rectangle.setFill(colorHashMapBlack.get(theme));
					cellImageView.setEffect(glowHashMapBlack.get(theme));
				}
				break;
		}
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
		setHighlighted(highlight);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
