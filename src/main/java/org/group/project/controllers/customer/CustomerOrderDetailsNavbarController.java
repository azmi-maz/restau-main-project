package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.ImageLoader;
import org.group.project.scenes.customer.stackViews.OrderDetailsController;

import java.net.URISyntaxException;

public class CustomerOrderDetailsNavbarController {

    @FXML
    private Button returnToMenuButton;

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(returnToMenuButton, 25, 25, "undo");

        returnToMenuButton.setOnMousePressed(e -> {
            System.out.println("Return to menu");
            OrderDetailsController.presenter.returnToFirstScene();
//            CustomerScenesMap.getCustomerStage().setScene(CustomerScenesMap.getScenes().get(CustomerMapsMain.MENU));

        });

    }


}
