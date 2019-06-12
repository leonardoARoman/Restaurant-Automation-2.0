package controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * 
 * @author leonardoroman
 *
 */
public class HostpanelController {

	@FXML
	private AnchorPane mainPain;

	private static Stage stage;
	private  FXMLLoader loader;
	public void start(Stage mainStage) {
		stage = mainStage;
		loader = new FXMLLoader();
	}

	/**
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void kitchenPanel() throws IOException, ClassNotFoundException{

		loader.setLocation(getClass().getResource("/view/kitchenpanel.fxml"));
		mainPain = (AnchorPane)loader.load();
		KitchenController kitchencontroller = 
				loader.getController();
		kitchencontroller.start(stage);
		Scene scene = new Scene(mainPain,600,600);
		stage.setScene(scene);
		stage.show();

	}


	/**
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void floorPanel() throws IOException, ClassNotFoundException {

		//FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/floor.fxml"));
		mainPain = (AnchorPane)loader.load();
		FloorController floorcontroller = 
				loader.getController();
		floorcontroller.start(stage);
		Scene scene = new Scene(mainPain,600,600);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void clockinout() throws IOException {
		
		//FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/clockintime.fxml"));
		mainPain = (AnchorPane)loader.load();
		ClockInTimeController clockInTimeController = 
				loader.getController();
		clockInTimeController.start(stage);
		Scene scene = new Scene(mainPain,600,600);
		stage.setScene(scene);
		stage.show();

	}

	/**
	 * 
	 * @throws IOException
	 */
	public void adminPanel() throws IOException {

		//FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/admin.fxml"));
		mainPain = (AnchorPane)loader.load();
		AdminController adminController = 
				loader.getController();
		adminController.start(stage);
		Scene scene = new Scene(mainPain,600,600);
		stage.setScene(scene);
		stage.show();

	}
}
/*
public static Stage getStage() {

}
*/