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

public class DriverView implements ViewMaker {

    private Stage stage;

    public static DriverHomepageController controller;

    public static DriverPendingDeliveryNavbarCounter driverPendingDeliveryNavbarCounterController;

    public DriverView(Stage stage) throws IOException {

        this.stage = stage;

    }

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
        driverPendingDeliveryNavbarCounterController = fxmlLoaderNavbar.getController();

        return new Scene(borderPane, WindowSize.MAIN.WIDTH,
                WindowSize.MAIN.HEIGHT);
    }

}
