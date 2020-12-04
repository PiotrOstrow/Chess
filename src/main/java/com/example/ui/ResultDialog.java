package com.example.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;

public class ResultDialog extends JFXDialog {

    Label gameWonLabel, gameLostLabel;
    JFXButton restartButton, menuButton, exitButton;

    public ResultDialog() {
        JFXDialogLayout content = new JFXDialogLayout();

        gameLostLabel = new Label("Game Lost");

        gameWonLabel = new Label("Game Won");
        gameWonLabel.setStyle("-fx-text-fill: black");
        URL url1 = getClass().getResource("/norwester/norwester.otf");
        Font font = Font.loadFont(url1.toString(), 48);
        gameWonLabel.setFont(font);

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

        restartButton.setButtonType(JFXButton.ButtonType.RAISED);
        menuButton.setButtonType(JFXButton.ButtonType.RAISED);
        exitButton.setButtonType(JFXButton.ButtonType.RAISED);

        restartButton.setFocusTraversable(false);
        menuButton.setFocusTraversable(false);
        exitButton.setFocusTraversable(false);

        restartButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                restartButton.setCursor(Cursor.HAND);
            }
        });

        menuButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                menuButton.setCursor(Cursor.HAND);
            }
        });

        exitButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                exitButton.setCursor(Cursor.HAND);
            }
        });

        VBox vBox = new VBox(gameWonLabel, restartButton, menuButton, exitButton);

        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        getChildren().add(vBox);
        content.setBody(vBox);
        setContent(content);

    }
}
