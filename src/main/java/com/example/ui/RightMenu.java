package com.example.ui;

import com.example.game.Game;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class RightMenu extends GridPane {

	public final Button testButton;

	private Game currentGame;

	public RightMenu() {
		testButton = new Button("Test");
		getChildren().add(testButton);

		setAlignment(Pos.CENTER);
		setMaxWidth(200);
	}

	public void setGame(Game game) {
		this.currentGame = game;
	}
}
