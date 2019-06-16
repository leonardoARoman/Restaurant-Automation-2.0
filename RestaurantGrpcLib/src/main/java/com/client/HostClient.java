package com.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.client.HostClient;
import io.grpc.restaurantnetworkapp.Response;
import io.grpc.restaurantnetworkapp.RestaurantServiceGrpc;
import io.grpc.restaurantnetworkapp.Table;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
/**
 * 
 * @author Roman
 * Host API handles stub calls to the service 
 * 1. Update tables
 * 2. Request table state record
 * 
 */
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

	/**
	 * getTable returns Table state record.
	 * @return 
	 */
	public ArrayList<Table> getTables()
	{
		logger.info("Will try to get tables from database...");
		ArrayList<Table> tables = new ArrayList<Table>();
		Iterator<Table> response = null;
		try 
		{
			response = blockingStub.tables(Response
					.newBuilder()
					.setMessage("Get talbe record")
					.build());
		}
		catch (StatusRuntimeException e)
		{
			logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
		}
		while(response.hasNext())
		{
			Table table= response.next();
			tables.add(table);
			logger.info("Table "+table.getTableID()+" status "+status[table.getStatusValue()]);
		}
		return tables;
	}
	
	/**
	 * updateTable updates a table given an id table
	 * returns table state record
	 * @param id
	 * @return
	 */
	public void updateTable(int tableId, int tableState) {
		logger.info("Will try update table "+tableId+"...");
		Iterator<Table> response = null;
		Table table = Table.newBuilder()
				.setTableID(tableId)
				.setStatusValue(tableState)
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
		logger.info("Table "+tableId+" is now "+status[tableState]);
	}
	/*
	public static void main(String[] args)
	{
		HostClient clientStub = new HostClient("10.0.0.107",8080);
		
		clientStub.getTables();
	}
	*/
}
