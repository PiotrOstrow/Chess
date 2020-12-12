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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ResultDialog extends JFXDialog {

    private Label gameResultLabel;
    JFXButton restartButton, menuButton, exitButton;

    public ResultDialog() {
        JFXDialogLayout content = new JFXDialogLayout();

        Font font = null, font2 = null;
        URL url1 = getClass().getResource("/norwester/norwester.otf");
        try {
            String fontPath = new URI(url1.toString()).getPath();
            font = Font.loadFont(new FileInputStream(fontPath), 48);
            font2 = Font.loadFont(new FileInputStream(fontPath), 14);

            if(font == null || font2 == null)
                throw new NullPointerException();
        } catch (URISyntaxException | NullPointerException | FileNotFoundException e) {
            System.err.println("Could not load font");
            e.printStackTrace();
            Platform.exit();
        }

        gameResultLabel = new Label("result");
        gameResultLabel.setStyle("-fx-text-fill: white;-fx-font-weight: bold");
        gameResultLabel.setFont(font);

        restartButton = new JFXButton("Restart");
        menuButton = new JFXButton("Menu");
        exitButton = new JFXButton("Exit");

        restartButton.setStyle("-fx-background-color: dimgray; -fx-text-fill: black");
        menuButton.setStyle("-fx-background-color: cornflowerblue; -fx-text-fill: white");
        exitButton.setStyle("-fx-background-color: cornflowerblue; -fx-text-fill: white");

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
        getChildren().get(getChildren().size() - 1).setStyle("-fx-background-color: rgba(0, 0, 0, 0.5)");
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
