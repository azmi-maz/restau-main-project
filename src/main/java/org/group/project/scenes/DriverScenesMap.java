package org.group.project.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.scenes.customer.mainViews.BookingsView;
import org.group.project.scenes.main.CustomerView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DriverScenesMap {

    private static Stage stage;

    /**
     * Holds the various scenes to switch between
     */
    private static Map<DriverMapsMain, Scene> driverScenes =
            new HashMap<>();

    public DriverScenesMap(Stage stage) throws IOException {

        DriverScenesMap.stage = stage;

        // Create and store all scenes up front
        driverScenes.put(DriverMapsMain.HOME,
                new CustomerView(stage).getScene());
        driverScenes.put(DriverMapsMain.DELIVERY,
                new BookingsView(stage).getScene());

//        stage.setScene(Main.getScenes().get(MainScenes.CUSTOMER));
//        stage.setTitle("Cafe94 Restaurant");
//        stage.show();

    }

    public static Map<DriverMapsMain, Scene> getScenes() {
        return driverScenes;
    }

    public Scene getScene() throws IOException {
        return driverScenes.get(DriverMapsMain.HOME);
    }

    public static Stage getDriverStage() {
        return stage;
    }

}
