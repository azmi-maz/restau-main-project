package org.group.project.scenes.manager;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.scenes.main.ManagerView;
import org.group.project.scenes.manager.mainViews.ManagementView;
import org.group.project.scenes.manager.mainViews.ReportsView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ManagerScenesMap {

    private static Stage stage;

    // Holds the various scenes to switch between
    private static Map<ManagerMapsMain, Scene> managerScenes =
            new HashMap<>();

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

    public static Map<ManagerMapsMain, Scene> getScenes() {
        return managerScenes;
    }

    public Scene getScene() throws IOException {
        return managerScenes.get(ManagerMapsMain.HOME);
    }

    public static Stage getManagerStage() {
        return stage;
    }

}
