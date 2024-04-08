package org.group.project.scenes.waiter.mainViews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.controllers.waiter.WaiterApproveDeliveryViewController;
import org.group.project.mapscenes.view.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

public class DeliveryView implements ViewMaker {

    private Stage stage;

    public static WaiterApproveDeliveryViewController controller;

    public DeliveryView(Stage stage) {

        this.stage = stage;
    }

    @Override
    public Scene getScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "waiterscenes/mapscenes/waiter-approvedeliveries.fxml"));

        VBox vbox = fxmlLoader.load();

        controller = fxmlLoader.getController();

        return new Scene(vbox, WindowSize.MAIN.WIDTH,
                WindowSize.MAIN.HEIGHT);
    }
}
