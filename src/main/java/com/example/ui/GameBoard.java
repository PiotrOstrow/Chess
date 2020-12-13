package com.example.ui;

import com.example.game.Color;
import com.example.game.Game;
import com.example.game.pieces.*;
import com.example.game.pieces.ChessPiece;
import com.example.game.pieces.King;
import com.example.game.pieces.Position;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class GameBoard extends StackPane {

	private final PromotionDialog promotionDialog;
	private final BoardCell[][] cells = new BoardCell[8][8];

	private BoardCell highlighted;
	private final List<Position> highlightedPossibleMoves = new ArrayList<>();
	private final List<BoardCell> checkHightlight = new ArrayList<>();

	private Game currentGame;
	private Color controlledColor;

	public GameBoard(StackPane dialogRoot) {
		GridPane gridPane = new GridPane();
		for(int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				cells[x][y] = new BoardCell(x, y);
				cells[x][y].setOnMouseClicked(this::handleClick);
				gridPane.add(cells[x][y], x + 1, y + 1);
			}
		}

		gridPane.add(new ImageView(new Image("Chess_Artwork/Chess_Board/Wood/border_left_legend.png")), 0, 0, 1, 10);
		gridPane.add(new ImageView(new Image("Chess_Artwork/Chess_Board/Wood/border_right.png")), 9, 0, 1, 10);
		gridPane.add(new ImageView(new Image("Chess_Artwork/Chess_Board/Wood/border_top.png")), 1, 0, 8, 1);
		gridPane.add(new ImageView(new Image("Chess_Artwork/Chess_Board/Wood/border_bottom_legend.png")), 1, 9, 8, 1);

		gridPane.setAlignment(Pos.CENTER);
		getChildren().add(gridPane);

		promotionDialog = new PromotionDialog();
		promotionDialog.setDialogContainer(dialogRoot);
		promotionDialog.setEffect(new DropShadow());
		promotionDialog.setOverlayClose(false);
		getChildren().add(promotionDialog);

		setMinSize(100, 100);
		setAlignment(Pos.CENTER);

		promotionDialog.queenButton.setOnAction(event -> {
			currentGame.promote(Queen.class);
			promotionDialog.close();
		});

		promotionDialog.knightButton.setOnAction(event -> {
			currentGame.promote(Knight.class);
			promotionDialog.close();
		});

		promotionDialog.rookButton.setOnAction(event -> {
			currentGame.promote(Rook.class);
			promotionDialog.close();
		});

		promotionDialog.bishopButton.setOnAction(event -> {
			currentGame.promote(Bishop.class);
			promotionDialog.close();
		});
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

		if(currentGame.move(chessPiece, to.getX(), to.getY())) {
			if(chessPiece instanceof Pawn) {
				if(chessPiece.getPosition().getY() == 0 || chessPiece.getPosition().getY() == 7) {
					promotionDialog.show();
				}
			}
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

		for(int x = 0; x < 8; x++) {
			ChessPiece p1 = game.getPiece(x, 0);
			ChessPiece p2 = game.getPiece(x, 7);
			if((p1 instanceof Pawn && p1.getColor() == controlledColor)
					|| (p2 instanceof Pawn && p2.getColor() == controlledColor)){
				promotionDialog.show();
			}
		}
	}

	private void highlightCheck(BoardCell cell) {
		cell.setChecked(true);
		checkHightlight.add(cell);
	}

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

	public Color getControlledColor() {
		return controlledColor;
	}
}
