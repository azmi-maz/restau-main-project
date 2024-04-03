package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.ImageLoader;
import org.group.project.scenes.CustomerMapsMain;
import org.group.project.scenes.CustomerScenesMap;

import java.net.URISyntaxException;

public class CustomerTableReservationNavbarController {

    @FXML
    private Button returnButton;

    @FXML
    private Button newReservationButton;

    public void initialize() throws URISyntaxException {

        ImageLoader.setUpGraphicButton(returnButton, 25, 25, "undo");

        returnButton.setOnMousePressed(e -> {
            CustomerScenesMap.getCustomerStage().setScene(CustomerScenesMap.getScenes().get(CustomerMapsMain.HOME));
        });

        ImageLoader.setUpGraphicButton(newReservationButton, 25, 25, "edit");

        newReservationButton.setOnMousePressed(e -> {
            // TODO open new form
        });

    }


}