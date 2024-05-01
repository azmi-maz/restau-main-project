package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.scenes.main.WaiterView;
import org.group.project.scenes.waiter.WaiterMapsMain;
import org.group.project.scenes.waiter.WaiterScenesMap;
import org.group.project.scenes.waiter.mainViews.BookingView;

/**
 * This class loads up the pending table reservation navigation bar for waiter.
 */
public class WaiterPendingTableNavbarController {

    @FXML
    private Button homeButton;

    @FXML
    private Button pendingTablesButton;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        ImageLoader.setUpGraphicButton(homeButton, 25, 25,
                "undo");

        ImageLoader.setUpGraphicButton(pendingTablesButton,
                25, 25,
                "reservation");

        homeButton.setOnMousePressed(e -> {
            WaiterView.waiterMainCounterController
                    .refreshMainCounter();
            WaiterScenesMap.getWaiterStage().setScene(
                    WaiterScenesMap.getScenes().get(WaiterMapsMain.HOME));
        });

        pendingTablesButton.setOnMousePressed(e -> {
            BookingView.controller.refreshReservationList();
            BookingView.waiterDeliveryCounterController
                    .refreshDeliveryCounter();
            WaiterScenesMap.getWaiterStage().setScene(
                    WaiterScenesMap.getScenes().get(WaiterMapsMain.BOOKING));
        });

    }
}
