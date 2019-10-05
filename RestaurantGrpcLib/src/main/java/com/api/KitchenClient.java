package com.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.restaurantnetworkapp.Order;
import io.grpc.restaurantnetworkapp.Response;
import io.grpc.restaurantnetworkapp.RestaurantServiceGrpc;

public class KitchenClient {
	private static final Logger logger = Logger.getLogger(HostClient.class.getName());
	private final RestaurantServiceGrpc.RestaurantServiceBlockingStub blockingStub;
	private static RestaurantServiceGrpc.RestaurantServiceStub newStub;
	private static KitchenClient kitchenChannel;
	private static String[] status = {"CLEAN","TAKEN","DIRTY"};
	
	/**
	 * 
	 * @param host
	 * @param port
	 */
	private KitchenClient(String host, int port) 
	{
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
	private KitchenClient(ManagedChannel channel) 
	{
		blockingStub = RestaurantServiceGrpc.newBlockingStub(channel);
		newStub = RestaurantServiceGrpc.newStub(channel);
	}

	/**
	 * 
	 * @param host
	 * @param port
	 * @return
	 */
	public static KitchenClient connectToServer(String host, int port) {
		if(kitchenChannel == null) {
			kitchenChannel = new KitchenClient(host,port);
		}
		return kitchenChannel;
	}
	
	/**
	 * 
	 * @return
	 */
	public RestaurantServiceGrpc.RestaurantServiceBlockingStub getNewBlockingStub(){
		return blockingStub;
	}
	
	/**
	 * 
	 * @return
	 */
	public RestaurantServiceGrpc.RestaurantServiceStub getNewStub(){
		return newStub;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return kitchenChannel != null;
	}
	/**
	 * For debugging: kitchen does not need order status
	 * @param orderId
	 */
	public void getOrderStatus(int orderId) {
		Order order = blockingStub.status(Response
				.newBuilder()
				.setMessage(""+orderId)
				.build());
		if(order.getIsCompleated()) {
			logger.info("Order "+order.getOrderID()+" is ready.");
		}else {
			logger.info("Order "+order.getOrderID()+" is not ready.");
		}
	}

	/**
	 * Get all orders that are in queue and display them in the monitor
	 * get a serialized format of the list and compare to local one if
	 * different them update.
	 * @return
	 */
	public ArrayList<Order> getOrdersInQueue() {

		Iterator<Order> order = blockingStub.orderqueue(Response
				.newBuilder()
				.setMessage("Get orders")
				.build());
		
		ArrayList<Order> orders = new ArrayList<Order>();
		while(order.hasNext()) { orders.add(order.next()); }
		
		return orders;
	}

	/**
	 * 
	 * @return
	 */
	public static String[] getStatus() {
		return status;
	}
}
