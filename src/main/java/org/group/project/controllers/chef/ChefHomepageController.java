package org.group.project.controllers.chef;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.group.project.Main;
import org.group.project.classes.auxiliary.AlertPopUpWindow;

import java.net.URISyntaxException;

/**
 * This class allows the chef home page to load up.
 *
 * @author azmi_maz
 */
public class ChefHomepageController {
    private static final String BG_IMAGE = "images" +
            "/background/chef-main" +
            ".jpg";
    private static final String WELCOME = "Welcome, %s!";
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
     * This method updates the greeting message to welcome the user.
     */
    public void welcomeChef() {
        mainTitle.setText(
                String.format(
                        WELCOME,
                        Main.getCurrentUser().getFirstNameForDisplay()
                )
        );
    }
}
