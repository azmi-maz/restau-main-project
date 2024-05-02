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

    @FXML
    private Button returnButton;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        ImageLoader.setUpGraphicButton(returnButton, 25,
                25, "undo");

        returnButton.setOnMousePressed(e -> {
            OrderConfirmationController.presenter.returnToOrderDetails();
        });

    }


}
