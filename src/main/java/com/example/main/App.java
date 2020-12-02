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
	private Scene mainScene, gameScene;

	@Override
	public void start(Stage primaryStage) {

		{ //Main menu
			mainMenu = new MainMenu();
			mainScene = new Scene(mainMenu, 700, 600);
			mainMenu.setOnNewGame(event -> {
				primaryStage.setScene(gameScene);
				startGame(new Game());
			});
		}
		{ // Game
			gameBoard = new GameBoard();

			topBar = new CapturedPiecesBar(Color.BLACK);
			bottomBar = new CapturedPiecesBar(Color.WHITE);

			BorderPane borderPane = new BorderPane();
			borderPane.setCenter(gameBoard);
			borderPane.setTop(topBar);
			borderPane.setBottom(bottomBar);

			gameScene = new Scene(borderPane, 700, 600);
		}
		primaryStage.setTitle("Chess");
		primaryStage.setScene(mainScene);
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
