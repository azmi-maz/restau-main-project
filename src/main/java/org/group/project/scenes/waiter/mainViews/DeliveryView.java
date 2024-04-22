package org.group.project.scenes.waiter.mainViews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.controllers.waiter.WaiterApproveDeliveryViewController;
import org.group.project.controllers.waiter.WaiterBookingNavbarCounter;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

public class DeliveryView implements ViewMaker {

    private Stage stage;

    public static WaiterApproveDeliveryViewController controller;

    public static WaiterBookingNavbarCounter waiterBookingCounterController;

    public DeliveryView(Stage stage) {

        this.stage = stage;
    }

    @Override
    public Scene getScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "waiterscenes/mapscenes/waiter-approvedeliveries.fxml"));


        FXMLLoader fxmlLoaderNavbar = new FXMLLoader(Main.class.getResource(
                "navbar/waiter/waiter-pendingtablecounter.fxml"));

        BorderPane borderPane = fxmlLoader.load();
        controller = fxmlLoader.getController();

        VBox bookingCounterBox = fxmlLoaderNavbar.load();
        borderPane.getChildren().add(bookingCounterBox);
        waiterBookingCounterController = fxmlLoaderNavbar.getController();

        return new Scene(borderPane, WindowSize.MAIN.WIDTH,
                WindowSize.MAIN.HEIGHT);
    }
}
