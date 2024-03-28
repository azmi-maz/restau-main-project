package org.group.project.scenes.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.mapscenes.view.ViewMaker;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

public class CustomerView implements ViewMaker {

    private Stage stage;

    public CustomerView(Stage stage) throws IOException {

        this.stage = stage;

//        CustomerScenesMap customerScenes = new CustomerScenesMap(stage);

        // Start with the main scene
//        stage.setScene(customerScenes.getScenes().get(CustomerMapsMain.HOME));
//        stage.setTitle("Cafe94 Restaurant");
//        stage.show();
    }

    @Override
    public Scene getScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "homepages/customer-homepage.fxml"));
        return new Scene(fxmlLoader.load(), WindowSize.MAIN.WIDTH,
                WindowSize.MAIN.HEIGHT);
    }

}
