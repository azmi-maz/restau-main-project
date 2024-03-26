package org.group.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login" +
                "-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WindowSize.MAIN.HEIGHT,
                WindowSize.MAIN.WIDTH);
        stage.setTitle("Cafe94 Restaurant");
        stage.setScene(scene);
        stage.show();

//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("new" +
//                "-customer-registration" +
//                ".fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), WindowSize.MAIN.HEIGHT,
//                WindowSize.MAIN.WIDTH);
//        stage.setTitle("Cafe94 Restaurant");
//        stage.setScene(scene);
//        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}