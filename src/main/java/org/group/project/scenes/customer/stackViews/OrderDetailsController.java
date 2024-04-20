package org.group.project.scenes.customer.stackViews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.group.project.Main;
import org.group.project.classes.FoodDrink;
import org.group.project.classes.Order;
import org.group.project.controllers.customer.CustomerOrderDetailsViewController;
import org.group.project.scenes.ControllerView;
import org.group.project.scenes.WindowSize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsController implements ControllerView {

    private Scene scene;

    public static OrderDetailsPresenter presenter;
    public static List<Order> newOrder = new ArrayList<>();

    private List<FoodDrink> orderList;

    public OrderDetailsController(OrderDetailsPresenter presenter) {
        this.presenter = presenter;

        try {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(Main.class.getResource(
                            "customerscenes/stackscenes/customer-cart-order" +
                                    "-details" +
                                    ".fxml"));
//            fxmlLoader.setController(this);
            orderList = MenuController.orderList;
            CustomerOrderDetailsViewController customerOrderDetailsViewController =
                    new CustomerOrderDetailsViewController(orderList, newOrder);
            fxmlLoader.setController(customerOrderDetailsViewController);
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
