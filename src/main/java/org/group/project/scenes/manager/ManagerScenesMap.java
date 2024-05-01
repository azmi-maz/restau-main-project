package org.group.project.scenes.manager;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.scenes.main.ManagerView;
import org.group.project.scenes.manager.mainViews.ManagementView;
import org.group.project.scenes.manager.mainViews.ReportsView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class handles all the manager main scenes.
 */
public class ManagerScenesMap {

    private static Stage stage;

    // Holds the various scenes to switch between.
    private static Map<ManagerMapsMain, Scene> managerScenes =
            new HashMap<>();

    /**
     * The constructor that sets up the manager main scenes.
     *
     * @param stage - the main stage from main.
     * @throws IOException // TODO
     */
    public ManagerScenesMap(Stage stage) throws IOException {

        ManagerScenesMap.stage = stage;

        // Create and store all scenes up front
        managerScenes.put(ManagerMapsMain.HOME,
                new ManagerView(stage).getScene());
        managerScenes.put(ManagerMapsMain.MANAGEMENT,
                new ManagementView(stage).getScene());
        managerScenes.put(ManagerMapsMain.REPORT,
                new ReportsView(stage).getScene());

    }

    /**
     * This method gets the Map of manager main scenes.
     *
     * @return the map of all manager scenes.
     */
    public static Map<ManagerMapsMain, Scene> getScenes() {
        return managerScenes;
    }

    /**
     * This method gets the manager home page scene.
     *
     * @return the manager home page scene.
     * @throws IOException // TODO
     */
    public Scene getScene() throws IOException {
        return managerScenes.get(ManagerMapsMain.HOME);
    }

    /**
     * This method gets the manager main stage.
     *
     * @return the manager main stage.
     */
    public static Stage getManagerStage() {
        return stage;
    }

}
