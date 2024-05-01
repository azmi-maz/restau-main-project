package org.group.project.scenes.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.controllers.driver.DriverHomepageController;
import org.group.project.controllers.driver.DriverPendingDeliveryNavbarCounter;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

/**
 * This class prepares the driver home page view scene.
 */
public class DriverView implements ViewMaker {

    public static DriverHomepageController controller;
    public static DriverPendingDeliveryNavbarCounter
            driverPendingDeliveryNavbarCounterController;
    private Stage stage;

    /**
     * This constructor sets up the stage from the main one.
     *
     * @param stage - the main stage.
     */
    public DriverView(Stage stage) {

        this.stage = stage;

    }

    /**
     * This method gets the driver home page view scene.
     *
     * @return the driver home page view scene.
     * @throws IOException // TODO
     */
    @Override
    public Scene getScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "homepages/driver-homepage.fxml"));

        FXMLLoader fxmlLoaderNavbar = new FXMLLoader(Main.class.getResource(
                "navbar/driver/driver-pendingdeliverycounter.fxml"));

        BorderPane borderPane = fxmlLoader.load();
        controller = fxmlLoader.getController();

        VBox pendingDeliveryCounterBox = fxmlLoaderNavbar.load();
        borderPane.getChildren().add(pendingDeliveryCounterBox);
        driverPendingDeliveryNavbarCounterController =
                fxmlLoaderNavbar.getController();

        return new Scene(borderPane, WindowSize.MAIN.WIDTH,
                WindowSize.MAIN.HEIGHT);
    }

}
