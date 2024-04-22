package org.group.project.scenes.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.controllers.chef.ChefHomepageController;
import org.group.project.controllers.chef.ChefOutstandingOrdersNavbarCounter;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

public class ChefView implements ViewMaker {

    private Stage stage;

    public static ChefHomepageController controller;

    public static ChefOutstandingOrdersNavbarCounter chefOutstandingOrdersNavbarCounterController;

    public ChefView(Stage stage) throws IOException {

        this.stage = stage;

    }

    @Override
    public Scene getScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "homepages/chef-homepage.fxml"));

        FXMLLoader fxmlLoaderNavbar = new FXMLLoader(Main.class.getResource(
                "navbar/chef/chef-outstandingorderscounter.fxml"));

        BorderPane borderPane = fxmlLoader.load();
        controller = fxmlLoader.getController();

        VBox outstandingCounterBox = fxmlLoaderNavbar.load();
        borderPane.getChildren().add(outstandingCounterBox);
        chefOutstandingOrdersNavbarCounterController = fxmlLoaderNavbar.getController();

        return new Scene(borderPane, WindowSize.MAIN.WIDTH,
                WindowSize.MAIN.HEIGHT);
    }

}
