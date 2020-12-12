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

	private Game game;

	private Color color;
	private Theme theme = Theme.BLACK_STONE;

	public CapturedPiecesBar(Color color) {
		this.color = color;

		setMinHeight(40);
		setSpacing(4);
		setAlignment(Pos.CENTER);
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
		set(game.getCapturedPieces());
	}

	public void set(Game game) {
		this.game = game;
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
			return "Chess_Artwork/Chess_Symbols/" + theme.getSymbolsFolder() + "/Pawn" + color + ".png";
		if(piece instanceof Bishop)
			return "Chess_Artwork/Chess_Symbols/" + theme.getSymbolsFolder() + "/Bishop" + color + ".png";
		if(piece instanceof King)
			return "Chess_Artwork/Chess_Symbols/" + theme.getSymbolsFolder() + "/King" + color + ".png";
		if(piece instanceof Knight)
			return "Chess_Artwork/Chess_Symbols/" + theme.getSymbolsFolder() + "/Knight" + color + ".png";
		if(piece instanceof Queen)
			return "Chess_Artwork/Chess_Symbols/" + theme.getSymbolsFolder() + "/Queen" + color + ".png";
		if(piece instanceof Rook)
			return "Chess_Artwork/Chess_Symbols/" + theme.getSymbolsFolder() + "/Rook" + color + ".png";
		return "";
	}
}
