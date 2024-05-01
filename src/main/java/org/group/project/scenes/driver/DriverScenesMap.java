package org.group.project.scenes.driver;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.scenes.driver.mainViews.DeliveryView;
import org.group.project.scenes.main.DriverView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class handles all the driver main scenes.
 */
public class DriverScenesMap {

    private static Stage stage;

    // Holds the various scenes to switch between.
    private static Map<DriverMapsMain, Scene> driverScenes =
            new HashMap<>();

    /**
     * The constructor that sets up the driver main scenes.
     *
     * @param stage - the main stage from main.
     * @throws IOException // TODO
     */
    public DriverScenesMap(Stage stage) throws IOException {

        DriverScenesMap.stage = stage;

        // Create and store all scenes up front
        driverScenes.put(DriverMapsMain.HOME,
                new DriverView(stage).getScene());
        driverScenes.put(DriverMapsMain.DELIVERY,
                new DeliveryView(stage).getScene());

    }

    /**
     * This method gets the Map of customer main scenes.
     *
     * @return the map of all customer scenes.
     */
    public static Map<DriverMapsMain, Scene> getScenes() {
        return driverScenes;
    }

    /**
     * This method gets the driver home page scene.
     *
     * @return the driver home page scene.
     * @throws IOException // TODO
     */
    public Scene getScene() throws IOException {
        return driverScenes.get(DriverMapsMain.HOME);
    }

    /**
     * This method gets the driver main stage.
     *
     * @return the driver main stage.
     */
    public static Stage getDriverStage() {
        return stage;
    }

}
