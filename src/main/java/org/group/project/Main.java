package org.group.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.scenes.CustomerScenesMap;
import org.group.project.scenes.MainScenes;
import org.group.project.scenes.WindowSize;
import org.group.project.scenes.main.LoginView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    /**
     * Holds the various scenes to switch between
     */
    private static Map<MainScenes, Scene> scenes = new HashMap<>();

    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {

        Main.stage = stage;

        scenes.put(MainScenes.LOGIN, new LoginView(stage).getScene());
//        scenes.put(MainScenes.CUSTOMER, new CustomerView(stage).getScene());
        scenes.put(MainScenes.CUSTOMER, new CustomerScenesMap(stage).getScene());
//        scenes.put(MainScenes.MANAGER, new MainView(stage).getScene());
//        scenes.put(MainScenes.WAITER, new MainView(stage).getScene());
//        scenes.put(MainScenes.CHEF, new MainView(stage).getScene());
//        scenes.put(MainScenes.DRIVER, new MainView(stage).getScene());

        // Start with user log in
//        stage.setScene(scenes.get(MainScenes.LOGIN));
//        stage.setTitle("Cafe94 Restaurant");
//        stage.show();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "customerscenes/mapscenes/customer-viewbookings.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WindowSize.MAIN.WIDTH,
                WindowSize.MAIN.HEIGHT);
        stage.setTitle("Cafe94 Restaurant");
        stage.setScene(scene);
        stage.show();


    }

    /**
     * This getter method return a Map of main scenes.
     *
     * @return Returns a Map of the scenes.
     */
    public static Map<MainScenes, Scene> getScenes() {
        return scenes;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch();
    }
}