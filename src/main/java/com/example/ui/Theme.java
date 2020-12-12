package com.example.ui;

public enum Theme {
	WOOD("Wood", "Wood", "Wood"),
	BLACK_STONE("Black Stone", "Stone_Black", "Stone"),
	GREY_STONE("Grey Stone", "Stone_Grey", "Stone");

	private final String name;
	private final String boardFolder;
	private final String symbolsFolder;

	Theme(String name, String boardFolder, String symbolsFolder) {
		this.name = name;
		this.boardFolder = boardFolder;
		this.symbolsFolder = symbolsFolder;
	}

	public String getBoardFolder() {
		return boardFolder;
	}

	public String getSymbolsFolder() {
		return symbolsFolder;
	}

	@Override
	public String toString() {
		return name;
	}
}
