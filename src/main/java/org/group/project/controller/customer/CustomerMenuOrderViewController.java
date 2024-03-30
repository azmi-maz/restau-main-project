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
                "/menu/bloody-mary.png").toURI().toString());
        Image image2 = new Image(Main.class.getResource("images" +
                "/menu/croquembouche.png").toURI().toString());
        Image image3 = new Image(Main.class.getResource("images" +
                "/menu/manhattan.png").toURI().toString());

        // Create ImageViews for each image
        ImageView imageView1 = new ImageView(image1);
        imageView1.setFitWidth(200);
        imageView1.setFitHeight(200);
        ImageView imageView2 = new ImageView(image1);
        imageView2.setFitWidth(450);
        imageView2.setFitHeight(450);
        ImageView imageView3 = new ImageView(image3);
        imageView3.setFitWidth(200);
        imageView3.setFitHeight(200);

        // Add ImageViews to the GridPane
        gridPane.setGridLinesVisible(true);
        gridPane.add(imageView1, 0, 0);
        gridPane.add(imageView2, 2, 1, 2, 2);
        gridPane.add(imageView3, 0, 3);

        GridPane.setHalignment(imageView1, HPos.RIGHT);
        GridPane.setValignment(imageView1, VPos.CENTER);

        GridPane.setHalignment(imageView2, HPos.CENTER);
        GridPane.setValignment(imageView2, VPos.CENTER);

        GridPane.setHalignment(imageView3, HPos.LEFT);
        GridPane.setValignment(imageView3, VPos.CENTER);

        imageView2.setOnMousePressed(e -> {
            System.out.println("Image2");
        });

    }
}
