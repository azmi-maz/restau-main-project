package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.auxiliary.DataFileStructure;
import org.group.project.classes.auxiliary.DataManager;
import org.group.project.classes.FoodDrink;
import org.group.project.scenes.WindowSize;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class CustomerMenuOrderViewController {

    @FXML
    private VBox mainVBox;

    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView bgImage;

    private List<FoodDrink> orderList;

    private final int DAILY_SPECIAL_TAG_HEIGHT = 55;
    private final int DAILY_SPECIAL_TAG_WIDTH = 55;

    public CustomerMenuOrderViewController(List<FoodDrink> orderList) {
        this.orderList = orderList;
    }

    public void initialize() throws URISyntaxException, FileNotFoundException {

        Image bgImage = new Image(Main.class.getResource("images" +
                "/background/white-floor" +
                ".jpg").toURI().toString());

        BackgroundSize bSize = new BackgroundSize(2000,
                1500, false, false
                , false, false);

        gridPane.setBackground(new Background(new BackgroundImage(bgImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                bSize)));

        // TODO try catch?
        List<String> imageDataList = DataManager.allDataFromFile("MENU");

        Image dailySpecial = new Image(Main.class.getResource("images" +
                "/icons/daily-special-stamp.png").toURI().toString());

        for (String imageData : imageDataList) {
            List<String> imageDataDetails = List.of(imageData.split(","));
            boolean isDailySpecial = Boolean.parseBoolean(imageDataDetails.get(DataFileStructure.getIndexByColName("MENU", "isDailySpecial")));
            String url = imageDataDetails.get(DataFileStructure.getIndexByColName("MENU", "imageurl"));
            double heightSub = Double.parseDouble(imageDataDetails.get(DataFileStructure.getIndexByColName("MENU", "height-sub")));
            double heightDiv = Double.parseDouble(imageDataDetails.get(DataFileStructure.getIndexByColName("MENU", "height-div")));
            double widthSub = Double.parseDouble(imageDataDetails.get(DataFileStructure.getIndexByColName("MENU", "width-sub")));
            double widthDiv = Double.parseDouble(imageDataDetails.get(DataFileStructure.getIndexByColName("MENU", "width-div")));
            int colIdx = Integer.parseInt(imageDataDetails.get(DataFileStructure.getIndexByColName("MENU", "colIdx")));
            int rowIdx = Integer.parseInt(imageDataDetails.get(DataFileStructure.getIndexByColName("MENU", "rowIdx")));
            int colSpan = Integer.parseInt(imageDataDetails.get(DataFileStructure.getIndexByColName("MENU", "colSpan")));
            int rowSpan = Integer.parseInt(imageDataDetails.get(DataFileStructure.getIndexByColName("MENU", "rowSpan")));
            String imgAlign = imageDataDetails.get(DataFileStructure.getIndexByColName("MENU", "imgAlign"));
            String tagAlign = imageDataDetails.get(DataFileStructure.getIndexByColName("MENU", "tagAlign"));
            String stackAlign = imageDataDetails.get(DataFileStructure.getIndexByColName("MENU", "stackAlign"));
            double maxHeight = Double.parseDouble(imageDataDetails.get(DataFileStructure.getIndexByColName("MENU", "maxHeight")));
            double maxWidth = Double.parseDouble(imageDataDetails.get(DataFileStructure.getIndexByColName("MENU", "maxWidth")));


            Image image = new Image(Main.class.getResource(url).toURI().toString());

            ImageView imageView = new ImageView(image);
            imageView.fitHeightProperty().bind(gridPane.heightProperty().subtract(heightSub).divide(heightDiv));
            imageView.fitWidthProperty().bind(gridPane.widthProperty().subtract(widthSub).divide(widthDiv));
            imageView.setPreserveRatio(true);

            StackPane imageViewFirstStack = new StackPane();
            StackPane imageViewSecondStack = new StackPane();
            imageViewFirstStack.getChildren().add(imageView);
            ImageView dailySpecialImg = new ImageView(dailySpecial);
            dailySpecialImg.setFitHeight(DAILY_SPECIAL_TAG_HEIGHT);
            dailySpecialImg.setFitWidth(DAILY_SPECIAL_TAG_WIDTH);
            dailySpecialImg.setPreserveRatio(true);

            if (isDailySpecial) {
                imageViewSecondStack.getChildren().add(dailySpecialImg);
            }
            imageViewFirstStack.getChildren().add(imageViewSecondStack);

            gridPane.add(imageViewFirstStack, colIdx, rowIdx, colSpan, rowSpan);

            imageViewFirstStack.setAlignment(imageView, Pos.valueOf(imgAlign));
            imageViewFirstStack.setAlignment(dailySpecialImg, Pos.valueOf(tagAlign));
            imageViewFirstStack.setAlignment(imageViewSecondStack, Pos.valueOf(stackAlign));
            imageViewSecondStack.setMaxHeight(maxHeight);
            imageViewSecondStack.setMaxWidth(maxWidth);

        }

        gridPane.setOnMouseClicked(e -> {

            boolean isImageViewSelected = e.getTarget() instanceof ImageView;

            if (isImageViewSelected) {
                String currentImageUrl = ((ImageView) e.getTarget()).getImage().getUrl();
                boolean isFavouriteTag = List.of(currentImageUrl.split("/")).getLast().equalsIgnoreCase("daily-special-stamp.png");

                // TODO needed to handle if tag was clicked
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
                                    "smallwindows/add-order-item" +
                                            ".fxml"));

                    String labelName = "";
                    String modifiedName = imageName.replace(".png", "");
                    String[] labelNameArray = modifiedName.split("-");
                    for (String string : labelNameArray) {
                        labelName += string.substring(0, 1).toUpperCase();
                        labelName += string.substring(1);
                        labelName += " ";
                    }

                    BorderPane borderPane = fxmlLoader.load();

                    CustomerMenuOrderAddItemController controller =
                            fxmlLoader.getController();

                    controller.setItemToEdit("images/menu/" + imageName,
                            labelName, orderList);
                    Scene editScene = new Scene(borderPane,
                            WindowSize.MEDIUM.WIDTH,
                            WindowSize.MEDIUM.HEIGHT);

                    Stage editStage = new Stage();
                    editStage.setScene(editScene);
                    // TODO Should final variable this
                    editStage.setTitle("Add Order Item");

                    editStage.initModality(Modality.APPLICATION_MODAL);

                    editStage.showAndWait();
                } catch (IOException ex) {
                    // TODO catch error
                    throw new RuntimeException(ex);
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });

    }

}
