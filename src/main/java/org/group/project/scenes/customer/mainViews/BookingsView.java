package org.group.project.scenes.customer.mainViews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.controllers.customer.CustomerBookingsHistoryViewController;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

public class BookingsView implements ViewMaker {

    private Stage stage;

    public static CustomerBookingsHistoryViewController controller;

    public BookingsView(Stage stage) {

        this.stage = stage;
    }

    @Override
    public Scene getScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "customerscenes/mapscenes/customer-viewbookings.fxml"));

        VBox vbox = fxmlLoader.load();

        controller = fxmlLoader.getController();

        return new Scene(vbox, WindowSize.MAIN.WIDTH,
                WindowSize.MAIN.HEIGHT);
    }
}
