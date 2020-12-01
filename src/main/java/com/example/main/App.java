package com.example.main;

import com.example.game.Color;
import com.example.game.Game;
import com.example.ui.CapturedPiecesBar;
import com.example.ui.GameBoard;
import com.example.ui.MainMenu;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
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

		mainMenu = new MainMenu();
		gameBoard = new GameBoard();
		gameBoard.setEffect(new GaussianBlur(10));

		topBar = new CapturedPiecesBar(Color.BLACK);
		bottomBar = new CapturedPiecesBar(Color.WHITE);

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(new StackPane(gameBoard, mainMenu));
		borderPane.setTop(topBar);
		borderPane.setBottom(bottomBar);

		Scene scene = new Scene(borderPane, 600, 600);

		mainMenu.setOnNewGame(event -> {
			gameBoard.setEffect(null);
			borderPane.setCenter(gameBoard);
			startGame(new Game());
		});

		primaryStage.setTitle("Chess");
		primaryStage.setScene(scene);
		primaryStage.show();

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
