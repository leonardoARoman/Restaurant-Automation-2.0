package com.client;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import io.grpc.restaurantnetworkapp.RestaurantServiceGrpc;
import io.grpc.restaurantnetworkapp.Table;
import com.client.WaiterClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class WaiterClient {
	private static final Logger logger = Logger.getLogger(WaiterClient.class.getName());
	private final ManagedChannel channel;
	private final RestaurantServiceGrpc.RestaurantServiceBlockingStub blockingStub;

	public WaiterClient(String host, int port) {
		// Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
		// needing certificates.
		this(ManagedChannelBuilder.forAddress(host, port)
				.usePlaintext()
				.build());
	}

	/** Construct client for accessing HelloWorld server using the existing channel. */
	WaiterClient(ManagedChannel channel) {
		this.channel = channel;
		blockingStub = RestaurantServiceGrpc.newBlockingStub(channel);
	}

	public Iterator<Table> getResponse(int id) {
		logger.info("Will try update table "+id+"...");
		Iterator<Table> response = null;
		Table table = Table.newBuilder()
				.setTableID(id)
				.setStatus(Table.TableState.DIRTY)
				.build();
		try 
		{
			response = blockingStub.update(table);
		} 
		catch (StatusRuntimeException e) 
		{
			logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
			response =  null;
		}
		return response;
	}
}
