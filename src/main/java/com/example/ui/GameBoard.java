package com.example.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class GameBoard extends GridPane {

	private static final int GRID_CELL_SIZE = 64;

	public GameBoard() {
		for(int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				ImageView imageView = new ImageView();
				imageView.setImage(new Image("Pixel_Art_Chess_DevilsWorkshop_V03\\chess\\white_king.png"));
				StackPane element = new StackPane(imageView);
				element.setMinSize(GRID_CELL_SIZE, GRID_CELL_SIZE);
				if((x + y) % 2 == 0)
					element.setStyle("-fx-background-color: black");
				else
					element.setStyle("-fx-background-color: white");
				add(element, x, y);
			}
		}

		setStyle("-fx-border-color: black; -fx-border-width: 4px;");
		setMaxSize(GRID_CELL_SIZE * 8, GRID_CELL_SIZE * 8);
		setPrefSize(GRID_CELL_SIZE * 8, GRID_CELL_SIZE * 8);
	}
}
