package org.group.project.classes.auxiliary;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.group.project.Main;

import java.util.Objects;

/**
 * This class set up graphic Javafx elements with png file.
 *
 * @author azmi_maz
 */
public class ImageLoader {

    /**
     * This method sets up a button with a png image and
     * adjust the width and height of the embedded image.
     *
     * @param button   - the JavaFX button.
     * @param width    - the desired width of the image in px.
     * @param height   - the desired height of the image in px.
     * @param fileName - the image file name.
     */
    public static void setUpGraphicButton(Button button,
                                          double width,
                                          double height,
                                          String fileName) {
        Image img = new Image(Objects.requireNonNull(
                Main.class.getResourceAsStream(
                        "images/icons/" + fileName + ".png")));
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(width);
        imgView.setFitHeight(height);
        button.setGraphic(imgView);
    }
}
