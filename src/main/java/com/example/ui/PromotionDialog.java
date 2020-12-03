package com.example.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PromotionDialog extends JFXDialog {

    JFXButton queenButton, knightButton, rookButton, bishopButton;

    public PromotionDialog() {

        JFXDialogLayout content = new JFXDialogLayout();

        Image queen = new Image("/Chess_ArtWork/Chess Symbols/Wood/QueenW.png");
        queenButton = new JFXButton();
        queenButton.setGraphic(new ImageView(queen));

        Image knight = new Image("/Chess_ArtWork/Chess Symbols/Wood/KnightW.png");
        knightButton = new JFXButton();
        knightButton.setGraphic(new ImageView(knight));

        Image rook = new Image("/Chess_ArtWork/Chess Symbols/Wood/RookW.png");
        rookButton = new JFXButton();
        rookButton.setGraphic(new ImageView(rook));

        Image bishop = new Image("/Chess_ArtWork/Chess Symbols/Wood/BishopW.png");
        bishopButton = new JFXButton();
        bishopButton.setGraphic(new ImageView(bishop));

        HBox hBox = new HBox(queenButton, knightButton, rookButton, bishopButton);
        hBox.setSpacing(10);

        getChildren().add(hBox);
        content.setBody(hBox);
        setContent(content);
    }
}
