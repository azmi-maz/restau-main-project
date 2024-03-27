package org.group.project.mapscenes.view;

import javafx.stage.Stage;
import org.group.project.mapscenes.controller.ViewThreeController;

/**
 * Creates and returns the scene for the third view.
 * 
 * @author Knute Snortum
 * @version 2018-05-24
 */
public class ViewThree extends ViewBase {

	/** Must inject a stage */
	public ViewThree(Stage stage) {
		super(stage, "This is scene 3",
				e -> new ViewThreeController(stage).handleMousePress(e));
	}

}
