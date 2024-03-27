package org.group.project.mapscenes.controller;

import javafx.event.Event;
import javafx.stage.Stage;
import org.group.project.MainAppMap;
import org.group.project.mapscenes.model.SceneName;
import org.group.project.mapscenes.view.ViewTwo;

/**
 * Controller for {@link ViewTwo}.
 * 
 * @author Knute Snortum
 * @version 2018-05-24
 */
public class ViewTwoController {
	
	private Stage stage;
	
	/** Must inject a stage */
	public ViewTwoController(Stage stage) {
		if (stage == null) {
			throw new IllegalArgumentException("Stage cannot be null");
		}
		
		this.stage = stage;
	}
	
	/** Display main scene when the "back" button is clicked */
	public void handleMousePress(Event event) {
		stage.setScene(MainAppMap.getScenes().get(SceneName.MAIN));
	}
}
