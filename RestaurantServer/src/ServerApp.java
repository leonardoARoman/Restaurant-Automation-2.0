import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import com.service.ServerInterceptorHandler;
import com.service.ServiceStub;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class ServerApp {
	private static final Logger logger 
	= Logger.getLogger(ServerApp.class.getName());

	public static void main(String[] args) 
			throws IOException, InterruptedException 
	{
		logger.info(InetAddress.getLocalHost().toString());
		Server server = ServerBuilder
				.forPort(8080)
				.addService(ServiceStub.getInstance())
				.intercept(new ServerInterceptorHandler())
				.handshakeTimeout(10, TimeUnit.SECONDS)
				.build();
		server.start();
		logger.info("Server listenning on port "+server.getPort()+"...");
		server.awaitTermination();
	}
}
