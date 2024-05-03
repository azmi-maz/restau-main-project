package org.group.project.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.classes.User;
import org.group.project.scenes.chef.ChefScenesMap;
import org.group.project.scenes.customer.CustomerScenesMap;
import org.group.project.scenes.driver.DriverScenesMap;
import org.group.project.scenes.main.LoginView;
import org.group.project.scenes.manager.ManagerScenesMap;
import org.group.project.scenes.waiter.WaiterScenesMap;

import java.util.HashMap;
import java.util.Map;

/**
 * This class handles all the user main scenes.
 *
 * @author azmi_maz
 */
public class MainScenesMap {

    private static Stage stage;
    private static User currentUser;

    // Holds the various scenes to switch between.
    private static Map<MainScenes, Scene> scenes = new HashMap<>();

    /**
     * The constructor that sets up the customer main scenes.
     *
     * @param stage - the main stage from Main.
     */
    public MainScenesMap(Stage stage) {

        this.stage = stage;

        // Holds the various user scenes to switch between.
        scenes.put(MainScenes.LOGIN,
                new LoginView(stage).getScene());
        scenes.put(MainScenes.CUSTOMER,
                new CustomerScenesMap(stage).getScene());
        scenes.put(MainScenes.MANAGER,
                new ManagerScenesMap(stage).getScene());
        scenes.put(MainScenes.WAITER,
                new WaiterScenesMap(stage).getScene());
        scenes.put(MainScenes.CHEF,
                new ChefScenesMap(stage).getScene());
        scenes.put(MainScenes.DRIVER,
                new DriverScenesMap(stage).getScene());

    }

    /**
     * This getter method gets the Map of main scenes.
     *
     * @return Returns a Map of the scenes.
     */
    public static Map<MainScenes, Scene> getScenes() {
        return scenes;
    }

    /**
     * This method gets the stage used by Main.
     *
     * @return the active stage.
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * This method gets the current user currently logged in.
     *
     * @return - the current user.
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * This method sets the user that successfully logged in as the active
     * user.
     *
     * @param user - the logged-in user;
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

}
