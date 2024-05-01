package org.group.project.scenes.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.controllers.manager.ManagerHomepageController;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

/**
 * This class prepares the manager home page view scene.
 */
public class ManagerView implements ViewMaker {

    public static ManagerHomepageController controller;
    private Stage stage;

    /**
     * This constructor sets up the stage from the main one.
     *
     * @param stage - the main stage.
     */
    public ManagerView(Stage stage) {

        this.stage = stage;

    }

    /**
     * This method gets the manager home page view scene.
     *
     * @return the manager home page view scene.
     * @throws IOException // TODO
     */
    @Override
    public Scene getScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "homepages/manager-homepage.fxml"));

        BorderPane borderPane = fxmlLoader.load();

        controller = fxmlLoader.getController();

        return new Scene(borderPane, WindowSize.MAIN.WIDTH,
                WindowSize.MAIN.HEIGHT);
    }

}
