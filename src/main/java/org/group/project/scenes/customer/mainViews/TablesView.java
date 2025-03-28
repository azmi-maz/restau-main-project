package org.group.project.scenes.customer.mainViews;

import java.io.IOException;

import org.group.project.Main;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.controllers.customer.CustomerBookingTablesController;
import org.group.project.scenes.ViewMaker;
import org.group.project.scenes.WindowSize;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class prepares the customer tables availability view scene.
 * 
 * @author azmi_maz
 * 
 */
public class TablesView implements ViewMaker {

    private static final String VIEW_TABLES = "customerscenes/mapscenes/" +
            "customer-viewtables.fxml";
    public static CustomerBookingTablesController controller;
    private Stage stage;

    public TablesView(Stage stage) {

        this.stage = stage;
    }


    @Override
    public Scene getScene() {
        
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
            VIEW_TABLES));

        VBox vbox = null;
        try {
            vbox = fxmlLoader.load();
        } catch (IOException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }

        controller = fxmlLoader.getController();

        return new Scene(vbox, WindowSize.MAIN.WIDTH,
                WindowSize.MAIN.HEIGHT);

    }
    
}
