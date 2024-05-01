package org.group.project.scenes.chef;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.scenes.chef.mainViews.MenuView;
import org.group.project.scenes.chef.mainViews.OutstandingView;
import org.group.project.scenes.main.ChefView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class handles all the chef main scenes.
 */
public class ChefScenesMap {

    private static Stage stage;

    // Holds the various scenes to switch between.
    private static Map<ChefMapsMain, Scene> chefScenes =
            new HashMap<>();

    /**
     * The constructor that sets up the chef main scenes.
     *
     * @param stage - the main stage from Main.
     * @throws IOException // TODO
     */
    public ChefScenesMap(Stage stage) throws IOException {

        ChefScenesMap.stage = stage;

        // Create and store all scenes up front
        chefScenes.put(ChefMapsMain.HOME,
                new ChefView(stage).getScene());
        chefScenes.put(ChefMapsMain.OUTSTANDING,
                new OutstandingView(stage).getScene());
        chefScenes.put(ChefMapsMain.MENU,
                new MenuView(stage).getScene());

    }

    /**
     * This method gets the Map of all chef scenes.
     *
     * @return the map of all chef scenes.
     */
    public static Map<ChefMapsMain, Scene> getScenes() {
        return chefScenes;
    }

    /**
     * This method gets the scene of chef home page.
     *
     * @return - the chef home page.
     * @throws IOException // TODO
     */
    public Scene getScene() throws IOException {
        return chefScenes.get(ChefMapsMain.HOME);
    }

    /**
     * This method gets the chef main stage.s
     *
     * @return the chef main stage.
     */
    public static Stage getChefStage() {
        return stage;
    }

}
