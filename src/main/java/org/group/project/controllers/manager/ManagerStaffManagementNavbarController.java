package org.group.project.controllers.manager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.ImageLoader;
import org.group.project.scenes.ManagerMapsMain;
import org.group.project.scenes.ManagerScenesMap;

import java.net.URISyntaxException;

public class ManagerStaffManagementNavbarController {

    @FXML
    private Button homeButton;

    @FXML
    private Button addStaffButton;

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(homeButton, 25, 25,
                "undo");

        ImageLoader.setUpGraphicButton(addStaffButton, 25, 25, "add-user");


        homeButton.setOnMousePressed(e -> {
            ManagerScenesMap.getManagerStage().setScene(ManagerScenesMap.getScenes().get(ManagerMapsMain.HOME));
        });

        addStaffButton.setOnMousePressed(e -> {
            // TODO link with add new staff window
        });

    }


}
