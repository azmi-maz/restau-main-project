package org.group.project.controllers.driver;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.ImageLoader;
import org.group.project.scenes.DriverMapsMain;
import org.group.project.scenes.DriverScenesMap;

import java.net.URISyntaxException;

public class DriverHomepageNavbarController {

    @FXML
    private Button pendingDeliveriesButton;

    @FXML
    private Button deliveryHistoryButton;

    @FXML
    private Button userButton;

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(pendingDeliveriesButton, 25, 25,
                "pending");

        ImageLoader.setUpGraphicButton(deliveryHistoryButton, 25, 25, "history");

        ImageLoader.setUpGraphicButton(userButton, 25, 25, "user");


        pendingDeliveriesButton.setOnMousePressed(e -> {
            DriverScenesMap.getDriverStage().setScene(DriverScenesMap.getScenes().get(DriverMapsMain.DELIVERY));
        });

        deliveryHistoryButton.setOnMousePressed(e -> {
            // TODO order history?
        });

        userButton.setOnMousePressed(e -> {
           // TODO user scene?
        });

    }


}
