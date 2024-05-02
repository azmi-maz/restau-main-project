package org.group.project.controllers.driver;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.scenes.driver.DriverMapsMain;
import org.group.project.scenes.driver.DriverScenesMap;
import org.group.project.scenes.main.DriverView;

/**
 * This class loads up the return to home navigation bar for driver.
 *
 * @author azmi_maz
 */
public class DriverReturnHomeNavbarController {

    @FXML
    private Button homeButton;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        ImageLoader.setUpGraphicButton(homeButton,
                25, 25, "undo");

        homeButton.setOnMousePressed(e -> {
            DriverView.driverPendingDeliveryNavbarCounterController
                    .refreshPendingDeliveryCounter();
            DriverScenesMap.getDriverStage().setScene(
                    DriverScenesMap.getScenes().get(DriverMapsMain.HOME));
        });

    }


}
