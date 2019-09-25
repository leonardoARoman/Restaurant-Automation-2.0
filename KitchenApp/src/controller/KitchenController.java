package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import com.api.KitchenClient;
import io.grpc.restaurantnetworkapp.Dish;
import io.grpc.restaurantnetworkapp.RecievedOrder;
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
	private ObservableList<String> hotLineObsList1;
	private static final Logger logger = Logger.getLogger(KitchenController.class.getName());
	private KitchenClient kitchenMonitor;
	@FXML
	private ListView<String> orderList1;

	/**
	 * 
	 * @param mainStage
	 */
	public void start(Stage mainStage) {
		kitchenMonitor = KitchenClient.connectToServer("10.0.0.169", 8080);
		stage = mainStage;
		startMonitor();
	}
	
	private void startMonitor() {
		kitchenMonitor
		.getNewStub()
		.orderstream(new StreamObserver<RecievedOrder>() {
			@Override
			public void onNext(RecievedOrder order) {
				List<Dish> dishes = order.getOrder().getDishesList();
				Platform.runLater(()->{
					ArrayList<String> list = new ArrayList<String>();
					dishes.forEach(d->list.add(d.getName()));
					hotLineObsList1 = FXCollections.observableArrayList(list);
					orderList1.setItems(hotLineObsList1);	
				});
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
	
	public void clearMonitor() {
		System.out.println("clearMonitor pressed");
	}
}
