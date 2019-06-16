package model;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Logger;
import controller.KitchenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class KitchenApp extends Application {
	private static final Logger logger = Logger
			.getLogger(KitchenApp.class.getName());
	
	@Override
	public void start(Stage primaryStage) throws IOException 
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass()
				.getResource("/view/kitchen.fxml"));
		AnchorPane root = (AnchorPane)loader.load();

		KitchenController userController = loader.getController();
		userController.start(primaryStage);

		Scene scene = new Scene(root,600,600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * 
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException 
	{
		launch(args);
	}
}
