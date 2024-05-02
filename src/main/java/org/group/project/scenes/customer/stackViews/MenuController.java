package org.group.project.scenes.customer.stackViews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.group.project.Main;
import org.group.project.classes.FoodDrink;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.controllers.customer.CustomerMenuOrderViewController;
import org.group.project.scenes.ControllerView;
import org.group.project.scenes.WindowSize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class enables the customer to view menu and proceeds to order details
 * page with the order data being persisted.
 *
 * @author azmi_maz
 */
public class MenuController implements ControllerView {

    public static MenuPresenter presenter;

    public static List<FoodDrink> orderList = new ArrayList<>();
    private Scene scene;

    /**
     * This constructor sets up the main presenter.
     *
     * @param presenter - the main customer presenter.
     */
    public MenuController(MenuPresenter presenter) {
        this.presenter = presenter;

        try {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(Main.class.getResource(
                            "customerscenes/mapscenes/" +
                                    "customer-menuorder" +
                                    ".fxml"));

            CustomerMenuOrderViewController customerMenuOrderViewController =
                    new CustomerMenuOrderViewController(orderList);
            fxmlLoader.setController(customerMenuOrderViewController);
            scene = new Scene(fxmlLoader.load(), WindowSize.MAIN.WIDTH,
                    WindowSize.MAIN.HEIGHT);
        } catch (IOException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }
    }

    /**
     * This method gets the menu view scene.
     *
     * @return the customer menu view scene.
     */
    @Override
    public Scene getViewScene() {
        return scene;
    }
}
