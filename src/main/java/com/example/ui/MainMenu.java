package com.example.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class MainMenu extends GridPane {

	private Button newGameButton;

	public MainMenu() {
		Label chessLabel = new Label("Chess");
		chessLabel.setStyle("-fx-font-size:24px; -fx-font-style: bold;");

		newGameButton = new Button("New game");

		add(chessLabel, 0, 0);
		add(newGameButton, 0, 1);

		setMaxSize(200, 100);
		setAlignment(Pos.CENTER);
		setStyle("-fx-border-width: 4px; -fx-border-color: black; -fx-border-radius:4px; -fx-background-color:white");
	}

	public void setOnNewGame(EventHandler<ActionEvent> eventHandler) {
		newGameButton.setOnAction(eventHandler);
	}
}
