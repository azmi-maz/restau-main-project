package org.group.project.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.scenes.customer.mainViews.BookingsView;
import org.group.project.scenes.customer.mainViews.MenuOrderView;
import org.group.project.scenes.customer.mainViews.NotificationView;
import org.group.project.scenes.customer.mainViews.OrdersView;
import org.group.project.scenes.main.CustomerView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomerScenesMap {

    private static Stage stage;

    /** Holds the various scenes to switch between */
    private static Map<CustomerMapsMain, Scene> customerScenes =
            new HashMap<>();

    public CustomerScenesMap(Stage stage) throws IOException {

        CustomerScenesMap.stage = stage;

        // Create and store all scenes up front
        customerScenes.put(CustomerMapsMain.HOME,
                new CustomerView(stage).getScene());
        customerScenes.put(CustomerMapsMain.BOOKING,
                new BookingsView(stage).getScene());
        customerScenes.put(CustomerMapsMain.MENU,
                new MenuOrderView(stage).getScene());
        customerScenes.put(CustomerMapsMain.ORDER,
                new OrdersView(stage).getScene());
        customerScenes.put(CustomerMapsMain.NOTIFICATION,
                new NotificationView(stage).getScene());

        // TODO help scene and setting scene

//        stage.setScene(Main.getScenes().get(MainScenes.CUSTOMER));
//        stage.setTitle("Cafe94 Restaurant");
//        stage.show();

    }

    public static Map<CustomerMapsMain, Scene> getScenes() {
        return customerScenes;
    }

    public Scene getScene() throws IOException {
        return customerScenes.get(CustomerMapsMain.HOME);
    }

    public static Stage getCustomerStage() {
        return stage;
    }

}
