package org.group.project.mapscenes.controller;

import javafx.event.Event;
import javafx.stage.Stage;
import org.group.project.MainAppMap;
import org.group.project.mapscenes.model.SceneName;
import org.group.project.mapscenes.view.ViewOne;

/**
 * Controller for {@link ViewOne}.
 * 
 * @author Knute Snortum
 * @version 2018-05-24
 */
public class ViewOneController extends IllegalArgumentException {
	
	private Stage stage;
	
	/** Must inject a stage */
	public ViewOneController(Stage stage) {
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
