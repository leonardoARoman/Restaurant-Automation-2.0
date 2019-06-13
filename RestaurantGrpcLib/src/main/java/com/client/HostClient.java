package com.client;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.client.HostClient;
import io.grpc.restaurantnetworkapp.Response;
import io.grpc.restaurantnetworkapp.RestaurantServiceGrpc;
import io.grpc.restaurantnetworkapp.Table;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
 
public class HostClient {
	private static final Logger logger = Logger.getLogger(HostClient.class.getName());
	private final ManagedChannel channel;
	private final RestaurantServiceGrpc.RestaurantServiceBlockingStub blockingStub;
	private static String[] status = {"CLEAN","TAKEN","DIRTY"};

	public HostClient(String host, int port) 
	{
		// Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
		// needing certificates.
		this(ManagedChannelBuilder.forAddress(host, port)
				.usePlaintext()
				.build());
	}

	HostClient(ManagedChannel channel) 
	{
		this.channel = channel;
		blockingStub = RestaurantServiceGrpc.newBlockingStub(channel);
	}

	public Response getResponse(int tableId, int stateValue)
	{
		logger.info("Will try to create table "+tableId+" and add it to restaurant...");
		Response response;
		Table table = Table.newBuilder()
				.setTableID(tableId)
				.setStatusValue(stateValue)
				.build();
		try 
		{
			response = blockingStub.add(table);
		}
		catch (StatusRuntimeException e)
		{
			logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
			response = Response.newBuilder()
					.setMessage("Error: attempt to create table "+tableId+" failed!")
					.build();
		}

		logger.info("Table "+table.getTableID()+" created, status "+status[table.getStatusValue()]);
		return response;
	}
}
