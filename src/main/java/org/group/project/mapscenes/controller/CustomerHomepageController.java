package org.group.project.mapscenes.controller;

import javafx.stage.Stage;

public class CustomerHomepageController extends IllegalArgumentException {

    private Stage stage;

    public CustomerHomepageController(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("Stage cannot be null");
        }

        this.stage = stage;
    }

//    public void handleMousePress(Event event) {
//        stage.setScene(MainAppMap.getScenes().get(SceneName.MAIN));
//    }

}
