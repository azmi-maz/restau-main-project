package org.group.project.scenes.customer.stackViews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.group.project.Main;
import org.group.project.classes.Order;
import org.group.project.controllers.customer.CustomerOrderConfirmationViewController;
import org.group.project.scenes.WindowSize;
import org.group.project.stackscenes.view.ControllerView;

import java.io.IOException;
import java.util.List;

public class OrderConfirmationController implements ControllerView {

    private Scene scene;

    public static OrderConfirmationPresenter presenter;

    private List<Order> newOrder;

    public OrderConfirmationController(OrderConfirmationPresenter presenter) {
        this.presenter = presenter;

        try {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(Main.class.getResource(
                            "customerscenes/stackscenes/customer-cart" +
                                    "-confirmation" +
                                    ".fxml"));

            newOrder = OrderDetailsController.newOrder;
            CustomerOrderConfirmationViewController customerOrderConfirmationViewController =
                    new CustomerOrderConfirmationViewController(newOrder);
            fxmlLoader.setController(customerOrderConfirmationViewController);
            scene = new Scene(fxmlLoader.load(), WindowSize.MAIN.WIDTH,
                    WindowSize.MAIN.HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public Scene getViewScene() {
        return scene;
    }
}
