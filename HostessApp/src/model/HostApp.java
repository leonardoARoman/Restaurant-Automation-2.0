package model;

import java.io.IOException;
import java.net.UnknownHostException;
import controller.HostController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HostApp 
extends Application 
{
	@Override
	public void start(Stage primaryStage) 
			throws IOException 
	{
	    
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass()
				.getResource("/view/diningroom.fxml"));
		AnchorPane root = (AnchorPane)loader.load();


		HostController userController = 
				loader.getController();
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
	public static void main(String[] args) 
			throws UnknownHostException 
	{
		// Get Host IP address for reference.
		launch(args);
	}
}
