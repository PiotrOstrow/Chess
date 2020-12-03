package com.example.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PromotionDialog extends JFXDialog {

    public PromotionDialog() {

        JFXDialogLayout content = new JFXDialogLayout();

        Image queen = new Image("/Chess_ArtWork/Chess Symbols/Wood/QueenW.png");
        JFXButton jfxButton1 = new JFXButton();
        jfxButton1.setGraphic(new ImageView(queen));

        Image knight = new Image("/Chess_ArtWork/Chess Symbols/Wood/KnightW.png");
        JFXButton jfxButton2 = new JFXButton();
        jfxButton2.setGraphic(new ImageView(knight));

        Image rook = new Image("/Chess_ArtWork/Chess Symbols/Wood/RookW.png");
        JFXButton jfxButton3 = new JFXButton();
        jfxButton3.setGraphic(new ImageView(rook));

        Image bishop = new Image("/Chess_ArtWork/Chess Symbols/Wood/BishopW.png");
        JFXButton jfxButton4 = new JFXButton();
        jfxButton4.setGraphic(new ImageView(bishop));

        HBox hBox = new HBox(jfxButton1, jfxButton2, jfxButton3, jfxButton4);
        hBox.setSpacing(10);

        getChildren().add(hBox);
        content.setBody(hBox);
        setContent(content);
    }
}
