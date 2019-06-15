import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import com.service.ServerInterceptorHandler;
import com.service.ServiceStub;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.restaurantnetworkapp.Table;

public class ServerApp {
	private static final Logger logger 
	= Logger.getLogger(ServerApp.class.getName());
	private static final int numberOfTables = 12;

	private static Collection<Table> RestaurantTables()
	{
		Collection<Table> tables = new ArrayList<Table>();
		for(int i = 0; i < numberOfTables; i++)
		{
			tables.add(Table
					.newBuilder()
					.setTableID(i+1)
					.setStatus(Table.TableState.CLEAN)
					.build());
		}
		return tables;
	}
	
	public static void main(String[] args) 
			throws IOException, InterruptedException 
	{
		logger.info(InetAddress.getLocalHost().toString());
		Server server = ServerBuilder
				.forPort(8080)
				.addService(ServiceStub.getInstance(RestaurantTables()))
				.intercept(new ServerInterceptorHandler())
				.handshakeTimeout(10, TimeUnit.SECONDS)
				.build();
		server.start();
		logger.info("Server listenning on port "+server.getPort()+"...");
		server.awaitTermination();
	}
}
