package com.example.main;

import com.example.game.Color;
import com.example.game.Game;
import com.example.ui.CapturedPiecesBar;
import com.example.ui.GameBoard;
import com.example.ui.MainMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

	private GameBoard gameBoard;
	private CapturedPiecesBar topBar;
	private CapturedPiecesBar bottomBar;
	private MainMenu mainMenu;

	@Override
	public void start(Stage primaryStage) {

		{ // Main menu

		}

		{ // Game
			gameBoard = new GameBoard();

			topBar = new CapturedPiecesBar(Color.BLACK);
			bottomBar = new CapturedPiecesBar(Color.WHITE);

			BorderPane borderPane = new BorderPane();
			borderPane.setCenter(gameBoard);
			borderPane.setTop(topBar);
			borderPane.setBottom(bottomBar);

			Scene scene = new Scene(borderPane, 600, 600);
			primaryStage.setScene(scene);
			primaryStage.show();

			startGame(new Game());
		}
	}

	private void startGame(Game game){
		gameBoard.setGame(game);

		topBar.set(game);
		bottomBar.set(game);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
