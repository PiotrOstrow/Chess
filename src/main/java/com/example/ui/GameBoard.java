package com.example.ui;

import com.example.game.Color;
import com.example.game.Game;
import com.example.game.GameCallback;
import com.example.game.pieces.ChessPiece;
import com.example.game.pieces.King;
import com.example.game.pieces.Position;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class GameBoard extends GridPane implements GameCallback {

	private final BoardCell[][] cells = new BoardCell[8][8];

	private BoardCell highlighted;
	private final List<Position> highlightedPossibleMoves = new ArrayList<>();
	private final List<BoardCell> checkHightlight = new ArrayList<>();

	private Game currentGame;
	private Color controlledColor;

	public GameBoard() {
		for(int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				cells[x][y] = new BoardCell(x, y);
				cells[x][y].setOnMouseClicked(this::handleClick);
				add(cells[x][y], x + 1, y + 1);
			}
		}

		add(new ImageView(new Image("Chess_Artwork/Chess Board/Wood/border_left_legend.png")), 0, 0, 1, 10);
		add(new ImageView(new Image("Chess_Artwork/Chess Board/Wood/border_right.png")), 9, 0, 1, 10);
		add(new ImageView(new Image("Chess_Artwork/Chess Board/Wood/border_top.png")), 1, 0, 8, 1);
		add(new ImageView(new Image("Chess_Artwork/Chess Board/Wood/border_bottom_legend.png")), 1, 9, 8, 1);

		setMinSize(100, 100);
		setAlignment(Pos.CENTER);
	}

	@Override
	public void resize(double width, double height) {
		super.resize(width, height);
		double min = Math.min(width, height);
		setScaleX(min / 680);
		setScaleY(min / 680);
	}

	private void handleClick(MouseEvent event) {
		if(currentGame != null) {
			BoardCell target = (BoardCell) event.getSource();
			ChessPiece chessPiece = null;

			if (highlighted != null) {
				highlighted.setHighlighted(BoardCell.Highlight.NONE);

				for(Position p : highlightedPossibleMoves)
					cells[p.getX()][p.getY()].setHighlighted(BoardCell.Highlight.NONE);

				move(highlighted, target);
				highlighted = null;
			} else if ((chessPiece = currentGame.getPiece(target.getX(), target.getY())) != null
					&& chessPiece.getColor() == controlledColor && currentGame.getCurrentMovePlayer() == controlledColor) {
				highlighted = target;
				highlighted.setHighlighted(BoardCell.Highlight.SELECTED);

				List<Position> possibleMoves = currentGame.getPossibleMoves(chessPiece);

				highlightedPossibleMoves.clear();
				highlightedPossibleMoves.addAll(possibleMoves);

				for(Position p : highlightedPossibleMoves)
					cells[p.getX()][p.getY()].setHighlighted(BoardCell.Highlight.POSSIBLE_MOVE);
			}
		}
	}

	private void move(BoardCell from, BoardCell to) {
		ChessPiece chessPiece = currentGame.getPiece(from.getX(), from.getY());
		currentGame.move(chessPiece, to.getX(), to.getY());
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

	private void highlightCheck(BoardCell cell) {
		cell.setChecked(true);
		checkHightlight.add(cell);
	}

	@Override
	public void onMoved() {
		setGame(currentGame, controlledColor); // temporary

		for(BoardCell cell : checkHightlight)
			cell.setChecked(false);
		checkHightlight.clear();

		King checkedKing = currentGame.getCheckedKing();
		if(checkedKing != null && checkedKing.getColor() == controlledColor) {
			ChessPiece checkingPiece = currentGame.getCheckingPiece();

			Position p1 = checkedKing.getPosition();
			Position p2 = checkingPiece.getPosition();

			highlightCheck(cells[p1.getX()][p1.getY()]);
			highlightCheck(cells[p2.getX()][p2.getY()]);
		}
	}
}
