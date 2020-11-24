package com.example.ui;

import com.example.game.Game;
import com.example.game.pieces.ChessPiece;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import static com.example.ui.BoardCell.GRID_CELL_SIZE;

public class GameBoard extends GridPane {

	private final BoardCell[][] cells = new BoardCell[8][8];

	private BoardCell highlighted;

	private Game currentGame;

	public GameBoard() {
		for(int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				cells[x][y] = new BoardCell(x, y);
				add(cells[x][y], x, y);

				cells[x][y].setOnMouseClicked(this::handleClick);
			}
		}

		setStyle("-fx-border-color: black; -fx-border-width: 4px;");
		setMaxSize(GRID_CELL_SIZE * 8, GRID_CELL_SIZE * 8);
		setPrefSize(GRID_CELL_SIZE * 8, GRID_CELL_SIZE * 8);
	}

	private void handleClick(MouseEvent event) {
		if(currentGame != null) {
			BoardCell target = (BoardCell) event.getSource();
			if (highlighted != null) {
				move(highlighted, target);
				highlighted.setHighlighted(false);
				highlighted = null;
			} else if (currentGame.getPiece(target.getX(), target.getY()) != null) {
				highlighted = target;
				highlighted.setHighlighted(true);
			}
		}
	}

	private void move(BoardCell from, BoardCell to) {
		ChessPiece chessPiece = currentGame.getPiece(from.getX(), from.getY());

		if(chessPiece.move(currentGame, to.getX(), to.getY())) {
			from.setPiece(null);
			to.setPiece(chessPiece);
		}
	}

	public void setGame(Game game) {
		this.currentGame = game;

		for(int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				ChessPiece piece = game.getPiece(x, y);
				cells[x][y].setPiece(piece);
			}
		}
	}
}
