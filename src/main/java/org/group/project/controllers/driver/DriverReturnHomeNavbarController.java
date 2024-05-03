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
    private static final String UNDO_BUTTON = "undo";
    private static final int BUTTON_WIDTH = 25;
    private static final int BUTTON_HEIGHT = 25;
    @FXML
    private Button homeButton;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        ImageLoader.setUpGraphicButton(homeButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, UNDO_BUTTON);

        homeButton.setOnMousePressed(e -> {
            DriverView.driverPendingDeliveryNavbarCounterController
                    .refreshPendingDeliveryCounter();
            DriverScenesMap.getDriverStage().setScene(
                    DriverScenesMap.getScenes().get(DriverMapsMain.HOME));
        });

    }


}
