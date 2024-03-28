package org.group.project.mapscenes.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.group.project.MainAppMap;
import org.group.project.mapscenes.model.SceneName;

public class MainController {

    private Stage stage;

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button closeButton;

    public MainController(Stage stage) {

        this.stage = stage;
    }

    public void handleOnPressButton1(MouseEvent event) {
        stage.setScene(MainAppMap.getScenes().get(SceneName.SCENE1));
    }

    public void handleOnPressButton2(MouseEvent event) {
        stage.setScene(MainAppMap.getScenes().get(SceneName.SCENE2));
    }

    public void handleOnPressButton3(MouseEvent event) {
        stage.setScene(MainAppMap.getScenes().get(SceneName.SCENE3));
    }

    @FXML
    public void initialize() {

        button1.setOnMousePressed(e ->
                handleOnPressButton1(e));

        button2.setOnMousePressed(e ->
                handleOnPressButton2(e));

        button3.setOnMousePressed(e ->
                handleOnPressButton3(e));

        closeButton.setOnMousePressed(e -> stage.close());
    }

}
