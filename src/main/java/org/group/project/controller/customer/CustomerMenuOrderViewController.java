package org.group.project.controller.customer;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.group.project.Main;

import java.net.URISyntaxException;

public class CustomerMenuOrderViewController {

    @FXML
    private GridPane gridPane;


    public void initialize() throws URISyntaxException {

        Image image1 = new Image(Main.class.getResource("images" +
                "/menu/strip-steak-frite.png").toURI().toString()); // yes
        Image image2 = new Image(Main.class.getResource("images" +
                "/menu/stuffed-pork-tenderloin.png").toURI().toString()); // yes
        Image image3 = new Image(Main.class.getResource("images" +
                "/menu/strip-steak-frite.png").toURI().toString()); // yes
        Image image4 = new Image(Main.class.getResource("images" +
                "/menu/stuffed-pork-tenderloin.png").toURI().toString()); // yes

        // Create ImageViews for each image
        ImageView imageView1 = new ImageView(image1);
        imageView1.fitHeightProperty().bind(gridPane.heightProperty().subtract(50).divide(5));
        imageView1.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(5));
        imageView1.setPreserveRatio(true);
        ImageView imageView2 = new ImageView(image2);
        imageView2.fitHeightProperty().bind(gridPane.heightProperty().subtract(50).divide(5));
        imageView2.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(5));
        imageView2.setPreserveRatio(true);
        ImageView imageView3 = new ImageView(image3);
        imageView3.fitHeightProperty().bind(gridPane.heightProperty().subtract(50).divide(5));
        imageView3.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(5));
        imageView3.setPreserveRatio(true);
        ImageView imageView4 = new ImageView(image4);
        imageView4.fitHeightProperty().bind(gridPane.heightProperty().subtract(50).divide(5));
        imageView4.fitWidthProperty().bind(gridPane.widthProperty().subtract(10).divide(5));
        imageView4.setPreserveRatio(true);

        // Add ImageViews to the GridPane
        gridPane.setGridLinesVisible(true);
        gridPane.add(imageView1, 0, 0);
        gridPane.add(imageView2, 1, 0);
        gridPane.add(imageView3, 2, 0);
        gridPane.add(imageView4, 3, 0);

        GridPane.setHalignment(imageView1, HPos.CENTER);
        GridPane.setValignment(imageView1, VPos.CENTER);

        GridPane.setHalignment(imageView2, HPos.CENTER);
        GridPane.setValignment(imageView2, VPos.CENTER);

        GridPane.setHalignment(imageView3, HPos.CENTER);
        GridPane.setValignment(imageView3, VPos.CENTER);

        GridPane.setHalignment(imageView4, HPos.CENTER);
        GridPane.setValignment(imageView4, VPos.CENTER);


    }
}
