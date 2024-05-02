package org.group.project.scenes.driver.mainViews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.controllers.driver.DriverPendingDeliveryViewController;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

/**
 * This class prepares the driver pending delivery order view scene.
 *
 * @author azmi_maz
 */
public class DeliveryView implements ViewMaker {

    public static DriverPendingDeliveryViewController controller;
    private Stage stage;

    /**
     * This constructor sets up the stage from the main one.
     *
     * @param stage - the main stage.
     */
    public DeliveryView(Stage stage) {

        this.stage = stage;
    }

    /**
     * This method gets the driver pending delivery order view scene.
     *
     * @return the driver pending delivery order view scene.
     */
    @Override
    public Scene getScene() {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "driverscenes/mapscenes/" +
                        "driver-pendingdeliveries.fxml"));

        VBox vbox = null;
        try {
            vbox = fxmlLoader.load();
        } catch (IOException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }

        controller = fxmlLoader.getController();

        return new Scene(vbox, WindowSize.MAIN.WIDTH,
                WindowSize.MAIN.HEIGHT);
    }
}
