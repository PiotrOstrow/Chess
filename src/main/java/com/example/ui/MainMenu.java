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
import javafx.scene.text.Font;

public class MainMenu extends StackPane {

	private final Button newGameButton, resumeGameButton, exitButton;

	private final ImageView backgroundImageView;
	private final double backgroundImageAspectRatio;

	public MainMenu() {

		Label chessLabel = new Label("CHESS GAME");
		chessLabel.setStyle("-fx-font-size: 32px; -fx-text-fill: white; -fx-font-family: Arial; -fx-font-weight: bold");
		newGameButton = new Button("New game");
		resumeGameButton = new Button("Resume game");
		exitButton = new Button("Exit");
		Label createdByLabel = new Label("Created by 8x8");

		VBox vBox = new VBox(chessLabel, newGameButton, resumeGameButton, exitButton, createdByLabel);

		newGameButton.setMinSize(150, 20);
		resumeGameButton.setMinSize(150, 20);
		exitButton.setMinSize(150,20);
		exitButton.setStyle("-fx-background-color: dimgray; -fx-text-fill: black");

		vBox.setSpacing(10);
		vBox.setAlignment(Pos.CENTER);

		Image backgroundImage = new Image("hassan-pasha-7SjEuEF06Zw-unsplash.jpg");
		backgroundImageView = new ImageView(backgroundImage);
		backgroundImageView.setEffect(new GaussianBlur(7));
		backgroundImageAspectRatio = backgroundImage.getWidth() / backgroundImage.getHeight();

		getChildren().add(backgroundImageView);
		getChildren().add(vBox);

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
