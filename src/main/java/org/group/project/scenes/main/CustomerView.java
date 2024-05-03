package org.group.project.scenes.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.controllers.customer.CustomerHomepageController;
import org.group.project.controllers.customer.CustomerNotificationNavbarCounter;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

/**
 * This class prepares the customer home page view scene.
 *
 * @author azmi_maz
 */
public class CustomerView implements ViewMaker {
    private static final String HOMEPAGE = "homepages/customer-homepage.fxml";
    private static final String COUNTER = "navbar/customer/" +
            "customer-notificationCounter.fxml";
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
     */
    @Override
    public Scene getScene() {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                HOMEPAGE));

        FXMLLoader fxmlLoaderNavbar = new FXMLLoader(Main.class.getResource(
                COUNTER));

        try {

            BorderPane borderPane = fxmlLoader.load();
            controller = fxmlLoader.getController();

            VBox notificationCounterVBox = fxmlLoaderNavbar.load();
            borderPane.getChildren().add(notificationCounterVBox);
            customerNotificationNavbarController =
                    fxmlLoaderNavbar.getController();

            return new Scene(borderPane, WindowSize.MAIN.WIDTH,
                    WindowSize.MAIN.HEIGHT);

        } catch (IOException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }
        return null;
    }
}
