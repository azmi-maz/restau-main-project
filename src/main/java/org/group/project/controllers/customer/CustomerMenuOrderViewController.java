package org.group.project.controllers.customer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.group.project.Main;
import org.group.project.classes.FoodDrink;
import org.group.project.scenes.WindowSize;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerMenuOrderViewController {

    @FXML
    private VBox mainVBox;

    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView bgImage;

    private List<FoodDrink> orderList = new ArrayList<>();

    public void initialize() throws URISyntaxException {

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

        Image image1 = new Image(Main.class.getResource("images" +
                "/menu/bloody-mary.png").toURI().toString());
        Image image2 = new Image(Main.class.getResource("images" +
                "/menu/chocolate-souffle.png").toURI().toString());
        Image image3 = new Image(Main.class.getResource("images" +
                "/menu/croquembouche.png").toURI().toString());
        Image image4 = new Image(Main.class.getResource("images" +
                "/menu/coq-au-vin.png").toURI().toString());
        Image image5 = new Image(Main.class.getResource("images" +
                "/menu/classic-duck-confit.png").toURI().toString());
        Image image6 = new Image(Main.class.getResource("images" +
                "/menu/cosmopolitan.png").toURI().toString());
        Image image7 = new Image(Main.class.getResource("images" +
                "/menu/classic-creme-brulee.png").toURI().toString());
        Image image8 = new Image(Main.class.getResource("images" +
                "/menu/stuffed-pork-tenderloin.png").toURI().toString());
        Image image9 = new Image(Main.class.getResource("images" +
                "/menu/duck-a-lorange.png").toURI().toString());
        Image image10 = new Image(Main.class.getResource("images" +
                "/menu/irish-coffee.png").toURI().toString());
        Image image11 = new Image(Main.class.getResource("images" +
                "/menu/lobster-thermidor.png").toURI().toString());
        Image image12 = new Image(Main.class.getResource("images" +
                "/menu/long-island-iced-tea.png").toURI().toString());
        Image image13 = new Image(Main.class.getResource("images" +
                "/menu/lyon-chicken.png").toURI().toString());
        Image image14 = new Image(Main.class.getResource("images" +
                "/menu/manhattan.png").toURI().toString());
        Image image15 = new Image(Main.class.getResource("images" +
                "/menu/margarita.png").toURI().toString());
        Image image16 = new Image(Main.class.getResource("images" +
                "/menu/strip-steak-frite.png").toURI().toString());
        Image image17 = new Image(Main.class.getResource("images" +
                "/menu/martini.png").toURI().toString());
        Image image18 = new Image(Main.class.getResource("images" +
                "/menu/mojito.png").toURI().toString());
        Image image19 = new Image(Main.class.getResource("images" +
                "/menu/negroni.png").toURI().toString());
        Image image20 = new Image(Main.class.getResource("images" +
                "/menu/pear-tarte-tatin.png").toURI().toString());
        Image image21 = new Image(Main.class.getResource("images" +
                "/menu/pina-colada.png").toURI().toString());
        Image image22 = new Image(Main.class.getResource("images" +
                "/menu/steak-au-poivre.png").toURI().toString());
        Image image23 = new Image(Main.class.getResource("images" +
                "/menu/marseilles-shrimp-stew.png").toURI().toString());
        Image image24 = new Image(Main.class.getResource("images" +
                "/menu/daiquiri.png").toURI().toString());
        Image image25 = new Image(Main.class.getResource("images" +
                "/menu/white-russian.png").toURI().toString());

        // Create ImageViews for each image
        ImageView imageView1 = new ImageView(image1);
        imageView1.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(4));
        imageView1.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.6));
        imageView1.setPreserveRatio(true);
        ImageView imageView2 = new ImageView(image2);
        imageView2.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.4));
        imageView2.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.4));
        imageView2.setPreserveRatio(true);
        ImageView imageView3 = new ImageView(image3);
        imageView3.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.0));
        imageView3.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.0));
        imageView3.setPreserveRatio(true);
        ImageView imageView4 = new ImageView(image4);
        imageView4.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.7));
        imageView4.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.7));
        imageView4.setPreserveRatio(true);
        ImageView imageView5 = new ImageView(image5);
        imageView5.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.4));
        imageView5.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.4));
        imageView5.setPreserveRatio(true);
        ImageView imageView6 = new ImageView(image6);
        imageView6.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.3));
        imageView6.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.3));
        imageView6.setPreserveRatio(true);
        ImageView imageView7 = new ImageView(image7);
        imageView7.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.7));
        imageView7.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.7));
        imageView7.setPreserveRatio(true);
        ImageView imageView8 = new ImageView(image8);
        imageView8.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.2));
        imageView8.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.2));
        imageView8.setPreserveRatio(true);
        ImageView imageView9 = new ImageView(image9);
        imageView9.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.1));
        imageView9.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.1));
        imageView9.setPreserveRatio(true);
        ImageView imageView10 = new ImageView(image10);
        imageView10.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.4));
        imageView10.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.4));
        imageView10.setPreserveRatio(true);
        ImageView imageView11 = new ImageView(image11);
        imageView11.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3));
        imageView11.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3));
        imageView11.setPreserveRatio(true);
        ImageView imageView12 = new ImageView(image12);
        imageView12.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.2));
        imageView12.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.2));
        imageView12.setPreserveRatio(true);
        ImageView imageView13 = new ImageView(image13);
        imageView13.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.2));
        imageView13.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.2));
        imageView13.setPreserveRatio(true);
        ImageView imageView14 = new ImageView(image14);
        imageView14.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.4));
        imageView14.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.4));
        imageView14.setPreserveRatio(true);
        ImageView imageView15 = new ImageView(image15);
        imageView15.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.5));
        imageView15.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.5));
        imageView15.setPreserveRatio(true);
        ImageView imageView16 = new ImageView(image16);
        imageView16.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3));
        imageView16.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3));
        imageView16.setPreserveRatio(true);
        ImageView imageView17 = new ImageView(image17);
        imageView17.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(2.6));
        imageView17.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(2.6));
        imageView17.setPreserveRatio(true);
        ImageView imageView18 = new ImageView(image18);
        imageView18.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(2.7));
        imageView18.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(2.7));
        imageView18.setPreserveRatio(true);
        ImageView imageView19 = new ImageView(image19);
        imageView19.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(5));
        imageView19.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(5));
        imageView19.setPreserveRatio(true);
        ImageView imageView20 = new ImageView(image20);
        imageView20.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.4));
        imageView20.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.4));
        imageView20.setPreserveRatio(true);
        ImageView imageView21 = new ImageView(image21);
        imageView21.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.2));
        imageView21.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.2));
        imageView21.setPreserveRatio(true);
        ImageView imageView22 = new ImageView(image22);
        imageView22.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(2.8));
        imageView22.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(2.8));
        imageView22.setPreserveRatio(true);
        ImageView imageView23 = new ImageView(image23);
        imageView23.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.5));
        imageView23.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.5));
        imageView23.setPreserveRatio(true);
        ImageView imageView24 = new ImageView(image24);
        imageView24.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.5));
        imageView24.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.5));
        imageView24.setPreserveRatio(true);
        ImageView imageView25 = new ImageView(image25);
        imageView25.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.8));
        imageView25.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.8));
        imageView25.setPreserveRatio(true);


        // Add ImageViews to the GridPane
        gridPane.add(imageView1, 0, 0, 2, 1);
        gridPane.add(imageView2, 2, 0, 2, 1);
        gridPane.add(imageView3, 4, 0, 2, 2);
        gridPane.add(imageView4, 0, 1, 2, 2);
        gridPane.add(imageView5, 2, 1, 2, 1);
        gridPane.add(imageView6, 4, 2, 2, 1);
        gridPane.add(imageView7, 0, 3, 2, 1);
        gridPane.add(imageView8, 2, 2, 2, 3);
        gridPane.add(imageView9, 4, 3, 2, 2);
        gridPane.add(imageView10
                , 0, 4, 2, 1);
        gridPane.add(imageView11, 2, 4, 2, 2);
        gridPane.add(imageView12, 0, 5, 2, 1);
        gridPane.add(imageView13, 4, 5, 2, 1);
        gridPane.add(imageView14, 2, 6, 2, 2);
        gridPane.add(imageView15, 4, 6, 2, 1);
        gridPane.add(imageView16, 0, 6, 2, 2);
        gridPane.add(imageView17, 2, 7, 4, 1);
        gridPane.add(imageView18, 0, 8, 3, 1);
        gridPane.add(imageView19, 2, 8, 3, 1);
        gridPane.add(imageView20, 4, 8, 2, 2);
        gridPane.add(imageView21, 0, 9, 2, 1);
        gridPane.add(imageView22, 2, 9, 2, 2);
        gridPane.add(imageView23, 4, 10, 2, 2);
        gridPane.add(imageView24, 0, 10, 2, 2);
        gridPane.add(imageView25, 2, 10, 2, 2);

        GridPane.setHalignment(imageView1, HPos.RIGHT);
        GridPane.setValignment(imageView1, VPos.BOTTOM);

        GridPane.setHalignment(imageView2, HPos.CENTER);
        GridPane.setValignment(imageView2, VPos.BOTTOM);

        GridPane.setHalignment(imageView3, HPos.CENTER);
        GridPane.setValignment(imageView3, VPos.CENTER);

        GridPane.setHalignment(imageView4, HPos.CENTER);
        GridPane.setValignment(imageView4, VPos.CENTER);

        GridPane.setHalignment(imageView5, HPos.CENTER);
        GridPane.setValignment(imageView5, VPos.CENTER);

        GridPane.setHalignment(imageView6, HPos.LEFT);
        GridPane.setValignment(imageView6, VPos.BOTTOM);

        GridPane.setHalignment(imageView7, HPos.RIGHT);
        GridPane.setValignment(imageView7, VPos.BOTTOM);

        GridPane.setHalignment(imageView8, HPos.CENTER);
        GridPane.setValignment(imageView8, VPos.TOP);

        GridPane.setHalignment(imageView9, HPos.CENTER);
        GridPane.setValignment(imageView9, VPos.CENTER);

        GridPane.setHalignment(imageView10, HPos.CENTER);
        GridPane.setValignment(imageView10, VPos.CENTER);

        GridPane.setHalignment(imageView11, HPos.CENTER);
        GridPane.setValignment(imageView11, VPos.CENTER);

        GridPane.setHalignment(imageView12, HPos.CENTER);
        GridPane.setValignment(imageView12, VPos.CENTER);

        GridPane.setHalignment(imageView13, HPos.CENTER);
        GridPane.setValignment(imageView13, VPos.TOP);

        GridPane.setHalignment(imageView14, HPos.CENTER);
        GridPane.setValignment(imageView14, VPos.TOP);

        GridPane.setHalignment(imageView15, HPos.LEFT);
        GridPane.setValignment(imageView15, VPos.CENTER);

        GridPane.setHalignment(imageView16, HPos.RIGHT);
        GridPane.setValignment(imageView16, VPos.TOP);

        GridPane.setHalignment(imageView17, HPos.CENTER);
        GridPane.setValignment(imageView17, VPos.CENTER);

        GridPane.setHalignment(imageView18, HPos.CENTER);
        GridPane.setValignment(imageView18, VPos.BOTTOM);

        GridPane.setHalignment(imageView19, HPos.CENTER);
        GridPane.setValignment(imageView19, VPos.BOTTOM);

        GridPane.setHalignment(imageView20, HPos.RIGHT);
        GridPane.setValignment(imageView20, VPos.CENTER);

        GridPane.setHalignment(imageView21, HPos.CENTER);
        GridPane.setValignment(imageView21, VPos.CENTER);

        GridPane.setHalignment(imageView22, HPos.LEFT);
        GridPane.setValignment(imageView22, VPos.CENTER);

        GridPane.setHalignment(imageView23, HPos.RIGHT);
        GridPane.setValignment(imageView23, VPos.TOP);

        GridPane.setHalignment(imageView24, HPos.CENTER);
        GridPane.setValignment(imageView24, VPos.TOP);

        GridPane.setHalignment(imageView25, HPos.CENTER);
        GridPane.setValignment(imageView25, VPos.BOTTOM);

        gridPane.setOnMouseClicked(e -> {

            boolean isImageViewSelected = e.getTarget() instanceof ImageView;

            if (isImageViewSelected) {
                javafx.scene.Node imageNode =
                        (javafx.scene.Node) e.getTarget();
                ImageView imageView = (ImageView) imageNode;
                String imageUrl = imageView.getImage().getUrl();
                String imageName = Arrays.stream(imageUrl.toString().split(
                        "/")).toList().getLast();

                try {
                    FXMLLoader fxmlLoader =
                            new FXMLLoader(Main.class.getResource(
                                    "smallwindows/add-edit-order-item" +
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

                    CustomerMenuOrderAddEditItemController controller =
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
