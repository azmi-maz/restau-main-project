package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.scenes.customer.stackViews.OrderDetailsController;

/**
 * This class loads up the navigation bar in customer order details view.
 *
 * @author azmi_maz
 */
public class CustomerOrderDetailsNavbarController {
    private static final String UNDO_BUTTON = "undo";
    private static final int BUTTON_WIDTH = 25;
    private static final int BUTTON_HEIGHT = 25;
    @FXML
    private Button returnToMenuButton;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        ImageLoader.setUpGraphicButton(returnToMenuButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, UNDO_BUTTON);

        returnToMenuButton.setOnMousePressed(e -> {
            OrderDetailsController.presenter.returnToMenu();
        });

    }


}
