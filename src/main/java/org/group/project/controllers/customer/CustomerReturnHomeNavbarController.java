package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.scenes.customer.CustomerMapsMain;
import org.group.project.scenes.customer.CustomerScenesMap;
import org.group.project.scenes.main.CustomerView;

/**
 * This class loads up the customer return to home page navigation bar.
 *
 * @author azmi_maz
 */
public class CustomerReturnHomeNavbarController {
    private static final String UNDO_BUTTON = "undo";
    private static final int BUTTON_WIDTH = 25;
    private static final int BUTTON_HEIGHT = 25;
    @FXML
    private Button returnButton;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        ImageLoader.setUpGraphicButton(returnButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, UNDO_BUTTON);

        returnButton.setOnMousePressed(e -> {
            CustomerView.customerNotificationNavbarController
                    .refreshNotificationCounter();
            CustomerScenesMap.getCustomerStage().setScene(
                    CustomerScenesMap.getScenes()
                            .get(CustomerMapsMain.HOME));
        });

    }


}
