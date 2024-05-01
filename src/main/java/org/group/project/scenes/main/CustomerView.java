package org.group.project.scenes.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.controllers.customer.CustomerHomepageController;
import org.group.project.controllers.customer.CustomerNotificationNavbarCounter;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

/**
 * This class prepares the customer home page view scene.
 */
public class CustomerView implements ViewMaker {

    public static CustomerHomepageController controller;
    public static CustomerNotificationNavbarCounter
            customerNotificationNavbarController;
    private Stage stage;

    /**
     * This constructor sets up the stage from the main one.
     *
     * @param stage - the main stage.
     */
    public CustomerView(Stage stage) {

        this.stage = stage;

    }

    /**
     * This method gets the customer home page view scene.
     *
     * @return the customer home page view scene.
     * @throws IOException // TODO
     */
    @Override
    public Scene getScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "homepages/customer-homepage.fxml"));

        FXMLLoader fxmlLoaderNavbar = new FXMLLoader(Main.class.getResource(
                "navbar/customer/customer-notificationCounter.fxml"));

        BorderPane borderPane = fxmlLoader.load();
        controller = fxmlLoader.getController();

        VBox notificationCounterVBox = fxmlLoaderNavbar.load();
        borderPane.getChildren().add(notificationCounterVBox);
        customerNotificationNavbarController =
                fxmlLoaderNavbar.getController();

        return new Scene(borderPane, WindowSize.MAIN.WIDTH,
                WindowSize.MAIN.HEIGHT);
    }

}
