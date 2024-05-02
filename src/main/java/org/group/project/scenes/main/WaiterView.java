package org.group.project.scenes.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.controllers.waiter.WaiterHomepageController;
import org.group.project.controllers.waiter.WaiterMainNavbarCounter;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

/**
 * This class prepares the waiter home page view scene.
 *
 * @author azmi_maz
 */
public class WaiterView implements ViewMaker {

    public static WaiterHomepageController controller;
    public static WaiterMainNavbarCounter waiterMainCounterController;
    private Stage stage;

    /**
     * This constructor sets up the stage from the main one.
     *
     * @param stage - the main stage.
     */
    public WaiterView(Stage stage) {

        this.stage = stage;

    }

    /**
     * This method gets the waiter home page view scene.
     *
     * @return the waiter home page view scene.
     */
    @Override
    public Scene getScene() {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "homepages/waiter-homepage.fxml"));

        FXMLLoader fxmlLoaderNavbar = new FXMLLoader(Main.class.getResource(
                "navbar/waiter/waiter-pendingmaincounter.fxml"));

        try {

            BorderPane borderPane = fxmlLoader.load();
            controller = fxmlLoader.getController();

            VBox mainCounterBox = fxmlLoaderNavbar.load();
            Label newLabel = new Label("Hello");
            borderPane.getChildren().add(mainCounterBox);
            borderPane.getChildren().add(newLabel);
            waiterMainCounterController = fxmlLoaderNavbar.getController();

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
