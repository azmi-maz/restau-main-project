package org.group.project.scenes.waiter.mainViews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.controllers.waiter.WaiterApproveBookingsViewController;
import org.group.project.controllers.waiter.WaiterDeliveryNavbarCounter;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

public class BookingView implements ViewMaker {

    private Stage stage;

    public static WaiterApproveBookingsViewController controller;

    public static WaiterDeliveryNavbarCounter waiterDeliveryCounterController;

    public BookingView(Stage stage) {

        this.stage = stage;
    }

    @Override
    public Scene getScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "waiterscenes/mapscenes/waiter-approvebookings.fxml"));

        FXMLLoader fxmlLoaderNavbar = new FXMLLoader(Main.class.getResource(
                "navbar/waiter/waiter-pendingdeliverycounter.fxml"));

        BorderPane borderPane = fxmlLoader.load();
        controller = fxmlLoader.getController();

        VBox deliveryCounterBox = fxmlLoaderNavbar.load();
        borderPane.getChildren().add(deliveryCounterBox);
        waiterDeliveryCounterController = fxmlLoaderNavbar.getController();

        return new Scene(borderPane, WindowSize.MAIN.WIDTH,
                WindowSize.MAIN.HEIGHT);
    }
}
