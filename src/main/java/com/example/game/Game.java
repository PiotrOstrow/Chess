package com.example.game;

import com.example.game.pieces.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class Game {

	private final ChessPiece[][] pieces = new ChessPiece[8][8];
	private final ObservableList<ChessPiece> capturedPieces = FXCollections.observableArrayList();
	private final HashMap<ChessPiece, List<Position>> possibleMovesCache = new HashMap<>();

	private final ArrayList<GameCallback> callbacks = new ArrayList<>();

	private final Stack<Move> moveLogStack = new Stack<>();

	private final Player playerWhite;
	private final Player playerBlack;

	private King whiteKing;
	private King blackKing;

	private Player currentMovePlayer;

	public Game() {
		playerWhite = new Player(Color.WHITE);
		playerBlack = new Computer(this, Color.BLACK);

		currentMovePlayer = playerWhite;
	}

	public ChessPiece getPiece(int x, int y) {
		if(x < 0 || y < 0 || x >= 8 || y >= 8)
			 return null;
		return pieces[x][y];
	}

	public boolean move(ChessPiece chessPiece, int x, int y) {
		// TODO: check if move results in a check
		// save the location before moving
		int previousX = chessPiece.getPosition().getX();
		int previousY = chessPiece.getPosition().getY();

		if(chessPiece.move(this, x, y)){
			// check if a piece was captured
			if(pieces[x][y] != null)
				capturedPieces.add(pieces[x][y]);

			if ((y==0 || y==7)&&chessPiece.isPromoteAble())
				chessPiece = new Queen(x,y,chessPiece.getColor());

			moveLogStack.add(new Move(chessPiece, previousX, previousY, x, y, pieces[x][y]));

			pieces[x][y] = chessPiece;
			pieces[previousX][previousY] = null;

			if(isInCheck(chessPiece.getColor())){
				reverseMove();
				return false;
			} else {
				possibleMovesCache.clear();

				// switch current move player, bofore callback
				currentMovePlayer = currentMovePlayer == playerWhite ? playerBlack : playerWhite;

				for (GameCallback callback : callbacks)
					callback.onMoved();
			}
			return true;
		}
		return false;
	}

	// filters out possible moves resulting in check of the king with the same color and caches the result until a move is made
	public List<Position> getPossibleMoves(ChessPiece piece) {
		if(possibleMovesCache.containsKey(piece))
			return possibleMovesCache.get(piece);

		// piece will be moved around, save current position
		int x = piece.getPosition().getX();
		int y = piece.getPosition().getY();

		List<Position> possibleMoves = piece.getPossibleMoves(this);

		ListIterator<Position> iterator = possibleMoves.listIterator();
		while(iterator.hasNext()) {
			Position pos = iterator.next();

			// make the move by just swapping
			ChessPiece temp = pieces[pos.getX()][pos.getY()];
			pieces[pos.getX()][pos.getY()] = piece;
			pieces[piece.getPosition().getX()][piece.getPosition().getY()] = null;
			// adjust positions in the objects so the king also has correct highlights
			piece.getPosition().set(pos.getX(), pos.getY());

			// if in check, remove from possible moves
			if(isInCheck(piece.getColor()))
				iterator.remove();

			// reverse the move and reset piece position
			piece.getPosition().set(x, y);
			pieces[piece.getPosition().getX()][piece.getPosition().getY()] = piece;
			pieces[pos.getX()][pos.getY()] = temp;
		}

		possibleMovesCache.put(piece, possibleMoves);

		return possibleMoves;
	}

	/**
	 * @return true if given color is in check
	 */
	private boolean isInCheck(Color color) {
		King king = getKing(color);

		for(int x = 0; x < 8; x++) {
			for(int y = 0; y < 8; y++) {
				ChessPiece piece = getPiece(x, y);
				if (piece != null && piece.getColor() != color) {
					List<Position> possibleMoves = piece.getPossibleMoves(this);
					for(Position position : possibleMoves){
						if(position.getX() == king.getPosition().getX() && position.getY() == king.getPosition().getY())
							return true;
					}
				}
			}
		}
		return false;
	}

	public void reverseMove() {
		if(!moveLogStack.isEmpty()) {
			Move move = moveLogStack.pop();

			move.movedPiece.getPosition().set(move.fromX, move.fromY);

			pieces[move.fromX][move.fromY] = move.movedPiece;
			pieces[move.toX][move.toY] = move.captured;

			// reverse captured piece
			if (move.captured != null)
				capturedPieces.remove(move.captured);
		}
	}

	public void addPiece(ChessPiece piece){
		pieces[piece.getPosition().getX()][piece.getPosition().getY()] = piece;

		if(piece instanceof King){
			if(piece.getColor() == Color.WHITE)
				whiteKing = (King) piece;
			else
				blackKing = (King) piece;
		}
	}

	public void setUpNormal(){
		for (int i=0; i<=7; i++){
			addPiece(new Pawn(i, 6, Color.WHITE));
			addPiece(new Pawn(i, 1, Color.BLACK));
		}
		addPiece(new King(4, 7, Color.WHITE));
		addPiece(new King(4, 0, Color.BLACK));
		addPiece(new Knight(1, 7, Color.WHITE));
		addPiece(new Knight(1, 0, Color.BLACK));
		addPiece(new Knight(6, 7, Color.WHITE));
		addPiece(new Knight(6, 0, Color.BLACK));
		addPiece(new Rook(0, 7, Color.WHITE));
		addPiece(new Rook(0, 0, Color.BLACK));
		addPiece(new Rook(7, 7, Color.WHITE));
		addPiece(new Rook(7, 0, Color.BLACK));
		addPiece(new Bishop(2, 7, Color.WHITE));
		addPiece(new Bishop(2, 0, Color.BLACK));
		addPiece(new Bishop(5, 7, Color.WHITE));
		addPiece(new Bishop(5, 0, Color.BLACK));
		addPiece(new Queen(3, 7, Color.WHITE));
		addPiece(new Queen(3, 0, Color.BLACK));
	}

	public Color getCurrentMovePlayer() {
		return currentMovePlayer.color;
	}

	public ObservableList<ChessPiece> getCapturedPieces() {
		return FXCollections.unmodifiableObservableList(capturedPieces);
	}

	public void addGameCallback(GameCallback callback) {
		callbacks.add(callback);
	}

	public King getKing(Color color) {
		if(color == Color.WHITE)
			return whiteKing;
		return blackKing;
	}
}
