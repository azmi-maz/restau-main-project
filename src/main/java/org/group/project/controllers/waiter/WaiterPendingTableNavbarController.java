package org.group.project.controllers.waiter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.ImageLoader;
import org.group.project.scenes.WaiterMapsMain;
import org.group.project.scenes.WaiterScenesMap;

import java.net.URISyntaxException;

public class WaiterPendingTableNavbarController {

    @FXML
    private Button homeButton;

    @FXML
    private Button pendingDeliveryButton;

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(homeButton, 25, 25,
                "undo");

        ImageLoader.setUpGraphicButton(pendingDeliveryButton, 25, 25,
                "delivery");

        homeButton.setOnMousePressed(e -> {
            WaiterScenesMap.getWaiterStage().setScene(WaiterScenesMap.getScenes().get(WaiterMapsMain.HOME));
        });

        pendingDeliveryButton.setOnMousePressed(e -> {
            WaiterScenesMap.getWaiterStage().setScene(WaiterScenesMap.getScenes().get(WaiterMapsMain.DELIVERY));
        });


    }


}
