package com.example.ui;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
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
		Font font = FontLoader.loadFont("/norwester/norwester.otf", 48);
		Font font2 = FontLoader.loadFont("/norwester/norwester.otf", 14);

		Label chessLabel = new Label("CHESS GAME");
		chessLabel.setStyle("-fx-text-fill: white;-fx-font-weight: bold");
		chessLabel.setFont(font);
		newGameButton = new JFXButton("New game");
		resumeGameButton = new JFXButton("Resume game");
		exitButton = new JFXButton("Exit");
		Label createdByLabel = new Label("Created by 8x8");
		createdByLabel.setStyle("-fx-text-fill: gray; -fx-font-size: 10");

		VBox vBox = new VBox(chessLabel, newGameButton, resumeGameButton, exitButton, createdByLabel);

		chessLabel.setPrefHeight(100);
		chessLabel.setAlignment(Pos.TOP_CENTER);

		newGameButton.setPrefSize(200, 40);
		resumeGameButton.setPrefSize(200, 40);
		exitButton.setPrefSize(200,40);
		newGameButton.setStyle("-fx-background-color: ghostwhite; -fx-text-fill: black");
		resumeGameButton.setStyle("-fx-background-color: ghostwhite; -fx-text-fill: black");
		exitButton.setStyle("-fx-background-color: dimgray; -fx-text-fill: black");

		newGameButton.setFont(font2);
		resumeGameButton.setFont(font2);
		exitButton.setFont(font2);

		newGameButton.setFocusTraversable(false);
		resumeGameButton.setFocusTraversable(false);
		exitButton.setFocusTraversable(false);

		newGameButton.setOnMouseEntered(mouseEvent -> {
			newGameButton.setCursor(Cursor.HAND);
		});

		resumeGameButton.setOnMouseEntered(mouseEvent -> {
			resumeGameButton.setCursor(Cursor.HAND);
		});

		exitButton.setOnMouseEntered(mouseEvent -> {
			exitButton.setCursor(Cursor.HAND);
		});

		vBox.setSpacing(10);
		vBox.setAlignment(Pos.CENTER);

		Image backgroundImage = new Image("hassan-pasha-7SjEuEF06Zw-unsplash.jpg");
		backgroundImageView = new ImageView(backgroundImage);
		backgroundImageView.setEffect(new GaussianBlur(7));

		getChildren().add(backgroundImageView);
		getChildren().add(vBox);

		setStyle("-fx-background-color: black");

		exitButton.setOnAction((e) -> Platform.exit());

		// offset image to be centered
		double xOffset = 160;
		double yOffset = 350;
		backgroundImageView.setViewport(new Rectangle2D(xOffset, yOffset, backgroundImage.getWidth() - xOffset, backgroundImage.getHeight() - yOffset));
		backgroundImageAspectRatio = backgroundImageView.getViewport().getWidth() / backgroundImageView.getViewport().getHeight();

		setMinSize(10, 10);
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

	public void setOnResumeGame(EventHandler<ActionEvent> eventHandler) {
		resumeGameButton.setOnAction(eventHandler);
	}
}
