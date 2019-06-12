package model;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

public class TableAction {

	private TableAction() {}

	private void tableNumber(int number) {
		
		switch(number) {
		case 1:
			System.out.println("Hi i was clicked. What's up?");
			int action = tableAction();

			if(action == 0) {
				return;
			}else if(action == 1) {
				//table1.setImage(TableState.ocupiedTable(url+"t1.png"));
				//floor1_table_1.setImage(TableState.ocupiedTable(url+"t1.png"));
				return;
			} else if(action == 2) {
				return;
			} else if(action == 0) {
				return;
			}
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			break;
		case 7:
			break;
		case 8:
			break;
		case 9:
			break;
		case 10:
			break;
		case 11:
			break;
		case 12:
			break;
		case 13:
			break;
		case 14:
			break;
		case 15:
			break;
		case 16:
			break;
		case 17:
			break;
		case 18:
			break;
		case 19:
			break;
		case 20:
			break;
		case 21:
			break;
		case 22:
			break;
		case 23:
			break;
		case 24:
			break;
		default: System.out.println("Invalid month");
			break;
		}
	}
	/**
	 * 
	 * @return
	 */
	private int tableAction() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog with Custom Actions");
		alert.setHeaderText("Look, a Confirmation Dialog with Custom Actions");
		alert.setContentText("Choose your option.");

		ButtonType buttonTypeOne = new ButtonType("Seat");
		ButtonType buttonTypeTwo = new ButtonType("Ready");
		ButtonType buttonTypeThree = new ButtonType("Dirty");
		ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeCancel);

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
		} 
		return 0;
	}

}
