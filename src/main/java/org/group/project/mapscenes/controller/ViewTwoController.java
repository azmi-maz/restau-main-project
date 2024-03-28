package org.group.project.mapscenes.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.group.project.MainAppMap;
import org.group.project.mapscenes.model.SceneName;

public class ViewTwoController {

    private Stage stage;

    @FXML
    private Label labelText;

    @FXML
    private Button backButton;

    @FXML
    private Button closeButton;

    public ViewTwoController(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("Stage cannot be null");
        }
        this.stage = stage;
    }

    public void handleMousePress(Event event) {
        stage.setScene(MainAppMap.getScenes().get(SceneName.MAIN));
    }

    @FXML
    public void initialize() {
        labelText.setText("This is scene 2");
        closeButton.setText("Log out");
        closeButton.setOnMousePressed(e -> stage.close());
        backButton.setOnMousePressed(e -> handleMousePress(e));
    }
}
