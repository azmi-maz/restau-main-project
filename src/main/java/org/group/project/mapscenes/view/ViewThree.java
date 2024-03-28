package org.group.project.mapscenes.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.MainAppMap;
import org.group.project.mapscenes.controller.ViewThreeController;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

public class ViewThree {

	private Stage stage;

	public ViewThree(Stage stage) {
		this.stage = stage;
	}

	public Scene getScene() throws IOException {

		ViewThreeController viewThreeController =
				new ViewThreeController(stage);

		FXMLLoader fxmlLoader = new FXMLLoader(MainAppMap.class.getResource(
				"map-test/test" +
						"-small.fxml"));
		fxmlLoader.setController(viewThreeController);
		Scene scene = new Scene(fxmlLoader.load(), WindowSize.MAIN.HEIGHT,
				WindowSize.MAIN.WIDTH);

		return scene;
	}

}
