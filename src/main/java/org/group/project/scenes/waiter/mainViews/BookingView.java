package org.group.project.scenes.waiter.mainViews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.controllers.waiter.WaiterApproveBookingsViewController;
import org.group.project.controllers.waiter.WaiterDeliveryNavbarCounter;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

/**
 * This class prepares the waiter pending bookings view scene.
 *
 * @author azmi_maz
 */
public class BookingView implements ViewMaker {

    public static WaiterApproveBookingsViewController controller;
    public static WaiterDeliveryNavbarCounter waiterDeliveryCounterController;
    private Stage stage;

    /**
     * This constructor sets up the stage from the main one.
     *
     * @param stage - the main stage.
     */
    public BookingView(Stage stage) {

        this.stage = stage;
    }

    /**
     * This method gets the waiter pending bookings view scene.
     *
     * @return the waiter pending bookings view scene.
     */
    @Override
    public Scene getScene() {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "waiterscenes/mapscenes/waiter-approvebookings.fxml"));

        FXMLLoader fxmlLoaderNavbar = new FXMLLoader(Main.class.getResource(
                "navbar/waiter/waiter-pendingdeliverycounter.fxml"));

        try {

            BorderPane borderPane = fxmlLoader.load();
            controller = fxmlLoader.getController();

            VBox deliveryCounterBox = fxmlLoaderNavbar.load();
            borderPane.getChildren().add(deliveryCounterBox);
            waiterDeliveryCounterController = fxmlLoaderNavbar.getController();

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
