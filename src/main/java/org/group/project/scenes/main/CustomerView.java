package org.group.project.scenes.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.controllers.customer.CustomerHomepageController;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

public class CustomerView implements ViewMaker {

    private Stage stage;

    public static CustomerHomepageController controller;

    public CustomerView(Stage stage) throws IOException {

        this.stage = stage;

    }

    @Override
    public Scene getScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "homepages/customer-homepage.fxml"));

        BorderPane borderPane = fxmlLoader.load();

        controller = fxmlLoader.getController();

        return new Scene(borderPane, WindowSize.MAIN.WIDTH,
                WindowSize.MAIN.HEIGHT);
    }

}
