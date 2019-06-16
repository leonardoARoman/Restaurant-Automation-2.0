package com.client;

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
		logger.info("Will try to create order...");
		Response response = blockingStub.order(order);
		logger.info(response.getMessage());
	}

	public void getOrderStatus(int orderId) {
		logger.info("Will try to get order update...");
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
	
	public static void main(String[] args) {
		KitchenClient clientStub = new KitchenClient("10.0.0.107",8080);
		Table table = Table
				.newBuilder()
				.setTableID(1)
				.setStatus(Table.TableState.TAKEN)
				.build();
		Order order = Order
				.newBuilder()
				.setOrderID(1)
				.setOrderNo(1)
				.setIsReady(false)
				.setTable(table)
				.putOrder("Poter House", "Medium Raer")
				.build();
		clientStub.postOrder(order);
		clientStub.getOrderStatus(order.getOrderID());
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
	}
}
