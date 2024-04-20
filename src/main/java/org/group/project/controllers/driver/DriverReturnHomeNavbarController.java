package org.group.project.controllers.driver;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.scenes.driver.DriverMapsMain;
import org.group.project.scenes.driver.DriverScenesMap;

import java.net.URISyntaxException;

public class DriverReturnHomeNavbarController {

    @FXML
    private Button homeButton;

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(homeButton, 25, 25, "undo");

        homeButton.setOnMousePressed(e -> {
            DriverScenesMap.getDriverStage().setScene(DriverScenesMap.getScenes().get(DriverMapsMain.HOME));
        });

    }


}
