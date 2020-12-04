package com.example.main;

import com.example.game.Color;
import com.example.game.Game;
import com.example.ui.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

	private GameBoard gameBoard;
	private CapturedPiecesBar topBar;
	private CapturedPiecesBar bottomBar;
	private MainMenu mainMenu;
	private Scene mainScene, gameScene;
	private ResultDialog resultDialog;

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
		topBar = new CapturedPiecesBar(Color.WHITE);
		bottomBar = new CapturedPiecesBar(Color.BLACK);
		resultDialog = new ResultDialog();
		resultDialog.setDialogContainer(gameBoard);
		resultDialog.setOverlayClose(false);

		resultDialog.getRestartButton().setOnAction(event -> {
			Game game = new Game();
			game.setUpNormal();
			startGame(game);
			gameBoard.getChildren().get(0).setEffect(null);
			resultDialog.close();
		});

		resultDialog.getMenuButton().setOnAction(event -> {
			primaryStage.setScene(mainScene);
			gameBoard.getChildren().get(0).setEffect(null);
			resultDialog.close();
		});

		resultDialog.getExitButton().setOnAction(event -> {
			Platform.exit();
		});

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(gameBoard);
		borderPane.setTop(topBar);
		borderPane.setBottom(bottomBar);

		gameScene = new Scene(borderPane, 750, 750);

		primaryStage.getIcons().add(new Image("Chess_Artwork/Chess_Pieces/Wood/KnightW.png"));
		primaryStage.setTitle("Chess");
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	private void startGame(final Game game){
		gameBoard.setGame(game, Color.WHITE);

		topBar.set(game);
		bottomBar.set(game);

		// temporary
		game.addGameCallback(() -> {
			gameBoard.onMoved();

			if(game.isInCheckMate(Color.WHITE)) {
				gameOver(gameBoard.getControlledColor() != Color.WHITE);
			} else if (game.isInCheckMate(Color.BLACK)) {
				gameOver(gameBoard.getControlledColor() != Color.BLACK);
			}
		});
	}

	private void gameOver(boolean win) {
		if(win) {
			resultDialog.getGameResultLabel().setText("Game Won");
		} else {
			resultDialog.getGameResultLabel().setText("Game Lost");
		}
		GaussianBlur blur = new GaussianBlur(0);
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5000), new KeyValue(blur.radiusProperty(), 7)));
		gameBoard.getChildren().get(0).setEffect(blur);
		resultDialog.show();
		timeline.playFromStart();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
