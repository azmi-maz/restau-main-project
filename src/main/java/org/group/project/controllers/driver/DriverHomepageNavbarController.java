package org.group.project.controllers.driver;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.controllers.main.UserProfileView;
import org.group.project.exceptions.ClearFileFailedException;
import org.group.project.scenes.MainScenes;
import org.group.project.scenes.MainScenesMap;
import org.group.project.scenes.driver.DriverMapsMain;
import org.group.project.scenes.driver.DriverScenesMap;
import org.group.project.scenes.driver.mainViews.DeliveryView;

/**
 * This class loads up the navigation bar for driver home page.
 *
 * @author azmi_maz
 */
public class DriverHomepageNavbarController {
    private static final String PENDING_BUTTON = "pending";
    private static final String HISTORY_BUTTON = "history";
    private static final String USER_BUTTON = "user";
    private static final String POWER_BUTTON = "power";
    private static final int BUTTON_WIDTH = 25;
    private static final int BUTTON_HEIGHT = 25;
    private static final String ACTIVE_USER = "ACTIVE_USER";
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
                BUTTON_WIDTH, BUTTON_HEIGHT, PENDING_BUTTON);

        ImageLoader.setUpGraphicButton(deliveryHistoryButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, HISTORY_BUTTON);

        ImageLoader.setUpGraphicButton(userButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, USER_BUTTON);

        ImageLoader.setUpGraphicButton(logOffButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, POWER_BUTTON);

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
                DataManager.clearFileData(ACTIVE_USER);
            } catch (ClearFileFailedException ex) {
                AlertPopUpWindow.displayErrorWindow(
                        ex.getMessage()
                );
                ex.printStackTrace();
            }
            MainScenesMap.getStage().setScene(MainScenesMap.getScenes()
                    .get(MainScenes.LOGIN));
        });

    }


}
