package com.example.main;

public class Main {

	// Separate class because of the error described here
	// https://stackoverflow.com/questions/52653836/maven-shade-javafx-runtime-components-are-missing
	public static void main(String[] args) {
		App.main(args);
	}
}
