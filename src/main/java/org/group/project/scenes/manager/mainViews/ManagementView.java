package org.group.project.scenes.manager.mainViews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

public class ManagementView implements ViewMaker {

    private Stage stage;

    public ManagementView(Stage stage) {

        this.stage = stage;
    }

    @Override
    public Scene getScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "managerscenes/mapscenes/manager-staffmanagement.fxml"));
        return new Scene(fxmlLoader.load(), WindowSize.MAIN.WIDTH,
                WindowSize.MAIN.HEIGHT);
    }
}
