package model;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

public class DialogBoxHelper {
private DialogBoxHelper() {}
	
	public static String enterIDnumber() {
		
		TextInputDialog dialog = new TextInputDialog("walter");
		dialog.setTitle("Text Input Dialog");
		dialog.setHeaderText("Look, a Text Input Dialog");
		dialog.setContentText("Please enter your name:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    System.out.println("Your name: " + result.get());
		}

		// The Java 8 way to get the response value (with lambda expression).
		result.ifPresent(name -> System.out.println("Your name: " + name));
		return "";
	}
	
	public static void emptyFieldException() {
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText("Error!");
		alert.setContentText("One or more fields empy!");

		alert.showAndWait();
	}
	
	public static void succesfulTransactionMessage() {
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Reservation saved!");
		alert.showAndWait();
		
	}
	
	public static int tableAction() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog with Custom Actions");
		alert.setHeaderText("Look, a Confirmation Dialog with Custom Actions");
		alert.setContentText("Choose your option.");

		ButtonType buttonTypeOne = new ButtonType("Seat");
		ButtonType buttonTypeTwo = new ButtonType("Dirty");
		ButtonType buttonTypeThree = new ButtonType("Ready");
		ButtonType buttonTypeFour = new ButtonType("Order");
		ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeFour, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne){
			// ... user chose "One"
			return 1;
		} else if (result.get() == buttonTypeTwo) {
			// ... user chose "Two"
			return 2;
		} else if (result.get() == buttonTypeThree) {
			// ... user chose "Three"
			return 3;
		} else if (result.get() == buttonTypeFour) {
			// ... user chose "Three"
			return 4;
		} 
		return 0;
	}
}
