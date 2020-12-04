package com.example.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PromotionDialog extends JFXDialog {

    JFXButton queenButton, knightButton, rookButton, bishopButton;

    public PromotionDialog() {

        JFXDialogLayout content = new JFXDialogLayout();

        Image queen = new Image("/Chess_ArtWork/Chess_Symbols/Wood/QueenW.png");
        queenButton = new JFXButton();
        queenButton.setGraphic(new ImageView(queen));
        queenButton.setFocusTraversable(false);
        queenButton.setButtonType(JFXButton.ButtonType.RAISED);
        queenButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                queenButton.setCursor(Cursor.HAND);
            }
        });

        Image knight = new Image("/Chess_ArtWork/Chess_Symbols/Wood/KnightW.png");
        knightButton = new JFXButton();
        knightButton.setGraphic(new ImageView(knight));
        knightButton.setFocusTraversable(false);
        knightButton.setButtonType(JFXButton.ButtonType.RAISED);
        knightButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                knightButton.setCursor(Cursor.HAND);
            }
        });

        Image rook = new Image("/Chess_ArtWork/Chess_Symbols/Wood/RookW.png");
        rookButton = new JFXButton();
        rookButton.setGraphic(new ImageView(rook));
        rookButton.setFocusTraversable(false);
        rookButton.setButtonType(JFXButton.ButtonType.RAISED);
        rookButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                rookButton.setCursor(Cursor.HAND);
            }
        });

        Image bishop = new Image("/Chess_ArtWork/Chess_Symbols/Wood/BishopW.png");
        bishopButton = new JFXButton();
        bishopButton.setGraphic(new ImageView(bishop));
        bishopButton.setFocusTraversable(false);
        bishopButton.setButtonType(JFXButton.ButtonType.RAISED);
        bishopButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                bishopButton.setCursor(Cursor.HAND);
            }
        });

        HBox hBox = new HBox(queenButton, knightButton, rookButton, bishopButton);
        hBox.setSpacing(10);

        getChildren().add(hBox);
        content.setBody(hBox);
        setContent(content);
    }
}
