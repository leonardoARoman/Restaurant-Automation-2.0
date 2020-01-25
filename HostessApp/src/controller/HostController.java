package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.api.HostClient;

import io.grpc.restaurantnetworkapp.ReceivedTable;
import io.grpc.restaurantnetworkapp.Table;
import io.grpc.stub.StreamObserver;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.DialogBoxHelper;
import model.TableState;

public class HostController {
	private static HostClient tableMonitor;
	private static final Logger logger = Logger.getLogger(HostController.class.getName());
	private ObservableList<String> specialsObsList,ffObsList,desertObsList,drinksObsList;

	@FXML
	private ImageView table1,table2,table3,table4,table5,table6,table7,table8,table9,table10,table11,table12;
	@FXML
	private ImageView table20,table21,table22,table23,table24,table25,table26,table27,table28,table29,table30,table31;
	@FXML
	private TextField name,lastName,phone,time;
	@FXML
	private ListView<String> specialslList, fastfoodList, desertList, drinkList;
	@FXML
	private AnchorPane mainPain;

	private ArrayList<Table> tables;
	private ImageView[] button;
	private String url = "images/";
	private String[] URL = {url+"t1.png",url+"t2.png",url+"t3.png",url+"t4.png",url+"t5.png",url+"t6.png",url+"t7.png",url+"t8.png",url+"t9.png",url+"t10.png",url+"t11.png",url+"t12.png",
			url+"t20.png",url+"t21.png",url+"t22.png",url+"t23.png",url+"t24.png",url+"t25.png",url+"t26.png",url+"t27.png",url+"t28.png",url+"t29.png",url+"t30.png",url+"t31.png"};
	private static Stage stage;
	private  FXMLLoader loader;

	public void start(Stage primaryStage) throws IOException {
		// TODO Auto-generated method stub
		tableMonitor = HostClient.connectToServer("192.168.1.7",8080);
		tables = new ArrayList<Table>();
		button = new ImageView[24];

		new Thread(()->{
			try {
				updateDiningRoom();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();

		stage = primaryStage;
		loader = new FXMLLoader();
	}
	/**
	 * 
	 */
	public void showFastFood(){
		fastfoodList.setItems(ffObsList);
	}

	/**
	 * 
	 */
	public void showSpecials() {
		specialslList.setItems(specialsObsList);
	}

	/**
	 * 
	 */
	public void showDesert() {
		desertList.setItems(desertObsList);
	}

	/**
	 * 
	 */
	public void showDrinks() {
		drinkList.setItems(drinksObsList);
	}

	/**
	 * 
	 */
	public void addReservation() {
		if(name.getText().isEmpty() || lastName.getText().isEmpty() || phone.getText().isEmpty()) {
			DialogBoxHelper.emptyFieldException();
			return;
		}else {
			// Reset all TextFields back to blank
			name.setText("");
			lastName.setText("");
			phone.setText("");	
			DialogBoxHelper.succesfulTransactionMessage();
		}
	}

	private void updateTable(int tableNumber, int action ) {
		Table updateTable = Table.newBuilder()
				.setTableID(tableNumber)
				.setStatusValue(action)
				.build();

		tableMonitor.getNewStub().updatetable(new StreamObserver<ReceivedTable>() {
			@Override
			public void onNext(ReceivedTable table) {
				int tableId = (int)table.getTable().getTableID() - 1;
				Platform.runLater(()->{
					switch(table.getTable().getStatusValue()) {
					case 0:
						try {
							button[tableId].setImage(TableState.cleanTable(URL[tableId]));
						} catch (IOException e) {
							e.printStackTrace();
						}	
						break;
					case 1:
						try {
							button[tableId].setImage(TableState.ocupiedTable(URL[tableId]));
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					case 2:
						try {
							button[tableId].setImage(TableState.dirtyTable(URL[tableId]));
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					default:
						break;
					}
				});
			}

			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Throwable arg0) {
				// TODO Auto-generated method stub

			}
		}).onNext(updateTable);
	}
	/**
	 * 
	 * @throws IOException
	 */
	public void table1ImageViewListener() throws IOException {
		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table1.setImage(TableState.ocupiedTable(url+"t1.png"));
			updateTable(1,action);
			return;
		} else if(action == 2) {
			table1.setImage(TableState.dirtyTable(url+"t1.png"));
			updateTable(1,action);
			return;
		} else if(action == 0) {
			table1.setImage(TableState.cleanTable(url+"t1.png"));
			updateTable(1,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table2ImageViewListener() throws IOException{
		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table2.setImage(TableState.ocupiedTable(url+"t2.png"));
			updateTable(2,action);
			return;
		} else if(action == 2) {
			table2.setImage(TableState.dirtyTable(url+"t2.png"));
			updateTable(2,action);
			return;
		} else if(action == 0) {
			table2.setImage(TableState.cleanTable(url+"t2.png"));
			updateTable(2,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table3ImageViewListener() throws IOException {
		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table3.setImage(TableState.ocupiedTable(url+"t3.png"));
			updateTable(3,action);
			return;
		} else if(action == 2) {
			table3.setImage(TableState.dirtyTable(url+"t3.png"));
			updateTable(3,action);
			return;
		} else if(action == 0) {
			table3.setImage(TableState.cleanTable(url+"t3.png"));
			updateTable(3,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table4ImageViewListener() throws IOException {
		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table4.setImage(TableState.ocupiedTable(url+"t4.png"));
			updateTable(4,action);
			return;
		} else if(action == 2) {
			table4.setImage(TableState.dirtyTable(url+"t4.png"));
			updateTable(4,action);
			return;
		} else if(action == 0) {
			table4.setImage(TableState.cleanTable(url+"t4.png"));
			updateTable(4,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table5ImageViewListener() throws IOException {
		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table5.setImage(TableState.ocupiedTable(url+"t5.png"));
			updateTable(5,action);
			return;
		} else if(action == 2) {
			table5.setImage(TableState.dirtyTable(url+"t5.png"));
			updateTable(5,action);
			return;
		} else if(action == 0) {
			table5.setImage(TableState.cleanTable(url+"t5.png"));
			updateTable(5,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table6ImageViewListener() throws IOException {

		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table6.setImage(TableState.ocupiedTable(url+"t6.png"));
			updateTable(6,action);
			return;
		} else if(action == 2) {
			table6.setImage(TableState.dirtyTable(url+"t6.png"));
			updateTable(6,action);
			return;
		} else if(action == 0) {
			table6.setImage(TableState.cleanTable(url+"t6.png"));
			updateTable(6,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table7ImageViewListener() throws IOException {

		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table7.setImage(TableState.ocupiedTable(url+"t7.png"));
			updateTable(7,action);
			return;
		} else if(action == 2) {
			table7.setImage(TableState.dirtyTable(url+"t7.png"));
			updateTable(7,action);
			return;
		} else if(action == 0) {
			table7.setImage(TableState.cleanTable(url+"t7.png"));
			updateTable(7,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table8ImageViewListener() throws IOException {

		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table8.setImage(TableState.ocupiedTable(url+"t8.png"));
			updateTable(8,action);
			return;
		} else if(action == 2) {
			table8.setImage(TableState.dirtyTable(url+"t8.png"));
			updateTable(8,action);
			return;
		} else if(action == 0) {
			table8.setImage(TableState.cleanTable(url+"t8.png"));
			updateTable(8,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table9ImageViewListener() throws IOException {

		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table9.setImage(TableState.ocupiedTable(url+"t9.png"));
			updateTable(9,action);
			return;
		} else if(action == 2) {
			table9.setImage(TableState.dirtyTable(url+"t9.png"));
			updateTable(9,action);
			return;
		} else if(action == 0) {
			table9.setImage(TableState.cleanTable(url+"t9.png"));
			updateTable(9,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table10ImageViewListener() throws IOException {

		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table10.setImage(TableState.ocupiedTable(url+"t10.png"));
			updateTable(10,action);
			return;
		} else if(action == 2) {
			table10.setImage(TableState.dirtyTable(url+"t10.png"));
			updateTable(10,action);
			return;
		} else if(action == 0) {
			table10.setImage(TableState.cleanTable(url+"t10.png"));
			updateTable(10,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table11ImageViewListener() throws IOException {

		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table11.setImage(TableState.ocupiedTable(url+"t11.png"));
			updateTable(11,action);
			return;
		} else if(action == 2) {
			table11.setImage(TableState.dirtyTable(url+"t11.png"));
			updateTable(11,action);
			return;
		} else if(action == 0) {
			table11.setImage(TableState.cleanTable(url+"t11.png"));
			updateTable(11,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table12ImageViewListener() throws IOException {

		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table12.setImage(TableState.ocupiedTable(url+"t12.png"));
			updateTable(12,action);
			return;
		} else if(action == 2) {
			table12.setImage(TableState.dirtyTable(url+"t12.png"));
			updateTable(12,action);
			return;
		} else if(action == 0) {
			table12.setImage(TableState.cleanTable(url+"t12.png"));
			updateTable(12,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table20ImageViewListener() throws IOException {

		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table20.setImage(TableState.ocupiedTable(url+"t20.png"));
			updateTable(13,action);
			return;
		} else if(action == 2) {
			table20.setImage(TableState.dirtyTable(url+"t20.png"));
			updateTable(13,action);
			return;
		} else if(action == 0) {
			table20.setImage(TableState.cleanTable(url+"t20.png"));
			updateTable(13,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table21ImageViewListener() throws IOException {

		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table21.setImage(TableState.ocupiedTable(url+"t21.png"));
			updateTable(14,action);
			return;
		} else if(action == 2) {
			table21.setImage(TableState.dirtyTable(url+"t21.png"));
			updateTable(14,action);
			return;
		} else if(action == 0) {
			table21.setImage(TableState.cleanTable(url+"t21.png"));
			updateTable(14,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table22ImageViewListener() throws IOException {

		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table22.setImage(TableState.ocupiedTable(url+"t22.png"));
			updateTable(15,action);
			return;
		} else if(action == 2) {
			table22.setImage(TableState.dirtyTable(url+"t22.png"));
			updateTable(15,action);
			return;
		} else if(action == 0) {
			table22.setImage(TableState.cleanTable(url+"t22.png"));
			updateTable(15,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table23ImageViewListener() throws IOException {

		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table23.setImage(TableState.ocupiedTable(url+"t23.png"));
			updateTable(16,action);
			return;
		} else if(action == 2) {
			table23.setImage(TableState.dirtyTable(url+"t23.png"));
			updateTable(16,action);
			return;
		} else if(action == 0) {
			table23.setImage(TableState.cleanTable(url+"t23.png"));
			updateTable(16,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table24ImageViewListener() throws IOException {

		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table24.setImage(TableState.ocupiedTable(url+"t24.png"));
			updateTable(17,action);
			return;
		} else if(action == 2) {
			table24.setImage(TableState.dirtyTable(url+"t24.png"));
			updateTable(17,action);
			return;
		} else if(action == 0) {
			table24.setImage(TableState.cleanTable(url+"t24.png"));
			updateTable(17,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table25ImageViewListener() throws IOException {

		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table25.setImage(TableState.ocupiedTable(url+"t25.png"));
			updateTable(18,action);
			return;
		} else if(action == 2) {
			table25.setImage(TableState.dirtyTable(url+"t25.png"));
			updateTable(18,action);
			return;
		} else if(action == 0) {
			table25.setImage(TableState.cleanTable(url+"t25.png"));
			updateTable(18,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table26ImageViewListener() throws IOException {

		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table26.setImage(TableState.ocupiedTable(url+"t26.png"));
			updateTable(19,action);
			return;
		} else if(action == 2) {
			table26.setImage(TableState.dirtyTable(url+"t26.png"));
			updateTable(19,action);
			return;
		} else if(action == 0) {
			table26.setImage(TableState.cleanTable(url+"t26.png"));
			updateTable(19,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table27ImageViewListener() throws IOException {

		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table27.setImage(TableState.ocupiedTable(url+"t27.png"));
			updateTable(20,action);
			return;
		} else if(action == 2) {
			table27.setImage(TableState.dirtyTable(url+"t27.png"));
			updateTable(20,action);
			return;
		} else if(action == 0) {
			table27.setImage(TableState.cleanTable(url+"t27.png"));
			updateTable(20,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table28ImageViewListener() throws IOException {

		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table28.setImage(TableState.ocupiedTable(url+"t28.png"));
			updateTable(21,action);
			return;
		} else if(action == 2) {
			table28.setImage(TableState.dirtyTable(url+"t28.png"));
			updateTable(21,action);
			return;
		} else if(action == 0) {
			table28.setImage(TableState.cleanTable(url+"t28.png"));
			updateTable(21,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table29ImageViewListener() throws IOException {

		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table29.setImage(TableState.ocupiedTable(url+"t29.png"));
			updateTable(22,action);
			return;
		} else if(action == 2) {
			table29.setImage(TableState.dirtyTable(url+"t29.png"));
			updateTable(22,action);
			return;
		} else if(action == 0) {
			table29.setImage(TableState.cleanTable(url+"t29.png"));
			updateTable(22,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table30ImageViewListener() throws IOException {

		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table30.setImage(TableState.ocupiedTable(url+"t30.png"));
			updateTable(23,action);
			return;
		} else if(action == 2) {
			table30.setImage(TableState.dirtyTable(url+"t30.png"));
			updateTable(23,action);
			return;
		} else if(action == 0) {
			table30.setImage(TableState.cleanTable(url+"t30.png"));
			updateTable(23,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void table31ImageViewListener() throws IOException {

		int action = DialogBoxHelper.tableAction();

		if(action == 1) {
			table31.setImage(TableState.ocupiedTable(url+"t31.png"));
			updateTable(24,action);
			return;
		} else if(action == 2) {
			table31.setImage(TableState.dirtyTable(url+"t31.png"));
			updateTable(24,action);
			return;
		} else if(action == 0) {
			table31.setImage(TableState.cleanTable(url+"t31.png"));
			updateTable(24,action);
			return;
		} else if(action == 4) {
			//viewOrder();
		}
	}

	/**
	 * back up update if server goes down
	 * @throws IOException
	 */
	public void updateDiningRoom() throws IOException {
		tables = tableMonitor.getTables();
		button[0] = table1;button[1] = table2;button[2] = table3;button[3] = table4;
		button[4] = table5;button[5] = table6;button[6] = table7;button[7] = table8;
		button[8] = table9;button[9] = table10;button[10] = table11;button[11] = table12;
		button[12] = table20;button[13] = table21;button[14] = table22;button[15] = table23;
		button[16] = table24;button[17] = table25;button[18] = table26;button[19] = table27;
		button[20] = table28;button[21] = table29;button[22] = table30;button[23] = table31;

		for(Table table: tables) {			
			int tableId = (int)table.getTableID() - 1;
			if(table.getStatusValue()==0) {
				button[tableId].setImage(TableState.cleanTable(URL[tableId]));	
				continue;
			}else if(table.getStatusValue()==1) {
				button[tableId].setImage(TableState.ocupiedTable(URL[tableId]));
				continue;
			}else if(table.getStatusValue()==2) {
				button[tableId].setImage(TableState.dirtyTable(URL[tableId]));
				continue;
			}
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	/*
	private void viewOrder() throws IOException {
		// For management purposes.
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/vieworder.fxml"));
		mainPain = (AnchorPane)loader.load();
		ViewOrderController viewOrderController = 
				loader.getController();
		viewOrderController.start(stage);
		Scene scene = new Scene(mainPain,600,600);
		stage.setScene(scene);
		stage.show();
	}
	 */
}
