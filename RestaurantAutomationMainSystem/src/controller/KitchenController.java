package controller;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.MySqlManager;

/**
 * 
 * @author leonardoroman
 *
 */
public class KitchenController {

	private MySqlManager mysql;
	private static Stage stage;
	private ObservableList<String> hotLineObsList1,hotLineObsleList2,hotLineObsList3,hotLineObsList4,saladLineObsList1,saladLineObsList2;

	@FXML
	private ListView<String> orderList1,orderList2,orderList3,orderList4,saladOrderList1,saladOrderList2;

	/**
	 * 
	 * @param mainStage
	 */
	public void start(Stage mainStage) {
		stage = mainStage;
	}

	public void register1() {

	}

	public void register2() {

	}

	public void register3() {

	}

	public void register4() {

	}

	public void register5() {

	}

	public void register6() {

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
