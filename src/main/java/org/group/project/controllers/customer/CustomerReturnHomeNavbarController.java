package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.scenes.customer.CustomerMapsMain;
import org.group.project.scenes.customer.CustomerScenesMap;

import java.net.URISyntaxException;

public class CustomerReturnHomeNavbarController {

    @FXML
    private Button returnButton;

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(returnButton, 25, 25, "undo");

        returnButton.setOnMousePressed(e -> {
            CustomerScenesMap.getCustomerStage().setScene(CustomerScenesMap.getScenes().get(CustomerMapsMain.HOME));
        });

    }


}
