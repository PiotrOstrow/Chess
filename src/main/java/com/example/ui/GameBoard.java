package com.example.ui;

import com.example.game.Color;
import com.example.game.Game;
import com.example.game.Move;
import com.example.game.pieces.*;
import com.example.game.pieces.ChessPiece;
import com.example.game.pieces.King;
import com.example.game.pieces.Position;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

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

	private Theme theme = Theme.BLACK_STONE;

	private ImageView[] borders = new ImageView[4];
	private boolean showLegend = true;

	public GameBoard(StackPane dialogRoot) {
		GridPane gridPane = new GridPane();
		for(int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				cells[x][y] = new BoardCell(x, y);
				cells[x][y].setOnMouseClicked(this::handleClick);
				gridPane.add(cells[x][y], x + 1, y + 1);
			}
		}

		gridPane.add((borders[0] = new ImageView()), 0, 0, 1, 10);
		gridPane.add((borders[1] = new ImageView()), 9, 0, 1, 10);
		gridPane.add((borders[2] = new ImageView()), 1, 0, 8, 1);
		gridPane.add((borders[3] = new ImageView()), 1, 9, 8, 1);

		loadBorder();

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

	public void setTheme(Theme theme) {
		this.theme = theme;

		for(int x = 0; x < cells.length; x++) {
			for(int y = 0; y < cells[0].length; y++) {
				cells[x][y].setTheme(theme);
			}
		}

		// update highlights to adjust for theme
		if(highlighted != null) {
			for (Position p : highlightedPossibleMoves)
				cells[p.getX()][p.getY()].setHighlighted(BoardCell.Highlight.POSSIBLE_MOVE);

			highlighted.setHighlighted(BoardCell.Highlight.SELECTED);
		}

		loadBorder();
	}

	public void setShowLegend(boolean legend) {
		this.showLegend = legend;
		loadBorder();
	}

	private void loadBorder() {
		borders[0].setImage(new Image("Chess_Artwork/Chess_Board/" + theme.getBoardFolder() + "/border_left" + (showLegend ? "_legend" : "") + ".png"));
		borders[1].setImage(new Image("Chess_Artwork/Chess_Board/" + theme.getBoardFolder() + "/border_right.png"));
		borders[2].setImage(new Image("Chess_Artwork/Chess_Board/" + theme.getBoardFolder() + "/border_top.png"));
		borders[3].setImage(new Image("Chess_Artwork/Chess_Board/" + theme.getBoardFolder() + "/border_bottom" + (showLegend ? "_legend" : "") + ".png"));
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
			// promotion dialog
			if(chessPiece instanceof Pawn) {
				if(chessPiece.getPosition().getY() == 0 || chessPiece.getPosition().getY() == 7) {
					promotionDialog.show();
				}
			}
		}
	}

	public void setGame(Game game, Color controlledColor) {
		game.addGameCallback(this::onMoved);
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

	private void animateMove(final Move move) {
		if(move.promote != null) { // no animation for promote
			cells[move.toX][move.toY].setPiece(currentGame.getPiece(move.toX, move.toY));
		} else {
			BoardCell from = cells[move.fromX][move.fromY];
			BoardCell to = cells[move.toX][move.toY];

			final ImageView animated = new ImageView();
			getChildren().add(animated);
			animated.setImage(new Image(to.getImagePath(move.movedPiece)));
			animated.setVisible(true);

			Point2D translateFrom = from.localToScene(animated.getImage().getWidth() / 2, animated.getImage().getHeight() / 2);
			translateFrom = sceneToLocal(translateFrom);
			animated.setTranslateX(translateFrom.getX() - getWidth() / 2);
			animated.setTranslateY(translateFrom.getY() - getHeight() / 2);

			Point2D translateTo = to.localToScene(animated.getImage().getWidth() / 2, animated.getImage().getHeight() / 2);
			translateTo = sceneToLocal(translateTo);

			from.setPiece(null);

			EventHandler<ActionEvent> onFinished = ae -> {
				// set captured to null in case it's en passant
				if(move.isEnPassant()) {
					Position c = move.captured.getPosition();
					cells[c.getX()][c.getY()].setPiece(null);
				}
				to.setPiece(move.movedPiece);
				getChildren().remove(animated);
			};

			KeyValue keyValueX = new KeyValue(animated.translateXProperty(), translateTo.getX() - getWidth() / 2);
			KeyValue keyValueY = new KeyValue(animated.translateYProperty(), translateTo.getY() - getHeight() / 2);
			KeyFrame keyFrame = new KeyFrame(Duration.millis(150), onFinished, keyValueX, keyValueY);

			if(move.isCastleMove()) { // animate rook
				int rookFromX = move.fromX - move.toX > 0 ? 0 : 7;
				int rookToX = rookFromX == 7 ? 5 : 3;
				Move rookMove = new Move(move.captured, rookFromX, move.fromY, rookToX, move.toY, null);
				animateMove(rookMove);
			}

			Timeline timeline = new Timeline(keyFrame);
			timeline.play();
		}
	}

	public void onMoved(Move move) {
		animateMove(move);

		// highlight check
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
