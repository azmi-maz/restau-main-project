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
 *
 * @author azmi_maz
 */
public class WaiterPendingTableNavbarController {
    private static final String UNDO_BUTTON = "undo";
    private static final String RESERVATION_BUTTON = "reservation";
    private static final int BUTTON_WIDTH = 25;
    private static final int BUTTON_HEIGHT = 25;
    @FXML
    private Button homeButton;

    @FXML
    private Button pendingTablesButton;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        ImageLoader.setUpGraphicButton(homeButton,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                UNDO_BUTTON);

        ImageLoader.setUpGraphicButton(pendingTablesButton,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                RESERVATION_BUTTON);

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
