package org.group.project.mapscenes.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.group.project.MainAppMap;
import org.group.project.mapscenes.controller.MainController;
import org.group.project.scenes.WindowSize;

import java.io.IOException;

public class MainView implements ViewMaker {
	
	private Stage stage;

	public MainView(Stage stage) {

		this.stage = stage;
	}

	@Override
	public Scene getScene() throws IOException {

		MainController mainController = new MainController(stage);

		FXMLLoader fxmlLoader = new FXMLLoader(MainAppMap.class.getResource(
				"map-test/test" +
						"-main.fxml"));
		fxmlLoader.setController(mainController);
		Scene scene = new Scene(fxmlLoader.load(), WindowSize.MAIN.HEIGHT,
				WindowSize.MAIN.WIDTH);
		
		return scene;
	}

}
