package org.group.project.scenes.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.controllers.driver.DriverHomepageController;
import org.group.project.controllers.driver.DriverPendingDeliveryNavbarCounter;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

/**
 * This class prepares the driver home page view scene.
 *
 * @author azmi_maz
 */
public class DriverView implements ViewMaker {
    private static final String HOMEPAGE = "homepages/driver-homepage.fxml";
    private static final String COUNTER = "navbar/driver/" +
            "driver-pendingdeliverycounter.fxml";
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
     */
    @Override
    public Scene getScene() {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                HOMEPAGE));

        FXMLLoader fxmlLoaderNavbar = new FXMLLoader(Main.class.getResource(
                COUNTER));

        try {

            BorderPane borderPane = fxmlLoader.load();
            controller = fxmlLoader.getController();

            VBox pendingDeliveryCounterBox = fxmlLoaderNavbar.load();
            borderPane.getChildren().add(pendingDeliveryCounterBox);
            driverPendingDeliveryNavbarCounterController =
                    fxmlLoaderNavbar.getController();

            return new Scene(borderPane, WindowSize.MAIN.WIDTH,
                    WindowSize.MAIN.HEIGHT);

        } catch (IOException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }
        return null;
    }
}
