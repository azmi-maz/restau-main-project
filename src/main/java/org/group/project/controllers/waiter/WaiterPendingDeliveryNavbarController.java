package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.scenes.main.WaiterView;
import org.group.project.scenes.waiter.WaiterMapsMain;
import org.group.project.scenes.waiter.WaiterScenesMap;
import org.group.project.scenes.waiter.mainViews.DeliveryView;

/**
 * This class loads up the counter of pending delivery orders.
 *
 * @author azmi_maz
 */
public class WaiterPendingDeliveryNavbarController {
    private static final String UNDO_BUTTON = "undo";
    private static final String DELIVERY_BUTTON = "delivery";
    private static final int BUTTON_WIDTH = 25;
    private static final int BUTTON_HEIGHT = 25;
    @FXML
    private Button homeButton;

    @FXML
    private Button pendingDeliveryButton;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        ImageLoader.setUpGraphicButton(homeButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, UNDO_BUTTON);

        ImageLoader.setUpGraphicButton(pendingDeliveryButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, DELIVERY_BUTTON);

        homeButton.setOnMousePressed(e -> {
            WaiterView.waiterMainCounterController.refreshMainCounter();
            WaiterScenesMap.getWaiterStage().setScene(WaiterScenesMap
                    .getScenes().get(WaiterMapsMain.HOME));
        });

        pendingDeliveryButton.setOnMousePressed(e -> {
            DeliveryView.controller.refreshPendingDeliveryList();
            DeliveryView.waiterBookingCounterController
                    .refreshBookingCounter();
            WaiterScenesMap.getWaiterStage().setScene(WaiterScenesMap
                    .getScenes().get(WaiterMapsMain.DELIVERY));
        });
    }
}
