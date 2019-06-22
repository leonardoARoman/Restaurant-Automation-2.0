package com.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.restaurantnetworkapp.Order;
import io.grpc.restaurantnetworkapp.Response;
import io.grpc.restaurantnetworkapp.RestaurantServiceGrpc;
import io.grpc.restaurantnetworkapp.Table;

public class KitchenClient {
	private static final Logger logger = Logger.getLogger(HostClient.class.getName());
	private final ManagedChannel channel;
	private final RestaurantServiceGrpc.RestaurantServiceBlockingStub blockingStub;
	private static String[] status = {"CLEAN","TAKEN","DIRTY"};

	public KitchenClient(String host, int port) 
	{
		// Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
		// needing certificates.
		this(ManagedChannelBuilder.forAddress(host, port)
				.usePlaintext()
				.build());
	}

	KitchenClient(ManagedChannel channel) 
	{
		this.channel = channel;
		blockingStub = RestaurantServiceGrpc.newBlockingStub(channel);
	}

	public void postOrder(Order order)
	{
		Response response = blockingStub.order(order);
		logger.info(response.getMessage());
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
		if(order.getIsReady()) {
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


	public static void main(String[] args) {
		KitchenClient clientStub = new KitchenClient("192.168.1.11",8080);

		Table table;
		/*for(int i = 0; i < 5; i++) {
			table = Table
					.newBuilder()
					.setTableID(i)
					.setStatus(Table.TableState.TAKEN)
					.build();

			Order order = Order
					.newBuilder()
					.setOrderID(i)
					.setOrderNo(i)
					.setIsReady(false)
					.setTable(table)
					.setMessage(i+": Poter House, Medium Raer")
					.putOrder("Poter House", "Medium Raer")
					.build();
			clientStub.postOrder(order);
		}*/
		Order order = Order
				.newBuilder()
				.setOrderID(0)
				.setOrderNo(0)
				.setIsReady(true)
				.setTable(Table.newBuilder().build())
				.setMessage(0+": Poter House, Medium Raer")
				.putOrder("Poter House", "Medium Raer")
				.build();
		clientStub.postOrder(order);

		System.out.println("\n");
		logger.info("Orders in queue.");
		ArrayList<Order> orders = clientStub.getOrdersInQueue();
		for(Order o: orders) {
			System.out.println("Order number "+o.getMessage());
		}
		/*
		Order order1 = Order
				.newBuilder()
				.setOrderID(order.getOrderID())
				.setOrderNo(order.getOrderNo())
				.setIsReady(true)
				.setTable(order.getTable())
				.putOrder("Poter House", "Medium Raer")
				.build();
		clientStub.postOrder(order1);
		clientStub.getOrderStatus(order1.getOrderID());
		 */
	}
}
