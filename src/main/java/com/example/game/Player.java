package com.example.game;

import java.io.Serializable;

public class Player implements Serializable {

	public final Color color;

	public Player(Color color) {
		this.color = color;
	}

	public int getTimeLeft() {
		return 1;
	}
}
