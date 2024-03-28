package org.group.project.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.scenes.customer.mainViews.BookingsView;
import org.group.project.scenes.main.CustomerView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomerScenesMap {

    /** Holds the various scenes to switch between */
    private static Map<CustomerMapsMain, Scene> customerScenes =
            new HashMap<>();

    public CustomerScenesMap(Stage stage) throws IOException {

        // Create and store all scenes up front
        customerScenes.put(CustomerMapsMain.HOME, new CustomerView(stage).getScene());
        customerScenes.put(CustomerMapsMain.MENU, new BookingsView(stage).getScene());
//        customerScenes.put(CustomerMapsMain.NOTIFICATION, new ViewOne(stage).getScene());
//        customerScenes.put(CustomerMapsMain.BOOKING, new ViewOne(stage).getScene());
//        customerScenes.put(CustomerMapsMain.ORDER, new ViewOne(stage).getScene());

        stage.setScene(customerScenes.get(CustomerMapsMain.HOME));
        stage.setTitle("Cafe94 Restaurant");
        stage.show();

    }

    public static Map<CustomerMapsMain, Scene> getScenes() {
        return customerScenes;
    }

}
