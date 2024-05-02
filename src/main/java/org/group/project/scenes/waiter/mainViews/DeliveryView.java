package org.group.project.scenes.waiter.mainViews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.controllers.waiter.WaiterApproveDeliveryViewController;
import org.group.project.controllers.waiter.WaiterBookingNavbarCounter;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

/**
 * This class prepares the waiter pending delivery orders view scene.
 *
 * @author azmi_maz
 */
public class DeliveryView implements ViewMaker {

    public static WaiterApproveDeliveryViewController controller;
    public static WaiterBookingNavbarCounter waiterBookingCounterController;
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
     * This method gets the waiter pending delivery orders view scene.
     *
     * @return the waiter pending delivery orders view scene.
     */
    @Override
    public Scene getScene() {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "waiterscenes/mapscenes/waiter-approvedeliveries.fxml"));


        FXMLLoader fxmlLoaderNavbar = new FXMLLoader(Main.class.getResource(
                "navbar/waiter/waiter-pendingtablecounter.fxml"));

        try {

            BorderPane borderPane = fxmlLoader.load();
            controller = fxmlLoader.getController();

            VBox bookingCounterBox = fxmlLoaderNavbar.load();
            borderPane.getChildren().add(bookingCounterBox);
            waiterBookingCounterController = fxmlLoaderNavbar.getController();

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
