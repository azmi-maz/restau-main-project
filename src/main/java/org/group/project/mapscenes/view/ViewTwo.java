package org.group.project.mapscenes.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.MainAppMap;
import org.group.project.mapscenes.controller.ViewTwoController;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

public class ViewTwo {

	private Stage stage;

	public ViewTwo(Stage stage) {
		this.stage = stage;
	}

	public Scene getScene() throws IOException {

		ViewTwoController viewTwoController = new ViewTwoController(stage);

		FXMLLoader fxmlLoader = new FXMLLoader(MainAppMap.class.getResource(
				"map-test/test" +
						"-small.fxml"));
		fxmlLoader.setController(viewTwoController);
		Scene scene = new Scene(fxmlLoader.load(), WindowSize.MAIN.HEIGHT,
				WindowSize.MAIN.WIDTH);

		return scene;
	}

}
