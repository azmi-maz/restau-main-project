package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.scenes.customer.stackViews.OrderConfirmationController;

import java.net.URISyntaxException;

public class CustomerOrderConfirmationNavbarController {

    @FXML
    private Button returnButton;

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(returnButton, 25,
                25, "undo");

        returnButton.setOnMousePressed(e -> {
            OrderConfirmationController.presenter.returnToOrderDetails();
        });

    }


}
