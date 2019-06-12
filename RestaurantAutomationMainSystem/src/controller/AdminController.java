package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminController {
	private static Stage stage;

	public void start(Stage mainStage) {
		stage = mainStage;
	}
	
	public void exit() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/hostpanel.fxml"));
		AnchorPane root = (AnchorPane)loader.load();


		HostpanelController userController = 
				loader.getController();
		userController.start(stage);
		
		Scene scene = new Scene(root,600,600);
		stage.setScene(scene);
		stage.show();
	}
}
