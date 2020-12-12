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

public class SettingsDialog extends JFXDialog {

	private final JFXComboBox<Theme> styleComboBox;
	private final JFXCheckBox legendCheckBox;

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
	}

	public void onThemeChanged(ChangeListener<Theme> listener) {
		styleComboBox.getSelectionModel().selectedItemProperty().addListener(listener);
	}

	public void onLegendOption(ChangeListener<Boolean> listener) {
		legendCheckBox.selectedProperty().addListener(listener);
	}
}
