package org.group.project.controllers.manager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.group.project.Main;
import org.group.project.classes.auxiliary.AlertPopUpWindow;

import java.net.URISyntaxException;

/**
 * This class loads up the manager home page.
 */
public class ManagerHomepageController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label mainTitle;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        Image bgImage = null;
        try {
            bgImage = new Image(Main.class.getResource("images" +
                    "/background/manager-main" +
                    ".jpg").toURI().toString());
        } catch (URISyntaxException e) {
            AlertPopUpWindow.displayErrorWindow(
                    "Error",
                    e.getMessage()
            );
            e.printStackTrace();
        }

        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO,
                BackgroundSize.AUTO, false,
                false, true, true);

        borderPane.setBackground(new Background(new BackgroundImage(bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));

    }

    /**
     * This method changes the greeting message to welcome the manager.
     */
    public void welcomeManager() {
        mainTitle.setText(
                "Welcome, "
                        + Main.getCurrentUser().getFirstNameForDisplay()
                        + "!"
        );
    }
}
