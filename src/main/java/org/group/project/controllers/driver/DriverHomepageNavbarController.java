package org.group.project.controllers.driver;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.Main;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.controllers.main.UserProfileView;
import org.group.project.exceptions.ClearFileFailedException;
import org.group.project.scenes.MainScenes;
import org.group.project.scenes.driver.DriverMapsMain;
import org.group.project.scenes.driver.DriverScenesMap;
import org.group.project.scenes.driver.mainViews.DeliveryView;

/**
 * This class loads up the navigation bar for driver home page.
 */
public class DriverHomepageNavbarController {

    @FXML
    private Button pendingDeliveriesButton;

    @FXML
    private Button deliveryHistoryButton;

    @FXML
    private Button userButton;

    @FXML
    private Button logOffButton;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        ImageLoader.setUpGraphicButton(pendingDeliveriesButton,
                25, 25,
                "pending");

        ImageLoader.setUpGraphicButton(deliveryHistoryButton,
                25, 25, "history");

        ImageLoader.setUpGraphicButton(userButton, 25,
                25, "user");

        ImageLoader.setUpGraphicButton(logOffButton, 25,
                25, "power");

        pendingDeliveriesButton.setOnMousePressed(e -> {

            DeliveryView.controller.refreshPendingDeliveryList();
            DriverScenesMap.getDriverStage().setScene(
                    DriverScenesMap.getScenes()
                            .get(DriverMapsMain.DELIVERY));
        });

        deliveryHistoryButton.setOnMousePressed(e -> {
            // Purposely not implemented
        });

        userButton.setOnMousePressed(e -> {
            UserProfileView userProfileView = new UserProfileView();
            userProfileView.showWindow();
        });

        logOffButton.setOnMousePressed(e -> {
            // Log off by removing active user info
            try {
                DataManager.clearFileData("ACTIVE_USER");
            } catch (ClearFileFailedException ex) {
                AlertPopUpWindow.displayErrorWindow(
                        "Error",
                        ex.getMessage()
                );
                ex.printStackTrace();
            }
            Main.getStage().setScene(Main.getScenes()
                    .get(MainScenes.LOGIN));
        });

    }


}
