package com.example.main;

import com.example.game.Color;
import com.example.game.Game;
import com.example.ui.CapturedPiecesBar;
import com.example.ui.GameBoard;
import com.example.ui.MainMenu;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

	private GameBoard gameBoard;
	private CapturedPiecesBar topBar;
	private CapturedPiecesBar bottomBar;
	private MainMenu mainMenu;
	private Scene mainScene, gameScene;

	@Override
	public void start(Stage primaryStage) {
		mainMenu = new MainMenu();
		mainScene = new Scene(mainMenu, 750, 750);
		mainMenu.setOnNewGame(event -> {
			primaryStage.setScene(gameScene);
			Game game = new Game();
			game.setUpNormal();
			startGame(game);
		});

		gameBoard = new GameBoard();
		topBar = new CapturedPiecesBar(Color.BLACK);
		bottomBar = new CapturedPiecesBar(Color.WHITE);

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(gameBoard);
		borderPane.setTop(topBar);
		borderPane.setBottom(bottomBar);

		gameScene = new Scene(borderPane, 750, 750);

		primaryStage.getIcons().add(new Image("Chess_Artwork/Chess Pieces/Wood/KnightW.png"));
		primaryStage.setTitle("Chess");
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	private void startGame(final Game game){
		gameBoard.setGame(game, Color.WHITE);

		topBar.set(game);
		bottomBar.set(game);

		// temporary
		game.addGameCallback(() -> gameBoard.setGame(game, Color.WHITE));
	}

	//private void choosePromotion() {

	//}

	public static void main(String[] args) {
		launch(args);
	}
}
