package org.group.project.mapscenes.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.MainAppMap;
import org.group.project.mapscenes.controller.ViewOneController;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

public class ViewOne {

	private Stage stage;

	public ViewOne(Stage stage) {
		this.stage = stage;
	}

	public Scene getScene() throws IOException {

		ViewOneController viewOneController = new ViewOneController(stage);

		FXMLLoader fxmlLoader = new FXMLLoader(MainAppMap.class.getResource(
				"map-test/test" +
						"-small.fxml"));
		fxmlLoader.setController(viewOneController);
		Scene scene = new Scene(fxmlLoader.load(), WindowSize.MAIN.HEIGHT,
				WindowSize.MAIN.WIDTH);

		return scene;
	}

}
