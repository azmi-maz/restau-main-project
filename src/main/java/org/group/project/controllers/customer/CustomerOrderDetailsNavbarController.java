package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.scenes.customer.stackViews.OrderDetailsController;

import java.net.URISyntaxException;

public class CustomerOrderDetailsNavbarController {

    @FXML
    private Button returnToMenuButton;

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(returnToMenuButton,
                25, 25, "undo");

        returnToMenuButton.setOnMousePressed(e -> {
            OrderDetailsController.presenter.returnToMenu();
        });

    }


}
