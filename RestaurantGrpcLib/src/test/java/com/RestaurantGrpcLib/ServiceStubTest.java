/*package com.RestaurantGrpcLib;

import static org.junit.jupiter.api.Assertions.assertAll;
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
import com.api.KitchenClient;
import com.service.ServerInterceptorHandler;
import com.service.ServiceStub;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.restaurantnetworkapp.ReceivedTable;
import io.grpc.restaurantnetworkapp.Response;
import io.grpc.restaurantnetworkapp.Table;
import io.grpc.stub.StreamObserver;
import javafx.application.Platform;

class ServiceStubTest {

	static Server server;
	static String localHost;
	static Collection<Table> tablesList;

	// Create and start up a server.
	@BeforeAll
	static void initialize() {
		localHost = "";
		try {
			String host[] = InetAddress.getLocalHost().toString().split("/");
			localHost += host[1];

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		tablesList = new ArrayList<Table>();
		for(int i = 0; i < 12; i++) {
			tablesList.add(Table
					.newBuilder()
					.setTableID(i+1)
					.setStatus(Table.TableState.CLEAN)
					.build());
		}
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
	}*/

	/**
	 * Test server connection state
	 */
	/*@Test
	@DisplayName("Server Connection Test")
	void serverTest() {
		assertEquals(false,server.isShutdown(),"Server should be live.");
	}

	@Nested
	class HostessClientTest {

		@Test
		@DisplayName("Hostess Connection Test")
		void serverTest() {
			HostClient client = HostClient.connectToServer(localHost, 8080);
			assertEquals(true,client.isConnected(),"Host Client not connected");
		}

		@Test
		@DisplayName("Creating Tables Test")
		void setupTablesTest() {
			HostClient client = HostClient.connectToServer(localHost, 8080);
			ArrayList<Table> tables = client.getTables();
			client.setTables(tablesList);
			assertEquals(false,server.isShutdown(),"The server is online.");
			assertEquals(12,tables.size(),"The number of tables should be 12.");
			tables.forEach(t->assertEquals(Table.TableState.CLEAN,t.getStatus(),"Table is not Clean"));
		}

		@Test
		@DisplayName("Changing Table's State Test")
		void setTablesTest() {
			HostClient client = HostClient.connectToServer(localHost, 8080);
			client.setTables(tablesList);
			//StreamObserver<Table> updateTable = 
			Table updateTable = Table.newBuilder()
					.setTableID(0)
					.setStatusValue(1)
					.build();
			client.getNewStub().updatetable(new StreamObserver<ReceivedTable>() {
				@Override
				public void onNext(ReceivedTable table) {
					
				}

				@Override
				public void onCompleted() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onError(Throwable arg0) {
					// TODO Auto-generated method stub

				}
			}).onNext(updateTable);*/
			
			/*updateTable.onNext(Table.newBuilder().setTableID(0).setStatus(Table.TableState.DIRTY).build());
			updateTable.onNext(Table.newBuilder().setTableID(1).setStatus(Table.TableState.TAKEN).build());
			updateTable.onNext(Table.newBuilder().setTableID(2).setStatus(Table.TableState.DIRTY).build());
			updateTable.onNext(Table.newBuilder().setTableID(3).setStatus(Table.TableState.TAKEN).build());*/
			
			/*assertEquals(Table.TableState.DIRTY,
					client.getNewBlockingStub().tablestate(Response.newBuilder().setMessage("0").build()).getStatus());*/
			
			/*assertAll(
					()->assertEquals(Table.TableState.DIRTY,
							client.getNewBlockingStub().tablestate(Response.newBuilder().setMessage("0").build()).getStatus()),
					()->assertEquals(Table.TableState.TAKEN,
							client.getNewBlockingStub().tablestate(Response.newBuilder().setMessage("1").build()).getStatus()),
					()->assertEquals(Table.TableState.DIRTY,
							client.getNewBlockingStub().tablestate(Response.newBuilder().setMessage("2").build()).getStatus()),
					()->assertEquals(Table.TableState.TAKEN,
							client.getNewBlockingStub().tablestate(Response.newBuilder().setMessage("3").build()).getStatus()),
					()->assertEquals(Table.TableState.CLEAN,
							client.getNewBlockingStub().tablestate(Response.newBuilder().setMessage("4").build()).getStatus()));*/			
					
		/*}
	}

	@Nested
	class KitchenClientTest {

		@Test
		@DisplayName("Server Connection Test")
		void serverTest() {
			KitchenClient client = KitchenClient.connectToServer(localHost, 8080);
			assertEquals(true,client.isConnected(),"Kitchen Client not connected");
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
}*/
