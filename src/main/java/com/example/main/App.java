package com.example.main;

import com.example.game.Color;
import com.example.game.Game;
import com.example.game.pieces.ChessPiece;
import com.example.ui.CapturedPiecesBar;
import com.example.ui.GameBoard;
import com.example.ui.RightMenu;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {

	private GameBoard gameBoard;
	private RightMenu rightMenu;
	private CapturedPiecesBar topBar;
	private CapturedPiecesBar bottomBar;

	@Override
	public void start(Stage primaryStage) {
		gameBoard = new GameBoard();

		rightMenu = new RightMenu();
		//temporary
		rightMenu.testButton.setOnMouseClicked(event -> {
			gameBoard.getCurrentGame().reverseMove();
			gameBoard.setGame(gameBoard.getCurrentGame());
		});

		topBar = new CapturedPiecesBar(Color.WHITE);
		bottomBar = new CapturedPiecesBar(Color.BLACK);

		GridPane gridPane = new GridPane();
		gridPane.add(topBar, 0, 0);
		gridPane.add(gameBoard, 0, 1);
		gridPane.add(bottomBar, 0, 2);
		gridPane.add(rightMenu, 1, 0, 1, 3);
		gridPane.setAlignment(Pos.CENTER);

		BorderPane borderPane = new BorderPane(gridPane);
		Scene scene = new Scene(borderPane, 600, 600);
		primaryStage.setScene(scene);
		primaryStage.show();

		Game game = new Game();
		testGame(game);
		startGame(game);
	}

	private void testGame(Game game) {
		ChessPiece blackPawn = game.getPiece(4, 1);
		ChessPiece blackKing = game.getPiece(4, 0);
		ChessPiece whitePawn = game.getPiece(4, 6);
		ChessPiece whiteKing = game.getPiece(4, 7);

		game.move(blackPawn, 4, 3);
		game.move(whitePawn, 4, 4);

		game.move(blackKing, 4, 1);
		for(int i = 0; i < 4; i++)
			game.move(blackKing, 3 - i, 2 + i);
		game.move(blackKing, 0, 6);
		for(int i = 1; i < 8; i++)
			game.move(blackKing, i, 6);

		for(int i = 0; i < 4; i++)
			game.move(whiteKing, 4 + i, 6 - i);
		game.move(whiteKing, 7, 2);
		game.move(whiteKing, 7, 1);

		for(int i = 6; i >= 0; i--)
			game.move(whiteKing, i, 1);
	}

	private void startGame(Game game){
		gameBoard.setGame(game);
		rightMenu.setGame(game);
		topBar.setGame(game);
		bottomBar.setGame(game);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
