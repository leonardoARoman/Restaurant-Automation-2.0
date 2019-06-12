package controller;

import java.io.IOException;
import java.util.Optional;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ViewOrderController {

	private FXMLLoader loader;
	private static Stage stage;

	@FXML
	private AnchorPane mainPain;

	public void start(Stage mainStage) {
		stage = mainStage;
		//loader = HostpanelController.getLoader();
	}

	public void editOrder() throws IOException {
		// For management purposes.
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Login Dialog");
		dialog.setHeaderText("Enter credentials");

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField username = new TextField();
		username.setPromptText("Username");
		PasswordField password = new PasswordField();
		password.setPromptText("Password");

		grid.add(new Label("Username:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Password:"), 0, 1);
		grid.add(password, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = (Node) dialog.getDialogPane().lookupButton(loginButtonType);
		((javafx.scene.Node) loginButton).setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		username.textProperty().addListener((observable, oldValue, newValue) -> {
			((javafx.scene.Node) loginButton).setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> username.requestFocus());

		// Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				return new Pair<>(username.getText(), password.getText());
			}
			return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent(usernamePassword -> {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/editorder.fxml"));
			try {
				mainPain = (AnchorPane)loader.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EditOrderController editOrderController = 
					loader.getController();
			editOrderController.start(stage);
			Scene scene = new Scene(mainPain,600,600);
			stage.setScene(scene);
			stage.show();
			System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
		});
	}

	public void goBack() throws IOException {
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/floor.fxml"));
		mainPain = (AnchorPane)loader.load();
		FloorController floorcontroller = 
				loader.getController();
		floorcontroller.start(stage);
		Scene scene = new Scene(mainPain,600,600);
		stage.setScene(scene);
		stage.show();
	}
}
