package org.group.project.scenes.manager.mainViews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

/**
 * This class prepares the manager report view scene.
 *
 * @author azmi_maz
 */
public class ReportsView implements ViewMaker {

    private Stage stage;

    /**
     * This constructor sets up the stage from the main one.
     *
     * @param stage - the main stage.
     */
    public ReportsView(Stage stage) {

        this.stage = stage;
    }

    /**
     * This method gets the manager report view scene.
     *
     * @return the manager report view scene.
     */
    @Override
    public Scene getScene() {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "managerscenes/mapscenes/manager-viewreports.fxml"));

        try {

            return new Scene(fxmlLoader.load(), WindowSize.MAIN.WIDTH,
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
