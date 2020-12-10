package com.example.ui;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class SettingsDialog extends JFXDialog {

	public SettingsDialog() {
		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setPrefWidth(200);

		JFXComboBox<String> styleComboBox = new JFXComboBox<>(FXCollections.observableArrayList("Black Stone", "Grey Stone", "Wood"));
		styleComboBox.setValue("Black Stone");

		JFXCheckBox legendCheckBox = new JFXCheckBox("Grid legend");

		VBox container = new VBox(styleComboBox, legendCheckBox);
		container.setSpacing(20);
		container.setAlignment(Pos.CENTER);

		layout.setBody(container);
		setContent(layout);
	}
}
