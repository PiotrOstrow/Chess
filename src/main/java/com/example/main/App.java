package com.example.main;

import com.example.game.Game;
import com.example.ui.GameBoard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

	private GameBoard gameBoard;

	@Override
	public void start(Stage primaryStage) {
		gameBoard = new GameBoard();

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(gameBoard);

		Scene scene = new Scene(borderPane, 550, 550);
		primaryStage.setScene(scene);
		primaryStage.show();

		Game game = new Game();
		gameBoard.setGame(game);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
