package org.group.project.scenes.customer.stackViews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.group.project.Main;
import org.group.project.classes.Order;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.controllers.customer.CustomerOrderConfirmationViewController;
import org.group.project.scenes.ControllerView;
import org.group.project.scenes.WindowSize;

import java.io.IOException;
import java.util.List;

/**
 * This class enables the customer to view the order confirmation page
 * with the order data being persisted.
 *
 * @author azmi_maz
 */
public class OrderConfirmationController implements ControllerView {

    public static OrderConfirmationPresenter presenter;
    private Scene scene;

    private List<Order> newOrder;

    /**
     * This constructor sets up the order confirmation presenter.
     *
     * @param presenter - the order confirmation presenter.
     */
    public OrderConfirmationController(OrderConfirmationPresenter presenter) {
        this.presenter = presenter;

        try {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(Main.class.getResource(
                            "customerscenes/stackscenes/" +
                                    "customer-cart" +
                                    "-confirmation" +
                                    ".fxml"));

            newOrder = OrderDetailsController.newOrder;
            CustomerOrderConfirmationViewController
                    customerOrderConfirmationViewController =
                    new CustomerOrderConfirmationViewController(newOrder);
            fxmlLoader.setController(customerOrderConfirmationViewController);
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
     * This method gets the order confirmation view scene.
     *
     * @return the customer order confirmation view scene.
     */
    @Override
    public Scene getViewScene() {
        return scene;
    }
}
