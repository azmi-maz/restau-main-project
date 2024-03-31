package org.group.project.controllers.chef;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.ImageLoader;
import org.group.project.scenes.ChefMapsMain;
import org.group.project.scenes.ChefScenesMap;

import java.net.URISyntaxException;

public class ChefReturnHomeNavbarController {

    @FXML
    private Button homeButton;

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(homeButton, 25, 25, "undo");

        homeButton.setOnMousePressed(e -> {
            ChefScenesMap.getChefStage().setScene(ChefScenesMap.getScenes().get(ChefMapsMain.HOME));
        });

    }


}
