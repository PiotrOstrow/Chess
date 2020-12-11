package com.example.game.pieces;

import java.io.Serializable;

public class Position implements Serializable {

	private int x;
	private int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position(Position other) {
		this.x = other.x;
		this.y = other.y;
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

	public void set(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
