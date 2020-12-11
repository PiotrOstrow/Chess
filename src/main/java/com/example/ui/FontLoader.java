package com.example.ui;

import javafx.application.Platform;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class FontLoader {

	// tries a few different ways to load font, some don't work in IDE, some don't work in a jar...
	public static Font loadFont(String path, int size) {
		Font font = null;

		try {
			URL url = FontLoader.class.getResource(path);
			String fontPath = new URI(url.toString()).getPath();
			if(fontPath != null)
				font = Font.loadFont(new FileInputStream(fontPath), size);

			if(font == null)
				font = Font.loadFont(path, size);

			if(font == null)
				font = Font.loadFont(FontLoader.class.getResourceAsStream(path), size);

			if(font == null)
				throw new NullPointerException("Could not load font");
		} catch (URISyntaxException | NullPointerException | FileNotFoundException e) {
			System.err.println("Could not load font");
			e.printStackTrace();
			Platform.exit();
		}

		return font;
	}
}
