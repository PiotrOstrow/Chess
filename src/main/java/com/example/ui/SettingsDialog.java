package com.example.ui;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.Properties;

public class SettingsDialog extends JFXDialog {

	private final JFXComboBox<Theme> styleComboBox;
	private final JFXCheckBox legendCheckBox;

	private final Properties properties;
	private final File propertiesFile;

	public SettingsDialog() {
		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setPrefWidth(200);

		Label label = new Label("Theme");
		label.setStyle("-fx-font-weight: bold; -fx-text-fill: white");

		styleComboBox = new JFXComboBox<Theme>(FXCollections.observableArrayList(Theme.values()));
		styleComboBox.setValue(Theme.BLACK_STONE);
		styleComboBox.getStylesheets().add("comboBoxStyle.css");

		legendCheckBox = new JFXCheckBox("Grid legend");
		legendCheckBox.selectedProperty().setValue(true);
		legendCheckBox.setStyle("-fx-text-fill: white");
		legendCheckBox.setCheckedColor(styleComboBox.getFocusColor());

		VBox container = new VBox(label, styleComboBox, legendCheckBox);
		container.setSpacing(20);
		container.setAlignment(Pos.CENTER);

		layout.setBody(container);
		setContent(layout);

		layout.getParent().setStyle("-fx-background-color: rgba(0, 0, 0, 0.7)");

		properties = new Properties();
		propertiesFile = new File("config.ini");
		if(propertiesFile.exists()){
			try {
				properties.load(new FileInputStream(propertiesFile));
				styleComboBox.setValue(Theme.fromName(properties.getProperty("theme", "Black Stone")));
				legendCheckBox.setSelected(Boolean.parseBoolean(properties.getProperty("grid", "false")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Theme getSelectedTheme() {
		return styleComboBox.getValue();
	}

	public boolean isLegendChecked() {
		return legendCheckBox.isSelected();
	}

	public void saveSettingsToFile() {
		try {
			if(!propertiesFile.exists())
				System.out.println(propertiesFile.createNewFile());

			properties.setProperty("theme", styleComboBox.getValue().toString());
			properties.setProperty("grid", Boolean.toString(legendCheckBox.isSelected()));

			FileWriter fileWriter = new FileWriter(propertiesFile);
			properties.store(fileWriter, "");
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onThemeChanged(ChangeListener<Theme> listener) {
		styleComboBox.getSelectionModel().selectedItemProperty().addListener(listener);
	}

	public void onLegendOption(ChangeListener<Boolean> listener) {
		legendCheckBox.selectedProperty().addListener(listener);
	}
}
