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

    @FXML
    private Button returnToMenuButton;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        ImageLoader.setUpGraphicButton(returnToMenuButton,
                25, 25, "undo");

        returnToMenuButton.setOnMousePressed(e -> {
            OrderDetailsController.presenter.returnToMenu();
        });

    }


}
