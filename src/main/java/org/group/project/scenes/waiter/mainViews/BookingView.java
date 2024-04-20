package org.group.project.scenes.waiter.mainViews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.controllers.waiter.WaiterApproveBookingsViewController;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

public class BookingView implements ViewMaker {

    private Stage stage;

    public static WaiterApproveBookingsViewController controller;

    public BookingView(Stage stage) {

        this.stage = stage;
    }

    @Override
    public Scene getScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "waiterscenes/mapscenes/waiter-approvebookings.fxml"));

        VBox vbox = fxmlLoader.load();

        controller = fxmlLoader.getController();

        return new Scene(vbox, WindowSize.MAIN.WIDTH,
                WindowSize.MAIN.HEIGHT);
    }
}
