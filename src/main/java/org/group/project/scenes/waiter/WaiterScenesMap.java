package org.group.project.scenes.waiter;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.scenes.main.WaiterView;
import org.group.project.scenes.waiter.mainViews.BookingView;
import org.group.project.scenes.waiter.mainViews.DeliveryView;
import org.group.project.scenes.waiter.mainViews.DineInView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class handles all the waiter main scenes.
 */
public class WaiterScenesMap {

    private static Stage stage;

    // Holds the various scenes to switch between.
    private static Map<WaiterMapsMain, Scene> waiterScenes =
            new HashMap<>();

    /**
     * The constructor that sets up the waiter main scenes.
     *
     * @param stage - the main stage from main.
     * @throws IOException // TODO
     */
    public WaiterScenesMap(Stage stage) throws IOException {

        WaiterScenesMap.stage = stage;

        // Create and store all scenes up front
        waiterScenes.put(WaiterMapsMain.HOME,
                new WaiterView(stage).getScene());
        waiterScenes.put(WaiterMapsMain.DELIVERY,
                new DeliveryView(stage).getScene());
        waiterScenes.put(WaiterMapsMain.BOOKING,
                new BookingView(stage).getScene());
        waiterScenes.put(WaiterMapsMain.DINEIN,
                new DineInView(stage).getScene());

    }

    /**
     * This method gets the Map of waiter main scenes.
     *
     * @return the map of all waiter scenes.
     */
    public static Map<WaiterMapsMain, Scene> getScenes() {
        return waiterScenes;
    }

    /**
     * This method gets the waiter home page scene.
     *
     * @return the waiter home page scene.
     * @throws IOException // TODO
     */
    public Scene getScene() throws IOException {
        return waiterScenes.get(WaiterMapsMain.HOME);
    }

    /**
     * This method gets the waiter main stage.
     *
     * @return the waiter main stage.
     */
    public static Stage getWaiterStage() {
        return stage;
    }

}
