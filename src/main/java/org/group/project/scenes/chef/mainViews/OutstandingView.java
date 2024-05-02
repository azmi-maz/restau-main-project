package org.group.project.scenes.chef.mainViews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.controllers.chef.ChefOutstandingOrdersViewController;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

/**
 * This class prepares the chef outstanding orders view scene.
 *
 * @author azmi_maz
 */
public class OutstandingView implements ViewMaker {

    public static ChefOutstandingOrdersViewController controller;
    private Stage stage;

    /**
     * This constructor sets up the stage from the main one.
     *
     * @param stage - the main stage.
     */
    public OutstandingView(Stage stage) {

        this.stage = stage;
    }

    /**
     * This method gets the chef outstanding order scene.
     *
     * @return the scene.
     */
    @Override
    public Scene getScene() {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "chefscenes/mapscenes/chef-outstandingorders.fxml"));

        VBox vbox = null;
        try {
            vbox = fxmlLoader.load();
        } catch (IOException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }

        controller = fxmlLoader.getController();

        return new Scene(vbox, WindowSize.MAIN.WIDTH,
                WindowSize.MAIN.HEIGHT);
    }
}
