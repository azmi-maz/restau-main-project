package org.group.project.scenes.chef.mainViews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

public class MenuView implements ViewMaker {

    private Stage stage;

    public MenuView(Stage stage) {

        this.stage = stage;
    }

    @Override
    public Scene getScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "chefscenes/mapscenes/chef-viewmenulist.fxml"));
        return new Scene(fxmlLoader.load(), WindowSize.MAIN.WIDTH,
                WindowSize.MAIN.HEIGHT);
    }
}
