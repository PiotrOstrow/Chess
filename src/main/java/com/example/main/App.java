package com.example.main;

import com.example.game.Color;
import com.example.game.Game;
import com.example.game.SaveLoad;
import com.example.ui.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class App extends Application {

	private final StackPane gameRoot = new StackPane();
	private final GameBoard gameBoard = new GameBoard(gameRoot);
	private final CapturedPiecesBar topBar = new CapturedPiecesBar(Color.WHITE);
	private final CapturedPiecesBar bottomBar = new CapturedPiecesBar(Color.BLACK);
	private final MainMenu mainMenu = new MainMenu();
	private final ResultDialog resultDialog = new ResultDialog();
	private final BorderPane borderPane = new BorderPane(); // game container
	private final SettingsDialog settingsDialog = new SettingsDialog();

	private Game currentGame;

	@Override
	public void start(Stage primaryStage) {
		Image iconImage = new Image("Chess_Artwork/Chess_Pieces/Stone/KnightW.png");
		ImageView iconImageView = new ImageView(iconImage);
		iconImageView.setFitWidth(24);
		iconImageView.setFitHeight(24);

		StackPane decoratorRoot = new StackPane(mainMenu);
		JFXDecorator decorator = new JFXDecorator(primaryStage, decoratorRoot, false, true, true);
		decorator.setCustomMaximize(true);
		decorator.setGraphic(iconImageView);

		double size = Math.min(750, Screen.getPrimary().getVisualBounds().getHeight() - 50);
		final Scene scene = new Scene(decorator, size, size);

		JFXButton backButton = new JFXButton();
		FontIcon icon2 = new FontIcon("mdi2a-arrow-left-bold");
		icon2.setIconSize(24);
		icon2.setIconColor(javafx.scene.paint.Color.rgb(255, 255, 255, 1));
		backButton.setGraphic(icon2);
		backButton.setAlignment(Pos.CENTER_LEFT);
		backButton.setOnAction(event -> decoratorRoot.getChildren().setAll(mainMenu));

		JFXButton settingsButton = new JFXButton();
		FontIcon icon = new FontIcon("mdi2c-cog");
		icon.setIconSize(24);
		icon.setIconColor(javafx.scene.paint.Color.rgb(255, 255, 255, 1));
		settingsButton.setGraphic(icon);
		settingsButton.setAlignment(Pos.CENTER_RIGHT);
		settingsButton.setOnAction(event -> settingsDialog.show());

		gameRoot.setStyle("-fx-background-color: #141414");

		StackPane settingsButtonContainer = new StackPane(settingsButton);
		settingsButtonContainer.setAlignment(Pos.CENTER_RIGHT); // can not align button directly for whatever reason

		HBox topContainer = new HBox(backButton, topBar, settingsButton);
		topBar.prefWidthProperty().bind(primaryStage.widthProperty());
		borderPane.setCenter(gameBoard);
		borderPane.setTop(topContainer);
		borderPane.setBottom(bottomBar);
		gameRoot.getChildren().add(borderPane);

		settingsDialog.setDialogContainer(gameRoot);
		settingsDialog.onThemeChanged((observable, oldValue, newValue) -> setTheme(newValue));
		settingsDialog.onLegendOption((observable, oldValue, newValue) -> gameBoard.setShowLegend(newValue));

		resultDialog.setDialogContainer(gameRoot);
		resultDialog.setOverlayClose(false);

		mainMenu.setOnNewGame(event -> {
			decoratorRoot.getChildren().setAll(gameRoot);
			Game game = new Game();
			game.setUpNormal();
			startGame(game);
		});

        mainMenu.setOnResumeGame(event -> {
            if(currentGame == null) {
                SaveLoad load = new SaveLoad();
                Game game = load.loadFromSave();
				decoratorRoot.getChildren().setAll(gameRoot);
                startGame(game);
			} else if (!currentGame.isInCheckMate(Color.WHITE) && !currentGame.isInCheckMate(Color.BLACK)) {
                decoratorRoot.getChildren().setAll(gameRoot);
                startGame(currentGame);
            }
        });

		resultDialog.getRestartButton().setOnAction(event -> {
			Game game = new Game();
			game.setUpNormal();
			startGame(game);
			borderPane.setEffect(null);
			resultDialog.close();
		});

		resultDialog.getMenuButton().setOnAction(event -> {
			decoratorRoot.getChildren().setAll(mainMenu);
			borderPane.setEffect(null);
			resultDialog.close();
		});

        primaryStage.setOnCloseRequest(event -> {
        	if(currentGame != null && !currentGame.isInCheckMate(Color.WHITE) && !currentGame.isInCheckMate(Color.BLACK)) {
				SaveLoad closing = new SaveLoad();
				try {
					closing.saveGame(currentGame);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
        });

		primaryStage.getIcons().add(iconImage);
		primaryStage.setTitle("Chess");
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(500);
		primaryStage.setMinHeight(500);
		primaryStage.show();
	}

	private void setTheme(Theme theme) {
		gameBoard.setTheme(theme);
		topBar.setTheme(theme);
		bottomBar.setTheme(theme);
	}

	private void startGame(final Game game){
		currentGame = game;
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
		borderPane.setEffect(blur);
		resultDialog.show();
		timeline.playFromStart();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
