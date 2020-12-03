package com.example.game;

import com.example.game.pieces.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Constructor;
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

	private King checkedKing;
	private ChessPiece checkingPiece;

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

	public void promote(Class<? extends ChessPiece> pieceType) {
		try {
			// find pawn to be promoted
			ChessPiece pawn = getPromotablePawn();

			if (pawn == null)
				throw new IllegalStateException("Trying to promote while no pawn is in the required position");

			Constructor<? extends ChessPiece> constructor = pieceType.getConstructor(Integer.TYPE, Integer.TYPE, Color.class);
			ChessPiece newPiece = constructor.newInstance(pawn.getPosition().getX(), pawn.getPosition().getY(), pawn.getColor());
			addPiece(newPiece); // overrides old piece
			onMoved(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean move(ChessPiece chessPiece, int x, int y) {
		// save the location before moving
		int previousX = chessPiece.getPosition().getX();
		int previousY = chessPiece.getPosition().getY();

		if(chessPiece.move(this, x, y)){
			// check if a piece was captured
			if(pieces[x][y] != null)
				capturedPieces.add(pieces[x][y]);

			moveLogStack.add(new Move(chessPiece, previousX, previousY, x, y, pieces[x][y]));

			pieces[x][y] = chessPiece;
			pieces[previousX][previousY] = null;

			if(isInCheck(chessPiece.getColor())){
				reverseMove();
				return false;
			} else { // move passed
				boolean promoted = (y==0 || y==7)&&chessPiece.isPromoteAble();
				onMoved(!promoted);
			}
			return true;
		} else if(canCastleMove(chessPiece, x, y)) {
			King king = getKing(chessPiece.getColor());
			Rook rook = (Rook) (chessPiece instanceof King ?  getPiece(x, y) : chessPiece);

			int xDir = king.getPosition().getX() - rook.getPosition().getX() > 0 ? -1 : 1;

			pieces[king.getPosition().getX()][king.getPosition().getY()] = null;
			pieces[rook.getPosition().getX()][rook.getPosition().getY()] = null;

			rook.getPosition().setX(king.getPosition().getX() + xDir);
			king.getPosition().setX(king.getPosition().getX() + xDir * 2);

			pieces[king.getPosition().getX()][king.getPosition().getY()] = king;
			pieces[rook.getPosition().getX()][rook.getPosition().getY()] = rook;

			moveLogStack.add(new Move(king, 4, king.getPosition().getY(), king.getPosition().getX(), king.getPosition().getY(), rook));

			onMoved(true);

			return true;
		}
		return false;
	}

	// called after every move
	private void onMoved(boolean switchPlayer) {
		possibleMovesCache.clear();

		// switch current move player, bofore callback
		if(switchPlayer)
			currentMovePlayer = currentMovePlayer == playerWhite ? playerBlack : playerWhite;

		// TODO: temporary?
		if((checkingPiece = getCheckingPiece(Color.WHITE)) != null)
			checkedKing = getKing(Color.WHITE);
		else if((checkingPiece = getCheckingPiece(Color.BLACK)) != null)
			checkedKing = getKing(Color.BLACK);
		else
			checkedKing = null;

		for (GameCallback callback : callbacks)
			callback.onMoved();
	}

	private boolean canCastleMove(ChessPiece chessPiece, int x, int y){
		if(chessPiece instanceof King || chessPiece instanceof Rook)
			for(Position possibleMove : getPossibleMoves(chessPiece))
				if(possibleMove.getX() == x && possibleMove.getY() == y)
					return true;
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

			// TODO: Pawn returns positions out of bounds in the list
			if(pos.getX() < 0 || pos.getX() >= 8 || pos.getY() < 0 || pos.getY() >= 8){
				iterator.remove();
				continue;
			}

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

		// castling
		if(!piece.hasMoved() && piece instanceof King) {
			King king = (King) piece;
			// already checked if king moved, the Y position should be correct
			ChessPiece pieceLeft = getPiece(0, king.getPosition().getY());
			ChessPiece pieceRight = getPiece(7, king.getPosition().getY());

			if(pieceLeft instanceof Rook && canCastle(king, (Rook) pieceLeft)) {
				possibleMoves.add(new Position(0, king.getPosition().getY()));
				getPossibleMoves(pieceLeft).add(new Position(king.getPosition()));
			}

			if(pieceRight instanceof Rook && canCastle(king, (Rook) pieceRight)) {
				possibleMoves.add(new Position(7, king.getPosition().getY()));
				getPossibleMoves(pieceRight).add(new Position(king.getPosition()));
			}
		}

		possibleMovesCache.put(piece, possibleMoves);

		// so that the code for castling gets run and possible move for castling gets added
		if(!piece.hasMoved() && piece instanceof Rook)
			getPossibleMoves(getKing(piece.getColor()));

		return possibleMoves;
	}

	private boolean canCastle(King king, Rook rook) {
		// Castling may be done only if the king has never moved, the rook involved has never moved...
		if(king.getColor() != rook.getColor() || king.hasMoved() || rook.hasMoved())
			return false;

		// the squares between the king and the rook involved are unoccupied...
		int dir = king.getPosition().getX() - rook.getPosition().getX() > 0 ? -1 : 1;
		for(int x = king.getPosition().getX() + dir; x != rook.getPosition().getX(); x += dir)
			if(getPiece(x, king.getPosition().getY()) != null)
				return false;

		// the king is not in check...
		if(isInCheck(king.getColor()))
			return false;

		// and the king does not cross over or end on a square attacked by an enemy piece...
		// get all positions in between
		int squaresInBetween = Math.abs(king.getPosition().getX() - rook.getPosition().getX()) - 1;
		Position[] positions = new Position[squaresInBetween];
		for(int i = 0, x = king.getPosition().getX() + dir; x != rook.getPosition().getX(); i++, x += dir)
			positions[i] = new Position(x, king.getPosition().getY());

		// check every position int between for every possible move for all pieces of opposite color
		for(int x = 0; x < 8; x++) {
			for(int y = 0; y < 8; y++) {
				ChessPiece piece = getPiece(x, y);

				// have to break the loop for the king if it's never moved, otherwise it would result in an infinite resursive loop
				if(piece == null || piece.getColor() == king.getColor() || (piece instanceof King && !piece.hasMoved()))
					continue;

				// compare each possible position with each position in between the king and the rook
				for(Position pos1 : getPossibleMoves(piece)){
					for(Position pos2 : positions){
						if(pos1.getX() == pos2.getX() && pos1.getY() == pos2.getY())
							return false;
					}
				}

			}
		}

		return true;
	}

	/**
	 * Returns a pawn that is in the position to be promoted, can only be one at a time, null if there is none
	 */
	private ChessPiece getPromotablePawn() {
		ChessPiece pawn = null;

		for (int i = 0; i < 8 && !(pawn instanceof Pawn); i++) pawn = getPiece(i, 0);
		for (int i = 0; i < 8 && !(pawn instanceof Pawn); i++) pawn = getPiece(i, 7);

		if(pawn instanceof Pawn)
			return pawn;

		return null;
	}

	public boolean isInCheckMate(Color color) {
		// if player has no possible moves, then the king is in check mate
		for(int x = 0; x < 8; x++) {
			for(int y = 0; y < 8; y++) {
				ChessPiece piece = getPiece(x, y);
				if(piece != null && piece.getColor() == color){
					List<Position> possibleMoves = getPossibleMoves(piece);
					if(possibleMoves.size() > 0)
						return false;
				}
			}
		}

		// unless there is a pawn to promote
		ChessPiece pawn = getPromotablePawn();
		return pawn != null && pawn.getColor() != color;
	}

	/**
	 * @return true if given color is in check
	 */
	private boolean isInCheck(Color color) {
		return getCheckingPiece(color) != null;
	}

	/**
	 * @param color color of King to check check on
	 * @return the piece that checks the king of the given color, null if not in check
	 */
	private ChessPiece getCheckingPiece(Color color) {
		King king = getKing(color);

		for(int x = 0; x < 8; x++) {
			for(int y = 0; y < 8; y++) {
				ChessPiece piece = getPiece(x, y);
				if (piece != null && piece.getColor() != color) {
					List<Position> possibleMoves = piece.getPossibleMoves(this);
					for(Position position : possibleMoves){
						if(position.getX() == king.getPosition().getX() && position.getY() == king.getPosition().getY()) {
							return piece;
						}
					}
				}
			}
		}
		return null;
	}

	public void reverseMove() {
		if(moveLogStack.isEmpty())
			return;

		Move move = moveLogStack.pop();

		if (move.movedPiece instanceof King && Math.abs(move.fromX - move.toX) > 1) { // castling
			ChessPiece king = move.movedPiece;
			ChessPiece rook = move.captured;
			int xDir = move.fromX - move.toX > 0 ? -1 : 1;
			int rookX = xDir > 0 ? 7 : 0;

			pieces[king.getPosition().getX()][king.getPosition().getY()] = null;
			pieces[rook.getPosition().getX()][rook.getPosition().getY()] = null;

			king.getPosition().set(move.fromX, move.fromY);
			rook.getPosition().set(rookX, move.fromY);

			pieces[king.getPosition().getX()][king.getPosition().getY()] = king;
			pieces[rook.getPosition().getX()][rook.getPosition().getY()] = rook;
		} else { // regular move
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

	public King getCheckedKing() {
		return checkedKing;
	}

	public ChessPiece getCheckingPiece() {
		return checkingPiece;
	}
}
