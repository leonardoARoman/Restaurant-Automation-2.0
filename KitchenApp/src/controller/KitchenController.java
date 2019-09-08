package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import com.client.KitchenClient;

import io.grpc.ManagedChannelBuilder;
import io.grpc.restaurantnetworkapp.Dish;
import io.grpc.restaurantnetworkapp.Order;
import io.grpc.restaurantnetworkapp.RestaurantServiceGrpc;
import io.grpc.restaurantnetworkapp.SendOrder;
import io.grpc.stub.StreamObserver;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * 
 * @author leonardoroman
 *
 */
public class KitchenController {

	private static Stage stage;
	private ArrayList<Order> orders;
	private ObservableList<String> hotLineObsList1,hotLineObsleList2,hotLineObsList3,hotLineObsList4,saladLineObsList1,saladLineObsList2;
	//private static KitchenClient clientSub;
	private static final Logger logger = Logger.getLogger(KitchenController.class.getName());
	private RestaurantServiceGrpc.RestaurantServiceStub orderStream;

	@FXML
	private ListView<String> orderList1,orderList2,orderList3,orderList4,saladOrderList1,saladOrderList2;

	/**
	 * 
	 * @param mainStage
	 */
	public void start(Stage mainStage) {
		orderStream = RestaurantServiceGrpc
				.newStub(ManagedChannelBuilder.forAddress("192.168.1.2", 8080)
				.usePlaintext()
				.build());
		
		stage = mainStage;
	}

	public void register1() {
		orderStream.orderstream(new StreamObserver<SendOrder>() {
			@Override
			public void onNext(SendOrder order) {
				// TODO Auto-generated method stub
				/*List<Dish> dishes = order.getOrder().getDishesList();
				Platform.runLater(()->{
					ArrayList<String> list = new ArrayList<String>();
					dishes.forEach(d->list.add(d.getName()));
					hotLineObsleList2 = FXCollections.observableArrayList(list);
					orderList2.setItems(hotLineObsleList2);	
				});*/
			}
			@Override
			public void onError(Throwable arg0) {
				// TODO Auto-generated method stub

			}
			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub

			}
		});
	}

	public void register2() {
		/*orders = clientSub.getOrdersInQueue();
		ArrayList<String> list = new ArrayList<String>();
		for(Order o: orders) {
			list.add(o.getMessage());
		}
		hotLineObsList1 = FXCollections.observableArrayList(list);
		orderList1.setItems(hotLineObsList1);*/
	}

	public void register3() {

	}

	public void register4() {

	}

	public void register5() {

	}

	public void register6() {

	}
}
