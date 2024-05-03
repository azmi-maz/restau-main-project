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
 *
 * @author azmi_maz
 */
public class CustomerMenuOrderNavbarController {
    private static final String HOME_BUTTON = "home";
    private static final String CART_BUTTON = "cart";
    private static final String EMPTY_BASKET_TITLE = "Order Request";
    private static final String EMPTY_BASKET_MESSAGE = "Your cart is empty. " +
            "Please select at least one item.";
    private static final String OK = "Ok";
    private static final int BUTTON_WIDTH = 25;
    private static final int BUTTON_HEIGHT = 25;
    @FXML
    private Button homeButton;

    @FXML
    private Button cartButton;

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        ImageLoader.setUpGraphicButton(homeButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, HOME_BUTTON);

        homeButton.setOnMousePressed(e -> {
            CustomerScenesMap.getCustomerStage()
                    .setScene(CustomerScenesMap.getScenes()
                            .get(CustomerMapsMain.HOME));
        });

        ImageLoader.setUpGraphicButton(cartButton,
                BUTTON_WIDTH, BUTTON_HEIGHT, CART_BUTTON);

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
                EMPTY_BASKET_TITLE,
                EMPTY_BASKET_MESSAGE,
                OK
        );
    }


}
