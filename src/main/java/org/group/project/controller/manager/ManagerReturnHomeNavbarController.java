package org.group.project.controller.manager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.ImageLoader;
import org.group.project.scenes.ManagerMapsMain;
import org.group.project.scenes.ManagerScenesMap;

import java.net.URISyntaxException;

public class ManagerReturnHomeNavbarController {

    @FXML
    private Button homeButton;

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(homeButton, 25, 25,
                "undo");

        homeButton.setOnMousePressed(e -> {
            ManagerScenesMap.getManagerStage().setScene(ManagerScenesMap.getScenes().get(ManagerMapsMain.HOME));
        });

    }


}
