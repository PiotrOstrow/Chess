package com.example.ui;

import com.example.game.Color;
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

import java.net.URL;

public class MainMenu extends StackPane {

	private final Button newGameButton, resumeGameButton, exitButton;

	private final ImageView backgroundImageView;
	private final double backgroundImageAspectRatio;

	public MainMenu() {

		Label chessLabel = new Label("CHESS GAME");
		chessLabel.setStyle("-fx-text-fill: white;-fx-font-weight: bold");
		URL url1 = getClass().getResource("/norwester/norwester.otf");
		Font font = Font.loadFont(url1.toString(), 48);
		chessLabel.setFont(font);
		newGameButton = new Button("New game");
		resumeGameButton = new Button("Resume game");
		exitButton = new Button("Exit");
		Label createdByLabel = new Label("Created by 8x8");
		createdByLabel.setStyle("-fx-text-fill: gray; -fx-font-size: 10");

		VBox vBox = new VBox(chessLabel, newGameButton, resumeGameButton, exitButton, createdByLabel);

		chessLabel.setPrefHeight(100);
		chessLabel.setAlignment(Pos.TOP_CENTER);

		newGameButton.setPrefSize(170, 40);
		resumeGameButton.setPrefSize(170, 40);
		exitButton.setPrefSize(170,40);
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
