package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.scenes.customer.stackViews.OrderDetailsController;

import java.net.URISyntaxException;

/**
 * This class loads up the navigation bar in customer order details view.
 */
public class CustomerOrderDetailsNavbarController {

    @FXML
    private Button returnToMenuButton;

    /**
     * This initializes the controller for the fxml.
     * @throws URISyntaxException // TODO
     */
    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(returnToMenuButton,
                25, 25, "undo");

        returnToMenuButton.setOnMousePressed(e -> {
            OrderDetailsController.presenter.returnToMenu();
        });

    }


}
