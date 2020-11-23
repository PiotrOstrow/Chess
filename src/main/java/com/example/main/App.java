package com.example.main;

import com.example.ui.GameBoard;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void start(Stage primaryStage) {
		GameBoard gameBoard = new GameBoard();
		gameBoard.setAlignment(Pos.CENTER);

		Scene scene = new Scene(gameBoard, 550, 550);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
