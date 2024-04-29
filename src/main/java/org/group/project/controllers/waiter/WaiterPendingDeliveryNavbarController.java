package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.scenes.main.WaiterView;
import org.group.project.scenes.waiter.WaiterMapsMain;
import org.group.project.scenes.waiter.WaiterScenesMap;
import org.group.project.scenes.waiter.mainViews.DeliveryView;

import java.net.URISyntaxException;

public class WaiterPendingDeliveryNavbarController {

    @FXML
    private Button homeButton;

    @FXML
    private Button pendingDeliveryButton;

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(homeButton, 25, 25,
                "undo");

        ImageLoader.setUpGraphicButton(pendingDeliveryButton,
                25, 25,
                "delivery");

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
