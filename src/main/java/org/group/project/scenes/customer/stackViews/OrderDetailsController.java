package org.group.project.scenes.customer.stackViews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.group.project.Main;
import org.group.project.classes.FoodDrink;
import org.group.project.classes.Order;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.controllers.customer.CustomerOrderDetailsViewController;
import org.group.project.scenes.ControllerView;
import org.group.project.scenes.WindowSize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class enables the customer to view the order details page
 * with the order data being persisted.
 *
 * @author azmi_maz
 */
public class OrderDetailsController implements ControllerView {

    public static OrderDetailsPresenter presenter;
    public static List<Order> newOrder = new ArrayList<>();
    private Scene scene;
    private List<FoodDrink> orderList;

    /**
     * This constructor sets up the order details presenter.
     *
     * @param presenter - the order details presenter.
     */
    public OrderDetailsController(OrderDetailsPresenter presenter) {
        this.presenter = presenter;

        try {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(Main.class.getResource(
                            "customerscenes/stackscenes/" +
                                    "customer-cart-order" +
                                    "-details" +
                                    ".fxml"));

            orderList = MenuController.orderList;
            CustomerOrderDetailsViewController
                    customerOrderDetailsViewController =
                    new CustomerOrderDetailsViewController(
                            orderList, newOrder);
            fxmlLoader.setController(customerOrderDetailsViewController);
            scene = new Scene(fxmlLoader.load(), WindowSize.MAIN.WIDTH,
                    WindowSize.MAIN.HEIGHT);
        } catch (IOException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }
    }

    /**
     * This method gets the order details view scene.
     *
     * @return the customer order details view scene.
     */
    @Override
    public Scene getViewScene() {
        return scene;
    }
}
