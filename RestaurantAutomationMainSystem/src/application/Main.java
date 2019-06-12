package application;
	
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import controller.HostpanelController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


/**
 * 
 * @author leonardoroman
 *
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
	    
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/hostpanel.fxml"));
		AnchorPane root = (AnchorPane)loader.load();


		HostpanelController userController = 
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
	public static void main(String[] args) throws UnknownHostException {
		// Get Host IP address for reference.
		System.out.println("Host IP address: "+InetAddress.getLocalHost().getHostAddress());
		launch(args);
	}
}
