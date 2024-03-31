package org.group.project.scenes.waiter.mainViews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.mapscenes.view.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

public class DineInView implements ViewMaker {

    private Stage stage;

    public DineInView(Stage stage) {

        this.stage = stage;
    }

    @Override
    public Scene getScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "waiterscenes/mapscenes/waiter-dineinorders.fxml"));
        return new Scene(fxmlLoader.load(), WindowSize.MAIN.WIDTH,
                WindowSize.MAIN.HEIGHT);
    }
}
