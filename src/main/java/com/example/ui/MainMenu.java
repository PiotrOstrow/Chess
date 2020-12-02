package com.example.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class MainMenu extends StackPane {

	private final Button newGameButton, resumeGameButton, exitButton;

	private final ImageView backgroundImageView;
	private final double backgroundImageAspectRatio;

	public MainMenu() {
		GridPane gridPane = new GridPane();
		Label chessLabel = new Label("CHESS GAME");
		chessLabel.setStyle("-fx-font-size: 30px; -fx-font-style: bold; -fx-text-fill: white");
		newGameButton = new Button("New game");
		resumeGameButton = new Button("Resume game");
		exitButton = new Button("Exit");

		Label createdByLabel = new Label("Created by 8x8");
		createdByLabel.setStyle("-fx-font-size: 12px; -fx-font-style: bold; -fx-text-fill: gray");

		gridPane.add(chessLabel, 0, 0);
		gridPane.add(newGameButton, 0, 1);
		gridPane.add(resumeGameButton, 0, 2);
		gridPane.add(exitButton, 0, 3);
		gridPane.add(createdByLabel, 0, 4);

		gridPane.setAlignment(Pos.CENTER);

		Image backgroundImage = new Image("hassan-pasha-7SjEuEF06Zw-unsplash.jpg");
		backgroundImageView = new ImageView(backgroundImage);
		backgroundImageView.setEffect(new GaussianBlur(7));
		backgroundImageAspectRatio = backgroundImage.getWidth() / backgroundImage.getHeight();

		getChildren().add(backgroundImageView);
		getChildren().add(gridPane);

		setStyle("-fx-background-color: black");
	}

	@Override
	public void resize(double width, double height) {
		super.resize(width, height);

		backgroundImageView.setFitWidth(height * backgroundImageAspectRatio);
		backgroundImageView.setFitHeight(height);
	}

	public void setOnNewGame(EventHandler<ActionEvent> eventHandler) {
		newGameButton.setOnAction(eventHandler);
	}
}
