package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.scenes.customer.stackViews.OrderConfirmationController;

/**
 * This class loads up the customer confirmation order navigation bar.
 *
 * @author azmi_maz
 */
public class CustomerOrderConfirmationNavbarController {
    private static final String UNDO_BUTTON = "undo";
    private static final int BUTTON_WIDTH = 25;
    private static final int BUTTON_HEIGHT = 25;
    @FXML
    private Button returnButton;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        ImageLoader.setUpGraphicButton(returnButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, UNDO_BUTTON);

        returnButton.setOnMousePressed(e -> {
            OrderConfirmationController.presenter.returnToOrderDetails();
        });

    }


}
