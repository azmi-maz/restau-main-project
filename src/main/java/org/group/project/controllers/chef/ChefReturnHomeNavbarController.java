package org.group.project.controllers.chef;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.scenes.chef.ChefMapsMain;
import org.group.project.scenes.chef.ChefScenesMap;

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
