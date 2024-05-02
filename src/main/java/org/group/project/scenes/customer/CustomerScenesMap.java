package org.group.project.scenes.customer;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.scenes.customer.mainViews.BookingsView;
import org.group.project.scenes.customer.mainViews.MenuOrderView;
import org.group.project.scenes.customer.mainViews.NotificationView;
import org.group.project.scenes.customer.mainViews.OrdersView;
import org.group.project.scenes.main.CustomerView;

import java.util.HashMap;
import java.util.Map;

/**
 * This class handles all the customer main scenes.
 *
 * @author azmi_maz
 */
public class CustomerScenesMap {

    private static Stage stage;

    // Holds the various scenes to switch between.
    private static Map<CustomerMapsMain, Scene> customerScenes =
            new HashMap<>();

    /**
     * The constructor that sets up the customer main scenes.
     *
     * @param stage - the main stage from Main.
     */
    public CustomerScenesMap(Stage stage) {

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

    }

    /**
     * This method gets the Map of customer main scenes.
     *
     * @return the map of all customer scenes.
     */
    public static Map<CustomerMapsMain, Scene> getScenes() {
        return customerScenes;
    }

    /**
     * This method gets the customer home page scene.
     *
     * @return the customer home page scene.
     */
    public Scene getScene() {
        return customerScenes.get(CustomerMapsMain.HOME);
    }

    /**
     * This method gets the customer main stage.
     *
     * @return the customer main stage.
     */
    public static Stage getCustomerStage() {
        return stage;
    }

}
