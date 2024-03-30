package org.group.project.controller.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.ImageLoader;
import org.group.project.scenes.CustomerMapsMain;
import org.group.project.scenes.CustomerScenesMap;

import java.net.URISyntaxException;

public class CustomerBookingsHistoryNavbarController {

    @FXML
    private Button returnButton;

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(returnButton, 25, 25, "undo");

        returnButton.setOnMousePressed(e -> {
            CustomerScenesMap.getCustomerStage().setScene(CustomerScenesMap.getScenes().get(CustomerMapsMain.HOME));
        });

    }


}
