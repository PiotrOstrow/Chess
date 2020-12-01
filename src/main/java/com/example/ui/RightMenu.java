package com.example.ui;

import com.example.game.Game;
import com.example.game.Move;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;




public class RightMenu extends GridPane {

	private final ListView<Move> list = new ListView<>();

	private Game currentGame;


	public RightMenu() {
		getChildren().add(list);
		list.setPrefWidth(150);
		list.setPrefHeight(70);
		setAlignment(Pos.CENTER);
		setMaxWidth(300);


	}
		public void setGame(Game game) {
			this.currentGame = game;
			list.setItems(FXCollections.observableArrayList(game.getMoveLogStack()));

		}
	}


