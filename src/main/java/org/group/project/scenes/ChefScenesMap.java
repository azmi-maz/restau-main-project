package org.group.project.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.scenes.chef.mainViews.MenuView;
import org.group.project.scenes.chef.mainViews.OutstandingView;
import org.group.project.scenes.main.ChefView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChefScenesMap {

    private static Stage stage;

    /**
     * Holds the various scenes to switch between
     */
    private static Map<ChefMapsMain, Scene> chefScenes =
            new HashMap<>();

    public ChefScenesMap(Stage stage) throws IOException {

        ChefScenesMap.stage = stage;

        // Create and store all scenes up front
        chefScenes.put(ChefMapsMain.HOME,
                new ChefView(stage).getScene());
        chefScenes.put(ChefMapsMain.OUTSTANDING,
                new OutstandingView(stage).getScene());
        chefScenes.put(ChefMapsMain.MENU,
                new MenuView(stage).getScene());

//        stage.setScene(Main.getScenes().get(MainScenes.CUSTOMER));
//        stage.setTitle("Cafe94 Restaurant");
//        stage.show();

    }

    public static Map<ChefMapsMain, Scene> getScenes() {
        return chefScenes;
    }

    public Scene getScene() throws IOException {
        return chefScenes.get(ChefMapsMain.HOME);
    }

    public static Stage getChefStage() {
        return stage;
    }

}
