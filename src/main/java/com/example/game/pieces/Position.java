package com.example.game.pieces;

public class Position {

	private int x;
	private int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean isDiagonal(Position other) {
		return isDiagonal(other.x, other.y);
	}

	public boolean isDiagonal(int x, int y){
		return Math.abs(this.x - x) == Math.abs(this.y - y);
	}

	public double distance(Position other) {
		return distance(other.x, other.y);
	}

	public double distance(int x, int y){
		int deltaX = this.x - x;
		int deltaY = this.y - y;
		return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	}

	protected void set(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	protected void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	protected void setY(int y) {
		this.y = y;
	}
}
