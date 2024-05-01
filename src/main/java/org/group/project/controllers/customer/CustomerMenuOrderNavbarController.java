package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.classes.auxiliary.ImageLoader;
import org.group.project.scenes.customer.CustomerMapsMain;
import org.group.project.scenes.customer.CustomerScenesMap;
import org.group.project.scenes.customer.stackViews.MenuController;

/**
 * This class loads up the customer navigation bar in menu page.
 */
public class CustomerMenuOrderNavbarController {

    @FXML
    private Button homeButton;

    @FXML
    private Button cartButton;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        ImageLoader.setUpGraphicButton(homeButton, 25, 25, "home");

        homeButton.setOnMousePressed(e -> {
            CustomerScenesMap.getCustomerStage().setScene(CustomerScenesMap.getScenes().get(CustomerMapsMain.HOME));
        });

        ImageLoader.setUpGraphicButton(cartButton, 25, 25, "cart");

        cartButton.setOnMousePressed(e -> {
            if (MenuController.orderList.isEmpty()) {
                promptOrderlistEmpty();
            } else {
                MenuController.presenter.goToCart();
            }
        });

    }

    // To inform the user that there are no items in the cart.
    private void promptOrderlistEmpty() {
        AlertPopUpWindow.displayInformationWindow(
                "Order Request",
                "Your cart is empty. Please select at least one item.",
                "Ok"
        );
    }


}
