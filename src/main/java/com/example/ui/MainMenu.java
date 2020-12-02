package com.example.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.awt.*;

public class MainMenu extends GridPane {

	private Button newGameButton, resumeGameButton, exitButton;

	public MainMenu() {

		Label chessLabel = new Label("CHESS GAME");
		chessLabel.setStyle("-fx-font-size: 30px; -fx-font-style: bold; -fx-text-fill: white");
		newGameButton = new Button("New game");
		resumeGameButton = new Button("Resume game");
		exitButton = new Button("Exit");

		Label createdByLabel = new Label("Created by 8x8");
		createdByLabel.setStyle("-fx-font-size: 12px; -fx-font-style: bold; -fx-text-fill: gray");

		add(chessLabel, 0, 0);
		add(newGameButton, 0, 1);
		add(resumeGameButton, 0, 2);
		add(exitButton, 0, 3);
		add(createdByLabel, 0, 4);

		setAlignment(Pos.CENTER);
		setStyle("-fx-background-image: url('hassan-pasha-7SjEuEF06Zw-unsplash-small.jpg'); -fx-background-size: cover");

	}

	public void setOnNewGame(EventHandler<ActionEvent> eventHandler) {
		newGameButton.setOnAction(eventHandler);
	}
}
