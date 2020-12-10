package com.example.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;

public class ResultDialog extends JFXDialog {

    private Label gameResultLabel;
    JFXButton restartButton, menuButton, exitButton;

    public ResultDialog() {
        JFXDialogLayout content = new JFXDialogLayout();

        gameResultLabel = new Label("");
        gameResultLabel.setStyle("-fx-text-fill: black");
        URL url1 = getClass().getResource("/norwester/norwester.otf");
        Font font = Font.loadFont(url1.toString(), 48);
        gameResultLabel.setFont(font);

        restartButton = new JFXButton("Restart");
        menuButton = new JFXButton("Menu");
        exitButton = new JFXButton("Exit");

        restartButton.setStyle("-fx-background-color: cornflowerblue; -fx-text-fill: white");
        menuButton.setStyle("-fx-background-color: cornflowerblue; -fx-text-fill: white");
        exitButton.setStyle("-fx-background-color: cornflowerblue; -fx-text-fill: white");

        Font font2 = Font.loadFont(url1.toString(), 14);
        restartButton.setFont(font2);
        menuButton.setFont(font2);
        exitButton.setFont(font2);

        restartButton.setPrefSize(170, 30);
        menuButton.setPrefSize(170, 30);
        exitButton.setPrefSize(170,30);

        restartButton.setButtonType(JFXButton.ButtonType.RAISED);
        menuButton.setButtonType(JFXButton.ButtonType.RAISED);
        exitButton.setButtonType(JFXButton.ButtonType.RAISED);

        restartButton.setFocusTraversable(false);
        menuButton.setFocusTraversable(false);
        exitButton.setFocusTraversable(false);

        restartButton.setOnMouseEntered(mouseEvent -> {
            restartButton.setCursor(Cursor.HAND);
        });

        menuButton.setOnMouseEntered(mouseEvent -> {
            menuButton.setCursor(Cursor.HAND);
        });

        exitButton.setOnMouseEntered(mouseEvent -> {
            exitButton.setCursor(Cursor.HAND);
        });

        exitButton.setOnAction(event -> Platform.exit());

        VBox vBox = new VBox(gameResultLabel, restartButton, menuButton, exitButton);

        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        getChildren().add(vBox);
        content.setBody(vBox);
        setContent(content);

    }

    public Label getGameResultLabel() {
        return gameResultLabel;
    }

    public JFXButton getRestartButton() {
        return restartButton;
    }

    public JFXButton getMenuButton() {
        return menuButton;
    }

}
