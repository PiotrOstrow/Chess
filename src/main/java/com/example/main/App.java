package com.example.main;

import com.example.game.Color;
import com.example.game.Game;
import com.example.ui.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
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

public class App extends Application {

	private final StackPane gameRoot = new StackPane();
	private final GameBoard gameBoard = new GameBoard(gameRoot);
	private final CapturedPiecesBar topBar = new CapturedPiecesBar(Color.WHITE);
	private final CapturedPiecesBar bottomBar = new CapturedPiecesBar(Color.BLACK);
	private final MainMenu mainMenu = new MainMenu();
	private final ResultDialog resultDialog = new ResultDialog();
	private final BorderPane borderPane = new BorderPane(); // game container
	private final SettingsDialog settingsDialog = new SettingsDialog();

	@Override
	public void start(Stage primaryStage) {
		Image iconImage = new Image("Chess_Artwork/Chess_Pieces/Stone/KnightW.png");
		ImageView iconImageView = new ImageView(iconImage);
		iconImageView.setFitWidth(24);
		iconImageView.setFitHeight(24);

		final JFXDecorator decorator = new JFXDecorator(primaryStage, mainMenu, false, true, true);
		decorator.setCustomMaximize(true);
		decorator.setGraphic(iconImageView);

		double size = Math.min(750, Screen.getPrimary().getVisualBounds().getHeight() - 50);
		final Scene scene = new Scene(decorator, size, size);

		/*JFXButton backButton = new JFXButton("Back");
		backButton.setStyle("-fx-background-color: dimgray; -fx-text-fill: white");

		HBox hBox = new HBox();
		hBox.setPadding(new Insets(10));
		hBox.setAlignment(Pos.TOP_LEFT);
		hBox.getChildren().add(backButton);
		 */

		JFXButton settingsButton = new JFXButton();
		FontIcon icon = new FontIcon("mdi-settings");
		icon.setIconSize(24);
		icon.setIconColor(javafx.scene.paint.Color.rgb(255, 255, 255, 1));
		settingsButton.setGraphic(icon);
		settingsButton.setAlignment(Pos.CENTER_RIGHT);
		settingsButton.setOnAction(event -> settingsDialog.show());

		gameRoot.setStyle("-fx-background-color: black");

		StackPane settingsButtonContainer = new StackPane(settingsButton);
		settingsButtonContainer.setAlignment(Pos.CENTER_RIGHT); // can not align button directly for whatever reason

		borderPane.setCenter(gameBoard);
		borderPane.setTop(new StackPane(topBar, settingsButtonContainer));
		borderPane.setBottom(bottomBar);
		gameRoot.getChildren().add(borderPane);

		settingsDialog.setDialogContainer(gameRoot);

		resultDialog.setDialogContainer(gameRoot);
		resultDialog.setOverlayClose(false);

		mainMenu.setOnNewGame(event -> {
			decorator.setContent(gameRoot);
			Game game = new Game();
			game.setUpNormal();
			startGame(game);
		});

		resultDialog.getRestartButton().setOnAction(event -> {
			Game game = new Game();
			game.setUpNormal();
			startGame(game);
			borderPane.setEffect(null);
			resultDialog.close();
		});

		resultDialog.getMenuButton().setOnAction(event -> {
			decorator.setContent(mainMenu);
			borderPane.setEffect(null);
			resultDialog.close();
		});

		primaryStage.getIcons().add(iconImage);
		primaryStage.setTitle("Chess");
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(500);
		primaryStage.setMinHeight(500);
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
		borderPane.setEffect(blur);
		resultDialog.show();
		timeline.playFromStart();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
