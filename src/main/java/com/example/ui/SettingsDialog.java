package com.example.ui;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
		label.setStyle("-fx-font-weight: bold");

		styleComboBox = new JFXComboBox<Theme>(FXCollections.observableArrayList(
				Theme.BLACK_STONE, Theme.GREY_STONE, Theme.WOOD
		));
		styleComboBox.setValue(Theme.BLACK_STONE);

		legendCheckBox = new JFXCheckBox("Grid legend");

		VBox container = new VBox(label, styleComboBox, legendCheckBox);
		container.setSpacing(20);
		container.setAlignment(Pos.CENTER);

		layout.setBody(container);
		setContent(layout);
	}

	public void onThemeChanged(ChangeListener<Theme> listener) {
		styleComboBox.getSelectionModel().selectedItemProperty().addListener(listener);
	}

	public void onLegendOption(ChangeListener<Boolean> listener) {
		legendCheckBox.selectedProperty().addListener(listener);
	}
}
