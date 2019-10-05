package com.RestaurantGrpcLib;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.api.HostClient;
import com.service.ServerInterceptorHandler;
import com.service.ServiceStub;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.restaurantnetworkapp.Table;

class ServiceStubTest {

	static Server server;

	// Create and start up a server.
	@BeforeAll
	static void initialize() {
		server = null;
		try {
			server = ServerBuilder
					.forPort(8080)
					.addService(ServiceStub.getInstance())
					.intercept(new ServerInterceptorHandler())
					.handshakeTimeout(10, TimeUnit.SECONDS)
					.build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test server connection state
	 */
	@Test
	@DisplayName("Server Connection Test")
	void serverTest() {
		assertEquals(false,server.isShutdown(),"Server should be live.");
	}

	@Nested
	class HostessClientTest {

		@Test
		@DisplayName("Hostess Connection Test")
		void serverTest() {

			String localHost = "";
			try {
				String host[] = InetAddress.getLocalHost().toString().split("/");
				localHost += host[1];

			} catch (UnknownHostException e) {
				e.printStackTrace();
			}

			Collection<Table> tables = new ArrayList<Table>();
			for(int i = 0; i < 12; i++) {
				tables.add(Table
						.newBuilder()
						.setTableID(i+1)
						.setStatus(Table.TableState.CLEAN)
						.build());
			}
			
			HostClient client = HostClient.connectToServer(localHost, 8080);
			client.setTables(tables);

			assertEquals(false,server.isShutdown(),"The server is online.");
			assertEquals(12,client.getTables().size(),"The number of tables should be 12.");
		
			client.getTables().forEach(t->assertEquals(Table.TableState.CLEAN,t.getStatus(),"All tables should be clean"));
		}
	}

	class KitchenClientTest {
		@Test
		@DisplayName("ServerConnectionTest")
		void serverTest() {

		}
	}

	class WaiterClientTest {

		@Test
		@DisplayName("ServerConnectionTest")
		void serverTest() {

		}
	}
	
	@AfterAll
	@DisplayName("Shutdown Server After All Tests")
	static void shutdownServer() {
		server.shutdown();
		assertEquals(true,server.isShutdown(),"Server should be off.");
	}
}
