package org.group.project.controllers.driver;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.Main;
import org.group.project.classes.HelperMethods;
import org.group.project.classes.ImageLoader;
import org.group.project.scenes.DriverMapsMain;
import org.group.project.scenes.DriverScenesMap;
import org.group.project.scenes.MainScenes;

import java.io.IOException;
import java.net.URISyntaxException;

public class DriverHomepageNavbarController {

    @FXML
    private Button pendingDeliveriesButton;

    @FXML
    private Button deliveryHistoryButton;

    @FXML
    private Button userButton;

    @FXML
    private Button logOffButton;

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(pendingDeliveriesButton, 25, 25,
                "pending");

        ImageLoader.setUpGraphicButton(deliveryHistoryButton, 25, 25, "history");

        ImageLoader.setUpGraphicButton(userButton, 25, 25, "user");

        ImageLoader.setUpGraphicButton(logOffButton, 25, 25, "power");

        pendingDeliveriesButton.setOnMousePressed(e -> {
            DriverScenesMap.getDriverStage().setScene(DriverScenesMap.getScenes().get(DriverMapsMain.DELIVERY));
        });

        deliveryHistoryButton.setOnMousePressed(e -> {
            // TODO order history?
        });

        userButton.setOnMousePressed(e -> {
           // TODO user scene?
        });

        logOffButton.setOnMousePressed(e -> {
            // TODO remove all active user info here
            // TODO try catch
            try {
                HelperMethods.clearActiveUser();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Main.getStage().setScene(Main.getScenes().get(MainScenes.LOGIN));
        });

    }


}
