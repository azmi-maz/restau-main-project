package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.FoodDrink;
import org.group.project.classes.Menu;
import org.group.project.classes.auxiliary.AlertPopUpWindow;
import org.group.project.exceptions.TextFileNotFoundException;
import org.group.project.scenes.WindowSize;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * This class enables the user to view the restaurant menu.
 *
 * @author azmi_maz
 */
public class CustomerMenuOrderViewController {

    private static final String MENU_IMAGE_FILEPATH = "images/menu/%s";
    private static final String DAILY_SPECIAL_TAG = "daily-special-stamp.png";
    private static final String ADD_ORDER_WINDOW = "smallwindows/" +
            "add-order-item.fxml";
    private static final String ADD_ORDER_WINDOW_TITLE = "Add Order Item";
    private static final String WHITE_FLOOR_BG = "images" +
            "/background/white-floor" +
            ".jpg";
    private static final int BG_IMAGE_WIDTH = 2000;
    private static final int BG_IMAGE_HEIGHT = 1500;

    @FXML
    private GridPane gridPane;

    private List<FoodDrink> orderList;

    /**
     * This sets up the menu list.
     *
     * @param orderList - the list of available food/drink items.
     */
    public CustomerMenuOrderViewController(List<FoodDrink> orderList) {
        this.orderList = orderList;
    }

    /**
     * This initializes the controller for the fxml.
     */
    public void initialize() {

        Image bgImage = null;
        try {
            bgImage = new Image(Main.class
                    .getResource(WHITE_FLOOR_BG).toURI().toString());
        } catch (URISyntaxException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }

        BackgroundSize bSize = new BackgroundSize(BG_IMAGE_WIDTH,
                BG_IMAGE_HEIGHT, false, false
                , false, false);

        gridPane.setBackground(new Background(new BackgroundImage(bgImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                bSize)));

        try {

            Menu menu = new Menu();
            menu.populateCustomerMenuFromDatabase(
                    gridPane
            );

        } catch (TextFileNotFoundException | URISyntaxException e) {
            AlertPopUpWindow.displayErrorWindow(
                    e.getMessage()
            );
            e.printStackTrace();
        }

        gridPane.setOnMouseClicked(e -> {

            boolean isImageViewSelected = e.getTarget() instanceof ImageView;

            if (isImageViewSelected) {
                String currentImageUrl = ((ImageView) e.getTarget())
                        .getImage().getUrl();
                boolean isFavouriteTag = List.of(currentImageUrl
                                .split("/")).getLast()
                        .equalsIgnoreCase(
                                DAILY_SPECIAL_TAG);

                // This tags any daily special item marked by chef.
                if (isFavouriteTag) {
                    return;
                }

                javafx.scene.Node imageNode =
                        (javafx.scene.Node) e.getTarget();
                ImageView imageView = (ImageView) imageNode;
                String imageUrl = imageView.getImage().getUrl();
                String imageName = Arrays.stream(imageUrl.toString().split(
                        "/")).toList().getLast();

                try {
                    FXMLLoader fxmlLoader =
                            new FXMLLoader(Main.class.getResource(
                                    ADD_ORDER_WINDOW));

                    String labelName = "";
                    String modifiedName = imageName.replace(
                            ".png", "");
                    String[] labelNameArray = modifiedName.split("-");
                    for (String string : labelNameArray) {
                        labelName += string.substring(0, 1).toUpperCase();
                        labelName += string.substring(1);
                        labelName += " ";
                    }

                    BorderPane borderPane = fxmlLoader.load();

                    CustomerMenuOrderAddItemController controller =
                            fxmlLoader.getController();

                    controller.setItemToEdit(
                            String.format(
                                    MENU_IMAGE_FILEPATH,
                                    imageName),
                            labelName, orderList);
                    Scene editScene = new Scene(borderPane,
                            WindowSize.MEDIUM.WIDTH,
                            WindowSize.MEDIUM.HEIGHT);

                    Stage editStage = new Stage();
                    editStage.setScene(editScene);

                    editStage.setTitle(ADD_ORDER_WINDOW_TITLE);

                    editStage.initModality(Modality.APPLICATION_MODAL);

                    editStage.showAndWait();
                } catch (IOException | URISyntaxException ex) {
                    AlertPopUpWindow.displayErrorWindow(
                            ex.getMessage()
                    );
                    ex.printStackTrace();
                }
            }

        });

    }

}
