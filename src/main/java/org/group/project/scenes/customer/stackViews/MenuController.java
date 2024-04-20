package org.group.project.scenes.customer.stackViews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.group.project.Main;
import org.group.project.classes.FoodDrink;
import org.group.project.controllers.customer.CustomerMenuOrderViewController;
import org.group.project.scenes.ControllerView;
import org.group.project.scenes.WindowSize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuController implements ControllerView {

    private Scene scene;

    public static MenuPresenter presenter;

    public static List<FoodDrink> orderList = new ArrayList<>();

    public MenuController(MenuPresenter presenter) {
        this.presenter = presenter;

        try {
            FXMLLoader fxmlLoader =
                new FXMLLoader(Main.class.getResource(
                        "customerscenes/mapscenes/customer-menuorder" +
                                ".fxml"));

            CustomerMenuOrderViewController customerMenuOrderViewController =
                    new CustomerMenuOrderViewController(orderList);
            fxmlLoader.setController(customerMenuOrderViewController);
            scene = new Scene(fxmlLoader.load(), WindowSize.MAIN.WIDTH,
                    WindowSize.MAIN.HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Scene getViewScene() {
        return scene;
    }
}
