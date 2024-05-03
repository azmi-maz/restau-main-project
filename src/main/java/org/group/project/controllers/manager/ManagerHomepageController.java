package org.group.project.controllers.manager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.group.project.Main;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.scenes.MainScenesMap;

import java.net.URISyntaxException;

/**
 * This class loads up the manager home page.
 *
 * @author azmi_maz
 */
public class ManagerHomepageController {
    private static final String BG_IMAGE = "images" +
            "/background/manager-main" +
            ".jpg";
    private static final String WELCOME_MESSAGE = "Welcome, %s!";
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
            bgImage = new Image(Main.class
                    .getResource(BG_IMAGE).toURI().toString());
        } catch (URISyntaxException e) {
            AlertPopUpWindow.displayErrorWindow(
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
                String.format(
                        WELCOME_MESSAGE,
                        MainScenesMap.getCurrentUser().getFirstNameForDisplay()
                )
        );
    }
}
