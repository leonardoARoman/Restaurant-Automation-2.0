package com.api;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import io.grpc.restaurantnetworkapp.RestaurantServiceGrpc;
import io.grpc.restaurantnetworkapp.Table;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class WaiterClient {
	private static final Logger logger = Logger.getLogger(WaiterClient.class.getName());
	private final RestaurantServiceGrpc.RestaurantServiceBlockingStub blockingStub;
	private static WaiterClient waiterClient;
	
	/**
	 * 
	 * @param host
	 * @param port
	 */
	private WaiterClient(String host, int port) {
		// Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
		// needing certificates.
		this(ManagedChannelBuilder.forAddress(host, port)
				.usePlaintext()
				.build());
	}
	
	/**
	 * 
	 * @param channel
	 */
	private WaiterClient(ManagedChannel channel) {
		blockingStub = RestaurantServiceGrpc.newBlockingStub(channel);
	}

	/**
	 * 
	 * @param host
	 * @param port
	 * @return
	 */
	public WaiterClient connectToServer(String host, int port) {
		return waiterClient!=null?waiterClient:new WaiterClient(host,port);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Iterator<Table> getResponse(int id) {
		logger.info("Will try update table "+id+"...");
		Iterator<Table> response = null;
		/*Table table = Table.newBuilder()
				.setTableID(id)
				.setStatus(Table.TableState.DIRTY)
				.build();*/
		try 
		{
			//response = blockingStub.update(table);
		} 
		catch (StatusRuntimeException e) 
		{
			logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
			response =  null;
		}
		return response;
	}

	/**
	 * 
	 * @return
	 */
	public RestaurantServiceGrpc.RestaurantServiceBlockingStub getBlockingStub() {
		return blockingStub;
	}
}
