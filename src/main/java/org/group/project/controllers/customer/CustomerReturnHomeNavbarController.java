package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.scenes.customer.CustomerMapsMain;
import org.group.project.scenes.customer.CustomerScenesMap;
import org.group.project.scenes.main.CustomerView;

/**
 * This class loads up the customer return to home page navigation bar.
 */
public class CustomerReturnHomeNavbarController {

    @FXML
    private Button returnButton;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        ImageLoader.setUpGraphicButton(returnButton,
                25, 25, "undo");

        returnButton.setOnMousePressed(e -> {
            CustomerView.customerNotificationNavbarController
                    .refreshNotificationCounter();
            CustomerScenesMap.getCustomerStage().setScene(
                    CustomerScenesMap.getScenes()
                            .get(CustomerMapsMain.HOME));
        });

    }


}
