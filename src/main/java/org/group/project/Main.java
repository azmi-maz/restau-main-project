package org.group.project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.scenes.*;
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

    // TODO the static application name here Cafe94 Restaurant and use it in
    //  all classes.

    @Override
    public void start(Stage stage) throws IOException {

        Main.stage = stage;

        scenes.put(MainScenes.LOGIN, new LoginView(stage).getScene());
        scenes.put(MainScenes.CUSTOMER, new CustomerScenesMap(stage).getScene());
        scenes.put(MainScenes.MANAGER, new ManagerScenesMap(stage).getScene());
        scenes.put(MainScenes.WAITER, new WaiterScenesMap(stage).getScene());
        scenes.put(MainScenes.CHEF, new ChefScenesMap(stage).getScene());
        scenes.put(MainScenes.DRIVER, new DriverScenesMap(stage).getScene());

        // TODO Logoff scene? to login

        // Start with user log in
        stage.setScene(scenes.get(MainScenes.LOGIN));
        // TODO final variable constant this
        stage.setTitle("Cafe94 Restaurant");
        stage.show();

//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
//                "customerscenes/mapscenes/customer-menuorder.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), WindowSize.MAIN.WIDTH,
//                WindowSize.MAIN.HEIGHT);
//        stage.setTitle("Cafe94 Restaurant");
//        stage.setScene(scene);
//        stage.show();

//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
//                "customerscenes/mapscenes/customer-menuorder.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), WindowSize.MAIN.WIDTH,
//                WindowSize.MAIN.HEIGHT);
//        stage.setTitle("Cafe94 Restaurant");
//        stage.setScene(scene);
//        stage.show();

//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
//                "waiterscenes/mapscenes/waiter-approvebookings.fxml"));
//        Scene scene = new Scene(fxmlLoader.load()john, WindowSize.MAIN.WIDTH,
//                WindowSize.MAIN.HEIGHT);
//        stage.setTitle("Cafe94 Restaurant");
//        stage.setScene(scene);
//        stage.show();

//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
//                "waiterscenes/mapscenes/waiter-approvedeliveries.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), WindowSize.MAIN.WIDTH,
//                WindowSize.MAIN.HEIGHT);
//        stage.setTitle("Cafe94 Restaurant");
//        stage.setScene(scene);
//        stage.show();


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