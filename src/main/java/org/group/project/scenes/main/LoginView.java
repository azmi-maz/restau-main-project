package org.group.project.scenes.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

public class LoginView implements ViewMaker {

    private Stage stage;

    public LoginView(Stage stage) {

        this.stage = stage;
    }

    @Override
    public Scene getScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "homepages/login-view.fxml"));
        return new Scene(fxmlLoader.load(), WindowSize.MAIN.WIDTH,
                WindowSize.MAIN.HEIGHT);
    }

}
