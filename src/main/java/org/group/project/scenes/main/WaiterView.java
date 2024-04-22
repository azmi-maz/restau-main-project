package org.group.project.scenes.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.controllers.waiter.WaiterHomepageController;
import org.group.project.controllers.waiter.WaiterMainNavbarCounter;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

public class WaiterView implements ViewMaker {

    private Stage stage;

    public static WaiterHomepageController controller;

    public static WaiterMainNavbarCounter waiterMainCounterController;

    public WaiterView(Stage stage) throws IOException {

        this.stage = stage;

    }

    @Override
    public Scene getScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "homepages/waiter-homepage.fxml"));

        FXMLLoader fxmlLoaderNavbar = new FXMLLoader(Main.class.getResource(
                "navbar/waiter/waiter-pendingmaincounter.fxml"));

        BorderPane borderPane = fxmlLoader.load();
        controller = fxmlLoader.getController();

        VBox mainCounterBox = fxmlLoaderNavbar.load();
        Label newLabel = new Label("Hello");
        borderPane.getChildren().add(mainCounterBox);
        borderPane.getChildren().add(newLabel);
        waiterMainCounterController = fxmlLoaderNavbar.getController();

        return new Scene(borderPane, WindowSize.MAIN.WIDTH,
                WindowSize.MAIN.HEIGHT);
    }

}
