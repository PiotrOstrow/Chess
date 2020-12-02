package com.example.ui;

import com.example.game.Color;
import com.example.game.Game;
import com.example.game.pieces.ChessPiece;
import com.example.game.pieces.Position;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

import static com.example.ui.BoardCell.GRID_CELL_SIZE;

public class GameBoard extends GridPane {

	private final BoardCell[][] cells = new BoardCell[8][8];

	private BoardCell highlighted;
	private final List<Position> highlightedPossibleMoves = new ArrayList<>();

	private Game currentGame;
	private Color controlledColor;

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
			ChessPiece chessPiece = null;

			if (highlighted != null) {
				move(highlighted, target);
				highlighted.setHighlighted(BoardCell.Highlight.NONE);
				highlighted = null;

				for(Position p : highlightedPossibleMoves)
					cells[p.getX()][p.getY()].setHighlighted(BoardCell.Highlight.NONE);
			} else if ((chessPiece = currentGame.getPiece(target.getX(), target.getY())) != null
					&& chessPiece.getColor() == controlledColor && currentGame.getCurrentMovePlayer() == controlledColor) {
				highlighted = target;
				highlighted.setHighlighted(BoardCell.Highlight.SELECTED);

				List<Position> possibleMoves = chessPiece.getPossibleMoves(currentGame);

				highlightedPossibleMoves.clear();
				highlightedPossibleMoves.addAll(possibleMoves);

				for(Position p : highlightedPossibleMoves)
					cells[p.getX()][p.getY()].setHighlighted(BoardCell.Highlight.POSSIBLE_MOVE);
			}
		}
	}

	private void move(BoardCell from, BoardCell to) {
		ChessPiece chessPiece = currentGame.getPiece(from.getX(), from.getY());

		if(currentGame.move(chessPiece, to.getX(), to.getY())) {
			from.setPiece(null);
			to.setPiece(chessPiece);
		}
	}

	public void setGame(Game game, Color controlledColor) {
		this.currentGame = game;
		this.controlledColor = controlledColor;

		for(int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				ChessPiece piece = game.getPiece(x, y);
				cells[x][y].setPiece(piece);
			}
		}
	}
}
