package org.group.project.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.scenes.main.WaiterView;
import org.group.project.scenes.waiter.mainViews.BookingView;
import org.group.project.scenes.waiter.mainViews.DeliveryView;
import org.group.project.scenes.waiter.mainViews.DineInView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WaiterScenesMap {

    private static Stage stage;

    /**
     * Holds the various scenes to switch between
     */
    private static Map<WaiterMapsMain, Scene> waiterScenes =
            new HashMap<>();

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

//        stage.setScene(Main.getScenes().get(MainScenes.CUSTOMER));
//        stage.setTitle("Cafe94 Restaurant");
//        stage.show();

    }

    public static Map<WaiterMapsMain, Scene> getScenes() {
        return waiterScenes;
    }

    public Scene getScene() throws IOException {
        return waiterScenes.get(WaiterMapsMain.HOME);
    }

    public static Stage getWaiterStage() {
        return stage;
    }

}
