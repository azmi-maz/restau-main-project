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
import org.group.project.classes.DataFileStructure;
import org.group.project.classes.DataManager;
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
//        System.out.println(imageDataList);

        Image dailySpecial = new Image(Main.class.getResource("images" +
                "/icons/daily-special-stamp.png").toURI().toString());

        for (String imageData : imageDataList) {
            List<String> imageDataDetails = List.of(imageData.split(","));
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


            Image image1 = new Image(Main.class.getResource(url).toURI().toString());

            ImageView imageView1 = new ImageView(image1);
            imageView1.fitHeightProperty().bind(gridPane.heightProperty().subtract(heightSub).divide(heightDiv));
            imageView1.fitWidthProperty().bind(gridPane.widthProperty().subtract(widthSub).divide(widthDiv));
            imageView1.setPreserveRatio(true);

            StackPane imageView1FirstStack = new StackPane();
            StackPane imageView1SecondStack = new StackPane();
            imageView1FirstStack.getChildren().add(imageView1);
            ImageView dailySpecialImg = new ImageView(dailySpecial);
            dailySpecialImg.setFitHeight(55);
            dailySpecialImg.setFitWidth(55);
            dailySpecialImg.setPreserveRatio(true);
            imageView1SecondStack.getChildren().add(dailySpecialImg);
            imageView1FirstStack.getChildren().add(imageView1SecondStack);

            gridPane.add(imageView1FirstStack, colIdx, rowIdx, colSpan, rowSpan);

            imageView1FirstStack.setAlignment(imageView1, Pos.valueOf(imgAlign));
            imageView1FirstStack.setAlignment(dailySpecialImg, Pos.valueOf(tagAlign));
            imageView1FirstStack.setAlignment(imageView1SecondStack, Pos.valueOf(stackAlign));
            imageView1SecondStack.setMaxHeight(maxHeight);
            imageView1SecondStack.setMaxWidth(maxWidth);

        }


//        Image image1 = new Image(Main.class.getResource("images" +
//                "/menu/bloody-mary.png").toURI().toString());
//        Image image2 = new Image(Main.class.getResource("images" +
//                "/menu/chocolate-souffle.png").toURI().toString());
//        Image image3 = new Image(Main.class.getResource("images" +
//                "/menu/croquembouche.png").toURI().toString());
//        Image image4 = new Image(Main.class.getResource("images" +
//                "/menu/coq-au-vin.png").toURI().toString());
//        Image image5 = new Image(Main.class.getResource("images" +
//                "/menu/classic-duck-confit.png").toURI().toString());
//        Image image6 = new Image(Main.class.getResource("images" +
//                "/menu/cosmopolitan.png").toURI().toString());
//        Image image7 = new Image(Main.class.getResource("images" +
//                "/menu/classic-creme-brulee.png").toURI().toString());
//        Image image8 = new Image(Main.class.getResource("images" +
//                "/menu/stuffed-pork-tenderloin.png").toURI().toString());
//        Image image9 = new Image(Main.class.getResource("images" +
//                "/menu/duck-a-lorange.png").toURI().toString());
//        Image image10 = new Image(Main.class.getResource("images" +
//                "/menu/irish-coffee.png").toURI().toString());
//        Image image11 = new Image(Main.class.getResource("images" +
//                "/menu/lobster-thermidor.png").toURI().toString());
//        Image image12 = new Image(Main.class.getResource("images" +
//                "/menu/long-island-iced-tea.png").toURI().toString());
//        Image image13 = new Image(Main.class.getResource("images" +
//                "/menu/lyon-chicken.png").toURI().toString());
//        Image image14 = new Image(Main.class.getResource("images" +
//                "/menu/manhattan.png").toURI().toString());
//        Image image15 = new Image(Main.class.getResource("images" +
//                "/menu/margarita.png").toURI().toString());
//        Image image16 = new Image(Main.class.getResource("images" +
//                "/menu/strip-steak-frite.png").toURI().toString());
//        Image image17 = new Image(Main.class.getResource("images" +
//                "/menu/martini.png").toURI().toString());
//        Image image18 = new Image(Main.class.getResource("images" +
//                "/menu/mojito.png").toURI().toString());
//        Image image19 = new Image(Main.class.getResource("images" +
//                "/menu/negroni.png").toURI().toString());
//        Image image20 = new Image(Main.class.getResource("images" +
//                "/menu/pear-tarte-tatin.png").toURI().toString());
//        Image image21 = new Image(Main.class.getResource("images" +
//                "/menu/pina-colada.png").toURI().toString());
//        Image image22 = new Image(Main.class.getResource("images" +
//                "/menu/steak-au-poivre.png").toURI().toString());
//        Image image23 = new Image(Main.class.getResource("images" +
//                "/menu/marseilles-shrimp-stew.png").toURI().toString());
//        Image image24 = new Image(Main.class.getResource("images" +
//                "/menu/daiquiri.png").toURI().toString());
//        Image image25 = new Image(Main.class.getResource("images" +
//                "/menu/white-russian.png").toURI().toString());
//
//        Image dailySpecial = new Image(Main.class.getResource("images" +
//                "/icons/daily-special-stamp.png").toURI().toString());
//
//        // Create ImageViews for each image
//        ImageView imageView1 = new ImageView(image1);
//        imageView1.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(4));
//        imageView1.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.6));
//        imageView1.setPreserveRatio(true);
//        ImageView imageView2 = new ImageView(image2);
//        imageView2.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.4));
//        imageView2.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.4));
//        imageView2.setPreserveRatio(true);
//        ImageView imageView3 = new ImageView(image3);
//        imageView3.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.0));
//        imageView3.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.0));
//        imageView3.setPreserveRatio(true);
//        ImageView imageView4 = new ImageView(image4);
//        imageView4.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.7));
//        imageView4.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.7));
//        imageView4.setPreserveRatio(true);
//        ImageView imageView5 = new ImageView(image5);
//        imageView5.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.4));
//        imageView5.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.4));
//        imageView5.setPreserveRatio(true);
//        ImageView imageView6 = new ImageView(image6);
//        imageView6.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.3));
//        imageView6.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.3));
//        imageView6.setPreserveRatio(true);
//        ImageView imageView7 = new ImageView(image7);
//        imageView7.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.7));
//        imageView7.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.7));
//        imageView7.setPreserveRatio(true);
//        ImageView imageView8 = new ImageView(image8);
//        imageView8.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.2));
//        imageView8.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.2));
//        imageView8.setPreserveRatio(true);
//        ImageView imageView9 = new ImageView(image9);
//        imageView9.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.1));
//        imageView9.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.1));
//        imageView9.setPreserveRatio(true);
//        ImageView imageView10 = new ImageView(image10);
//        imageView10.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.4));
//        imageView10.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.4));
//        imageView10.setPreserveRatio(true);
//        ImageView imageView11 = new ImageView(image11);
//        imageView11.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3));
//        imageView11.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3));
//        imageView11.setPreserveRatio(true);
//        ImageView imageView12 = new ImageView(image12);
//        imageView12.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.2));
//        imageView12.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.2));
//        imageView12.setPreserveRatio(true);
//        ImageView imageView13 = new ImageView(image13);
//        imageView13.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.2));
//        imageView13.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.2));
//        imageView13.setPreserveRatio(true);
//        ImageView imageView14 = new ImageView(image14);
//        imageView14.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.4));
//        imageView14.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.4));
//        imageView14.setPreserveRatio(true);
//        ImageView imageView15 = new ImageView(image15);
//        imageView15.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.5));
//        imageView15.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.5));
//        imageView15.setPreserveRatio(true);
//        ImageView imageView16 = new ImageView(image16);
//        imageView16.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3));
//        imageView16.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3));
//        imageView16.setPreserveRatio(true);
//        ImageView imageView17 = new ImageView(image17);
//        imageView17.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(2.6));
//        imageView17.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(2.6));
//        imageView17.setPreserveRatio(true);
//        ImageView imageView18 = new ImageView(image18);
//        imageView18.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(2.7));
//        imageView18.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(2.7));
//        imageView18.setPreserveRatio(true);
//        ImageView imageView19 = new ImageView(image19);
//        imageView19.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(5));
//        imageView19.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(5));
//        imageView19.setPreserveRatio(true);
//        ImageView imageView20 = new ImageView(image20);
//        imageView20.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.4));
//        imageView20.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.4));
//        imageView20.setPreserveRatio(true);
//        ImageView imageView21 = new ImageView(image21);
//        imageView21.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.4));
//        imageView21.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.4));
//        imageView21.setPreserveRatio(true);
//        ImageView imageView22 = new ImageView(image22);
//        imageView22.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(2.8));
//        imageView22.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(2.8));
//        imageView22.setPreserveRatio(true);
//        ImageView imageView23 = new ImageView(image23);
//        imageView23.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.5));
//        imageView23.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.5));
//        imageView23.setPreserveRatio(true);
//        ImageView imageView24 = new ImageView(image24);
//        imageView24.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.5));
//        imageView24.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.5));
//        imageView24.setPreserveRatio(true);
//        ImageView imageView25 = new ImageView(image25);
//        imageView25.fitHeightProperty().bind(gridPane.heightProperty().subtract(10).divide(3.8));
//        imageView25.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(3.8));
//        imageView25.setPreserveRatio(true);
//
//        // Create the grid stackpanes
//        StackPane imageView1FirstStack = new StackPane();
//        StackPane imageView1SecondStack = new StackPane();
//        imageView1FirstStack.getChildren().add(imageView1);
//        ImageView dailySpecialImg = new ImageView(dailySpecial);
//        dailySpecialImg.setFitHeight(55);
//        dailySpecialImg.setFitWidth(55);
//        dailySpecialImg.setPreserveRatio(true);
//        imageView1SecondStack.getChildren().add(dailySpecialImg);
//        imageView1FirstStack.getChildren().add(imageView1SecondStack);
//
//        StackPane imageView2FirstStack = new StackPane();
//        StackPane imageView2SecondStack = new StackPane();
//        ImageView dailySpecialImg2 = new ImageView(dailySpecial);
//        dailySpecialImg2.setFitHeight(55);
//        dailySpecialImg2.setFitWidth(55);
//        dailySpecialImg2.setPreserveRatio(true);
//        imageView2FirstStack.getChildren().add(imageView2);
//        imageView2SecondStack.getChildren().add(dailySpecialImg2);
//        imageView2FirstStack.getChildren().add(imageView2SecondStack);
//
//        StackPane imageView3FirstStack = new StackPane();
//        StackPane imageView3SecondStack = new StackPane();
//        ImageView dailySpecialImg3 = new ImageView(dailySpecial);
//        dailySpecialImg3.setFitHeight(55);
//        dailySpecialImg3.setFitWidth(55);
//        dailySpecialImg3.setPreserveRatio(true);
//        imageView3FirstStack.getChildren().add(imageView3);
//        imageView3SecondStack.getChildren().add(dailySpecialImg3);
//        imageView3FirstStack.getChildren().add(imageView3SecondStack);
//
//        StackPane imageView4FirstStack = new StackPane();
//        StackPane imageView4SecondStack = new StackPane();
//        ImageView dailySpecialImg4 = new ImageView(dailySpecial);
//        dailySpecialImg4.setFitHeight(55);
//        dailySpecialImg4.setFitWidth(55);
//        dailySpecialImg4.setPreserveRatio(true);
//        imageView4FirstStack.getChildren().add(imageView4);
//        imageView4SecondStack.getChildren().add(dailySpecialImg4);
//        imageView4FirstStack.getChildren().add(imageView4SecondStack);
//
//        StackPane imageView5FirstStack = new StackPane();
//        StackPane imageView5SecondStack = new StackPane();
//        ImageView dailySpecialImg5 = new ImageView(dailySpecial);
//        dailySpecialImg5.setFitHeight(55);
//        dailySpecialImg5.setFitWidth(55);
//        dailySpecialImg5.setPreserveRatio(true);
//        imageView5FirstStack.getChildren().add(imageView5);
//        imageView5SecondStack.getChildren().add(dailySpecialImg5);
//        imageView5FirstStack.getChildren().add(imageView5SecondStack);
//
//        StackPane imageView6FirstStack = new StackPane();
//        StackPane imageView6SecondStack = new StackPane();
//        ImageView dailySpecialImg6 = new ImageView(dailySpecial);
//        dailySpecialImg6.setFitHeight(55);
//        dailySpecialImg6.setFitWidth(55);
//        dailySpecialImg6.setPreserveRatio(true);
//        imageView6FirstStack.getChildren().add(imageView6);
//        imageView6SecondStack.getChildren().add(dailySpecialImg6);
//        imageView6FirstStack.getChildren().add(imageView6SecondStack);
//
//        StackPane imageView7FirstStack = new StackPane();
//        StackPane imageView7SecondStack = new StackPane();
//        ImageView dailySpecialImg7 = new ImageView(dailySpecial);
//        dailySpecialImg7.setFitHeight(55);
//        dailySpecialImg7.setFitWidth(55);
//        dailySpecialImg7.setPreserveRatio(true);
//        imageView7FirstStack.getChildren().add(imageView7);
//        imageView7SecondStack.getChildren().add(dailySpecialImg7);
//        imageView7FirstStack.getChildren().add(imageView7SecondStack);
//
//        StackPane imageView8FirstStack = new StackPane();
//        StackPane imageView8SecondStack = new StackPane();
//        ImageView dailySpecialImg8 = new ImageView(dailySpecial);
//        dailySpecialImg8.setFitHeight(55);
//        dailySpecialImg8.setFitWidth(55);
//        dailySpecialImg8.setPreserveRatio(true);
//        imageView8FirstStack.getChildren().add(imageView8);
//        imageView8SecondStack.getChildren().add(dailySpecialImg8);
//        imageView8FirstStack.getChildren().add(imageView8SecondStack);
//
//        StackPane imageView9FirstStack = new StackPane();
//        StackPane imageView9SecondStack = new StackPane();
//        ImageView dailySpecialImg9 = new ImageView(dailySpecial);
//        dailySpecialImg9.setFitHeight(55);
//        dailySpecialImg9.setFitWidth(55);
//        dailySpecialImg9.setPreserveRatio(true);
//        imageView9FirstStack.getChildren().add(imageView9);
//        imageView9SecondStack.getChildren().add(dailySpecialImg9);
//        imageView9FirstStack.getChildren().add(imageView9SecondStack);
//
//        StackPane imageView10FirstStack = new StackPane();
//        StackPane imageView10SecondStack = new StackPane();
//        ImageView dailySpecialImg10 = new ImageView(dailySpecial);
//        dailySpecialImg10.setFitHeight(55);
//        dailySpecialImg10.setFitWidth(55);
//        dailySpecialImg10.setPreserveRatio(true);
//        imageView10FirstStack.getChildren().add(imageView10);
//        imageView10SecondStack.getChildren().add(dailySpecialImg10);
//        imageView10FirstStack.getChildren().add(imageView10SecondStack);
//
//        StackPane imageView11FirstStack = new StackPane();
//        StackPane imageView11SecondStack = new StackPane();
//        ImageView dailySpecialImg11 = new ImageView(dailySpecial);
//        dailySpecialImg11.setFitHeight(55);
//        dailySpecialImg11.setFitWidth(55);
//        dailySpecialImg11.setPreserveRatio(true);
//        imageView11FirstStack.getChildren().add(imageView11);
//        imageView11SecondStack.getChildren().add(dailySpecialImg11);
//        imageView11FirstStack.getChildren().add(imageView11SecondStack);
//
//        StackPane imageView12FirstStack = new StackPane();
//        StackPane imageView12SecondStack = new StackPane();
//        ImageView dailySpecialImg12 = new ImageView(dailySpecial);
//        dailySpecialImg12.setFitHeight(55);
//        dailySpecialImg12.setFitWidth(55);
//        dailySpecialImg12.setPreserveRatio(true);
//        imageView12FirstStack.getChildren().add(imageView12);
//        imageView12SecondStack.getChildren().add(dailySpecialImg12);
//        imageView12FirstStack.getChildren().add(imageView12SecondStack);
//
//        StackPane imageView13FirstStack = new StackPane();
//        StackPane imageView13SecondStack = new StackPane();
//        ImageView dailySpecialImg13 = new ImageView(dailySpecial);
//        dailySpecialImg13.setFitHeight(55);
//        dailySpecialImg13.setFitWidth(55);
//        dailySpecialImg13.setPreserveRatio(true);
//        imageView13FirstStack.getChildren().add(imageView13);
//        imageView13SecondStack.getChildren().add(dailySpecialImg13);
//        imageView13FirstStack.getChildren().add(imageView13SecondStack);
//
//        StackPane imageView14FirstStack = new StackPane();
//        StackPane imageView14SecondStack = new StackPane();
//        ImageView dailySpecialImg14 = new ImageView(dailySpecial);
//        dailySpecialImg14.setFitHeight(55);
//        dailySpecialImg14.setFitWidth(55);
//        dailySpecialImg14.setPreserveRatio(true);
//        imageView14FirstStack.getChildren().add(imageView14);
//        imageView14SecondStack.getChildren().add(dailySpecialImg14);
//        imageView14FirstStack.getChildren().add(imageView14SecondStack);
//
//        StackPane imageView15FirstStack = new StackPane();
//        StackPane imageView15SecondStack = new StackPane();
//        ImageView dailySpecialImg15 = new ImageView(dailySpecial);
//        dailySpecialImg15.setFitHeight(55);
//        dailySpecialImg15.setFitWidth(55);
//        dailySpecialImg15.setPreserveRatio(true);
//        imageView15FirstStack.getChildren().add(imageView15);
//        imageView15SecondStack.getChildren().add(dailySpecialImg15);
//        imageView15FirstStack.getChildren().add(imageView15SecondStack);
//
//        StackPane imageView16FirstStack = new StackPane();
//        StackPane imageView16SecondStack = new StackPane();
//        ImageView dailySpecialImg16 = new ImageView(dailySpecial);
//        dailySpecialImg16.setFitHeight(55);
//        dailySpecialImg16.setFitWidth(55);
//        dailySpecialImg16.setPreserveRatio(true);
//        imageView16FirstStack.getChildren().add(imageView16);
//        imageView16SecondStack.getChildren().add(dailySpecialImg16);
//        imageView16FirstStack.getChildren().add(imageView16SecondStack);
//
//        StackPane imageView17FirstStack = new StackPane();
//        StackPane imageView17SecondStack = new StackPane();
//        ImageView dailySpecialImg17 = new ImageView(dailySpecial);
//        dailySpecialImg17.setFitHeight(55);
//        dailySpecialImg17.setFitWidth(55);
//        dailySpecialImg17.setPreserveRatio(true);
//        imageView17FirstStack.getChildren().add(imageView17);
//        imageView17SecondStack.getChildren().add(dailySpecialImg17);
//        imageView17FirstStack.getChildren().add(imageView17SecondStack);
//
//        StackPane imageView18FirstStack = new StackPane();
//        StackPane imageView18SecondStack = new StackPane();
//        ImageView dailySpecialImg18 = new ImageView(dailySpecial);
//        dailySpecialImg18.setFitHeight(55);
//        dailySpecialImg18.setFitWidth(55);
//        dailySpecialImg18.setPreserveRatio(true);
//        imageView18FirstStack.getChildren().add(imageView18);
//        imageView18SecondStack.getChildren().add(dailySpecialImg18);
//        imageView18FirstStack.getChildren().add(imageView18SecondStack);
//
//        StackPane imageView19FirstStack = new StackPane();
//        StackPane imageView19SecondStack = new StackPane();
//        ImageView dailySpecialImg19 = new ImageView(dailySpecial);
//        dailySpecialImg19.setFitHeight(55);
//        dailySpecialImg19.setFitWidth(55);
//        dailySpecialImg19.setPreserveRatio(true);
//        imageView19FirstStack.getChildren().add(imageView19);
//        imageView19SecondStack.getChildren().add(dailySpecialImg19);
//        imageView19FirstStack.getChildren().add(imageView19SecondStack);
//
//        StackPane imageView20FirstStack = new StackPane();
//        StackPane imageView20SecondStack = new StackPane();
//        ImageView dailySpecialImg20 = new ImageView(dailySpecial);
//        dailySpecialImg20.setFitHeight(55);
//        dailySpecialImg20.setFitWidth(55);
//        dailySpecialImg20.setPreserveRatio(true);
//        imageView20FirstStack.getChildren().add(imageView20);
//        imageView20SecondStack.getChildren().add(dailySpecialImg20);
//        imageView20FirstStack.getChildren().add(imageView20SecondStack);
//
//        StackPane imageView21FirstStack = new StackPane();
//        StackPane imageView21SecondStack = new StackPane();
//        ImageView dailySpecialImg21 = new ImageView(dailySpecial);
//        dailySpecialImg21.setFitHeight(55);
//        dailySpecialImg21.setFitWidth(55);
//        dailySpecialImg21.setPreserveRatio(true);
//        imageView21FirstStack.getChildren().add(imageView21);
//        imageView21SecondStack.getChildren().add(dailySpecialImg21);
//        imageView21FirstStack.getChildren().add(imageView21SecondStack);
//
//        StackPane imageView22FirstStack = new StackPane();
//        StackPane imageView22SecondStack = new StackPane();
//        ImageView dailySpecialImg22 = new ImageView(dailySpecial);
//        dailySpecialImg22.setFitHeight(55);
//        dailySpecialImg22.setFitWidth(55);
//        dailySpecialImg22.setPreserveRatio(true);
//        imageView22FirstStack.getChildren().add(imageView22);
//        imageView22SecondStack.getChildren().add(dailySpecialImg22);
//        imageView22FirstStack.getChildren().add(imageView22SecondStack);
//
//        StackPane imageView23FirstStack = new StackPane();
//        StackPane imageView23SecondStack = new StackPane();
//        ImageView dailySpecialImg23 = new ImageView(dailySpecial);
//        dailySpecialImg23.setFitHeight(55);
//        dailySpecialImg23.setFitWidth(55);
//        dailySpecialImg23.setPreserveRatio(true);
//        imageView23FirstStack.getChildren().add(imageView23);
//        imageView23SecondStack.getChildren().add(dailySpecialImg23);
//        imageView23FirstStack.getChildren().add(imageView23SecondStack);
//
//        StackPane imageView24FirstStack = new StackPane();
//        StackPane imageView24SecondStack = new StackPane();
//        ImageView dailySpecialImg24 = new ImageView(dailySpecial);
//        dailySpecialImg24.setFitHeight(55);
//        dailySpecialImg24.setFitWidth(55);
//        dailySpecialImg24.setPreserveRatio(true);
//        imageView24FirstStack.getChildren().add(imageView24);
//        imageView24SecondStack.getChildren().add(dailySpecialImg24);
//        imageView24FirstStack.getChildren().add(imageView24SecondStack);
//
//        StackPane imageView25FirstStack = new StackPane();
//        StackPane imageView25SecondStack = new StackPane();
//        ImageView dailySpecialImg25 = new ImageView(dailySpecial);
//        dailySpecialImg25.setFitHeight(55);
//        dailySpecialImg25.setFitWidth(55);
//        dailySpecialImg25.setPreserveRatio(true);
//        imageView25FirstStack.getChildren().add(imageView25);
//        imageView25SecondStack.getChildren().add(dailySpecialImg25);
//        imageView25FirstStack.getChildren().add(imageView25SecondStack);
//
//        // Add StackPanes to the GridPane
//        gridPane.add(imageView1FirstStack, 0, 0, 2, 1);
//        gridPane.add(imageView2FirstStack, 2, 0, 2, 1);
//        gridPane.add(imageView3FirstStack, 4, 0, 2, 2);
//        gridPane.add(imageView4FirstStack, 0, 1, 2, 2);
//        gridPane.add(imageView5FirstStack, 2, 1, 2, 1);
//        gridPane.add(imageView6FirstStack, 4, 2, 2, 1);
//        gridPane.add(imageView7FirstStack, 0, 3, 2, 1);
//        gridPane.add(imageView8FirstStack, 2, 2, 2, 3);
//        gridPane.add(imageView9FirstStack, 4, 3, 2, 2);
//        gridPane.add(imageView10FirstStack
//                , 0, 4, 2, 1);
//        gridPane.add(imageView11FirstStack, 2, 4, 2, 2);
//        gridPane.add(imageView12FirstStack, 0, 5, 2, 1);
//        gridPane.add(imageView13FirstStack, 4, 5, 2, 1);
//        gridPane.add(imageView14FirstStack, 2, 6, 2, 2);
//        gridPane.add(imageView15FirstStack, 4, 6, 2, 1);
//        gridPane.add(imageView16FirstStack, 0, 6, 2, 2);
//        gridPane.add(imageView17FirstStack, 2, 7, 4, 1);
//        gridPane.add(imageView18FirstStack, 0, 8, 3, 1);
//        gridPane.add(imageView19FirstStack, 2, 8, 3, 1);
//        gridPane.add(imageView20FirstStack, 4, 8, 2, 2);
//        gridPane.add(imageView21FirstStack, 0, 9, 2, 1);
//        gridPane.add(imageView22FirstStack, 2, 9, 2, 2);
//        gridPane.add(imageView23FirstStack, 4, 10, 2, 2);
//        gridPane.add(imageView24FirstStack, 0, 10, 2, 2);
//        gridPane.add(imageView25FirstStack, 2, 10, 2, 2);
//
//        imageView1FirstStack.setAlignment(imageView1, Pos.BOTTOM_RIGHT);
//        imageView1FirstStack.setAlignment(dailySpecialImg, Pos.BOTTOM_LEFT);
//        imageView1FirstStack.setAlignment(imageView1SecondStack, Pos.TOP_RIGHT);
//        imageView1SecondStack.setMaxHeight(65);
//        imageView1SecondStack.setMaxWidth(65);
//
//        imageView2FirstStack.setAlignment(imageView2, Pos.BOTTOM_CENTER);
//        imageView2FirstStack.setAlignment(dailySpecialImg2, Pos.BOTTOM_LEFT);
//        imageView2FirstStack.setAlignment(imageView2SecondStack, Pos.TOP_RIGHT);
//        imageView2SecondStack.setMaxHeight(75);
//        imageView2SecondStack.setMaxWidth(95);
//
//        imageView3FirstStack.setAlignment(imageView3, Pos.CENTER);
//        imageView3FirstStack.setAlignment(dailySpecialImg3, Pos.BOTTOM_LEFT);
//        imageView3FirstStack.setAlignment(imageView3SecondStack, Pos.TOP_RIGHT);
//        imageView3SecondStack.setMaxHeight(135);
//        imageView3SecondStack.setMaxWidth(95);
//
//        imageView4FirstStack.setAlignment(imageView4, Pos.CENTER);
//        imageView4FirstStack.setAlignment(dailySpecialImg4, Pos.BOTTOM_LEFT);
//        imageView4FirstStack.setAlignment(imageView4SecondStack, Pos.TOP_RIGHT);
//        imageView4SecondStack.setMaxHeight(85);
//        imageView4SecondStack.setMaxWidth(125);
//
//        imageView5FirstStack.setAlignment(imageView5, Pos.CENTER);
//        imageView5FirstStack.setAlignment(dailySpecialImg5, Pos.BOTTOM_LEFT);
//        imageView5FirstStack.setAlignment(imageView5SecondStack, Pos.TOP_RIGHT);
//        imageView5SecondStack.setMaxHeight(75);
//        imageView5SecondStack.setMaxWidth(95);
//
//        imageView6FirstStack.setAlignment(imageView6, Pos.BOTTOM_LEFT);
//        imageView6FirstStack.setAlignment(dailySpecialImg6, Pos.BOTTOM_LEFT);
//        imageView6FirstStack.setAlignment(imageView6SecondStack, Pos.TOP_RIGHT);
//        imageView6SecondStack.setMaxHeight(65);
//        imageView6SecondStack.setMaxWidth(115);
//
//        imageView7FirstStack.setAlignment(imageView7, Pos.BOTTOM_RIGHT);
//        imageView7FirstStack.setAlignment(dailySpecialImg7, Pos.BOTTOM_LEFT);
//        imageView7FirstStack.setAlignment(imageView7SecondStack, Pos.TOP_RIGHT);
//        imageView7SecondStack.setMaxHeight(65);
//        imageView7SecondStack.setMaxWidth(65);
//
//        imageView8FirstStack.setAlignment(imageView8, Pos.TOP_CENTER);
//        imageView8FirstStack.setAlignment(dailySpecialImg8, Pos.BOTTOM_LEFT);
//        imageView8FirstStack.setAlignment(imageView8SecondStack, Pos.TOP_RIGHT);
//        imageView8SecondStack.setMaxHeight(65);
//        imageView8SecondStack.setMaxWidth(95);
//
//        imageView9FirstStack.setAlignment(imageView9, Pos.CENTER);
//        imageView9FirstStack.setAlignment(dailySpecialImg9, Pos.BOTTOM_LEFT);
//        imageView9FirstStack.setAlignment(imageView9SecondStack, Pos.TOP_RIGHT);
//        imageView9SecondStack.setMaxHeight(135);
//        imageView9SecondStack.setMaxWidth(75);
//
//        imageView10FirstStack.setAlignment(imageView10, Pos.CENTER);
//        imageView10FirstStack.setAlignment(dailySpecialImg10, Pos.BOTTOM_LEFT);
//        imageView10FirstStack.setAlignment(imageView10SecondStack, Pos.TOP_RIGHT);
//        imageView10SecondStack.setMaxHeight(75);
//        imageView10SecondStack.setMaxWidth(95);
//
//        imageView11FirstStack.setAlignment(imageView11, Pos.CENTER);
//        imageView11FirstStack.setAlignment(dailySpecialImg11, Pos.BOTTOM_LEFT);
//        imageView11FirstStack.setAlignment(imageView11SecondStack, Pos.TOP_RIGHT);
//        imageView11SecondStack.setMaxHeight(165);
//        imageView11SecondStack.setMaxWidth(55);
//
//        imageView12FirstStack.setAlignment(imageView12, Pos.CENTER);
//        imageView12FirstStack.setAlignment(dailySpecialImg12, Pos.BOTTOM_LEFT);
//        imageView12FirstStack.setAlignment(imageView12SecondStack, Pos.TOP_RIGHT);
//        imageView12SecondStack.setMaxHeight(85);
//        imageView12SecondStack.setMaxWidth(75);
//
//        imageView13FirstStack.setAlignment(imageView13, Pos.TOP_CENTER);
//        imageView13FirstStack.setAlignment(dailySpecialImg13, Pos.BOTTOM_LEFT);
//        imageView13FirstStack.setAlignment(imageView13SecondStack, Pos.TOP_RIGHT);
//        imageView13SecondStack.setMaxHeight(65);
//        imageView13SecondStack.setMaxWidth(85);
//
//        imageView14FirstStack.setAlignment(imageView14, Pos.TOP_CENTER);
//        imageView14FirstStack.setAlignment(dailySpecialImg14, Pos.BOTTOM_LEFT);
//        imageView14FirstStack.setAlignment(imageView14SecondStack, Pos.TOP_RIGHT);
//        imageView14SecondStack.setMaxHeight(55);
//        imageView14SecondStack.setMaxWidth(95);
//
//        imageView15FirstStack.setAlignment(imageView15, Pos.CENTER_LEFT);
//        imageView15FirstStack.setAlignment(dailySpecialImg15, Pos.BOTTOM_LEFT);
//        imageView15FirstStack.setAlignment(imageView15SecondStack, Pos.TOP_RIGHT);
//        imageView15SecondStack.setMaxHeight(105);
//        imageView15SecondStack.setMaxWidth(145);
//
//        imageView16FirstStack.setAlignment(imageView16, Pos.TOP_RIGHT);
//        imageView16FirstStack.setAlignment(dailySpecialImg16, Pos.BOTTOM_LEFT);
//        imageView16FirstStack.setAlignment(imageView16SecondStack, Pos.TOP_RIGHT);
//        imageView16SecondStack.setMaxHeight(65);
//        imageView16SecondStack.setMaxWidth(75);
//
//        imageView17FirstStack.setAlignment(imageView17, Pos.CENTER);
//        imageView17FirstStack.setAlignment(dailySpecialImg17, Pos.BOTTOM_LEFT);
//        imageView17FirstStack.setAlignment(imageView17SecondStack, Pos.TOP_RIGHT);
//        imageView17SecondStack.setMaxHeight(65);
//        imageView17SecondStack.setMaxWidth(265);
//
//        imageView18FirstStack.setAlignment(imageView18, Pos.BOTTOM_CENTER);
//        imageView18FirstStack.setAlignment(dailySpecialImg18, Pos.BOTTOM_LEFT);
//        imageView18FirstStack.setAlignment(imageView18SecondStack, Pos.TOP_RIGHT);
//        imageView18SecondStack.setMaxHeight(65);
//        imageView18SecondStack.setMaxWidth(165);
//
//        imageView19FirstStack.setAlignment(imageView19, Pos.BOTTOM_CENTER);
//        imageView19FirstStack.setAlignment(dailySpecialImg19, Pos.BOTTOM_LEFT);
//        imageView19FirstStack.setAlignment(imageView19SecondStack, Pos.TOP_RIGHT);
//        imageView19SecondStack.setMaxHeight(75);
//        imageView19SecondStack.setMaxWidth(275);
//
//        imageView20FirstStack.setAlignment(imageView20, Pos.CENTER_RIGHT);
//        imageView20FirstStack.setAlignment(dailySpecialImg20, Pos.BOTTOM_LEFT);
//        imageView20FirstStack.setAlignment(imageView20SecondStack, Pos.TOP_RIGHT);
//        imageView20SecondStack.setMaxHeight(155);
//        imageView20SecondStack.setMaxWidth(75);
//
//        imageView21FirstStack.setAlignment(imageView21, Pos.BOTTOM_CENTER);
//        imageView21FirstStack.setAlignment(dailySpecialImg21, Pos.BOTTOM_LEFT);
//        imageView21FirstStack.setAlignment(imageView21SecondStack, Pos.TOP_RIGHT);
//        imageView21SecondStack.setMaxHeight(120);
//        imageView21SecondStack.setMaxWidth(95);
//
//        imageView22FirstStack.setAlignment(imageView22, Pos.CENTER_LEFT);
//        imageView22FirstStack.setAlignment(dailySpecialImg22, Pos.BOTTOM_LEFT);
//        imageView22FirstStack.setAlignment(imageView22SecondStack, Pos.TOP_RIGHT);
//        imageView22SecondStack.setMaxHeight(155);
//        imageView22SecondStack.setMaxWidth(75);
//
//        imageView23FirstStack.setAlignment(imageView23, Pos.TOP_RIGHT);
//        imageView23FirstStack.setAlignment(dailySpecialImg23, Pos.BOTTOM_LEFT);
//        imageView23FirstStack.setAlignment(imageView23SecondStack, Pos.TOP_RIGHT);
//        imageView23SecondStack.setMaxHeight(65);
//        imageView23SecondStack.setMaxWidth(85);
//
//        imageView24FirstStack.setAlignment(imageView24, Pos.CENTER);
//        imageView24FirstStack.setAlignment(dailySpecialImg24, Pos.BOTTOM_LEFT);
//        imageView24FirstStack.setAlignment(imageView24SecondStack, Pos.TOP_RIGHT);
//        imageView24SecondStack.setMaxHeight(155);
//        imageView24SecondStack.setMaxWidth(105);
//
//        imageView25FirstStack.setAlignment(imageView25, Pos.BOTTOM_CENTER);
//        imageView25FirstStack.setAlignment(dailySpecialImg25, Pos.BOTTOM_LEFT);
//        imageView25FirstStack.setAlignment(imageView25SecondStack, Pos.TOP_RIGHT);
//        imageView25SecondStack.setMaxHeight(285);
//        imageView25SecondStack.setMaxWidth(125);

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
