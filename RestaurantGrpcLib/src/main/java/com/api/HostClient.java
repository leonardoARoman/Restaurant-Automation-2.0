package com.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import io.grpc.restaurantnetworkapp.Response;
import io.grpc.restaurantnetworkapp.RestaurantServiceGrpc;
import io.grpc.restaurantnetworkapp.Table;
import io.grpc.restaurantnetworkapp.TableRecord;
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
	private static HostClient hostessChannel;
	private static RestaurantServiceGrpc.RestaurantServiceBlockingStub blockingStub;
	private static RestaurantServiceGrpc.RestaurantServiceStub newStub;
	private static String[] status = {"CLEAN","TAKEN","DIRTY"};

	/**
	 * 
	 * @param host
	 * @param port
	 */
	private HostClient(String host, int port) 
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
	private HostClient(ManagedChannel channel) 
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
	public static HostClient connectToServer(String host, int port) {
		if(hostessChannel == null) {
			hostessChannel = new HostClient(host,port);
		}
		return hostessChannel;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return hostessChannel != null;
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

	public ArrayList<Table> setTables(Collection<Table> tables){
		logger.info("Will try to set tables and return the list of tables...");
		ArrayList<Table> tableList = new ArrayList<Table>();
		Iterator<Table> response = null;
		try {
			response = blockingStub.setup(TableRecord
					.newBuilder()
					.addAllTbl(tables)
					.build());
		}catch (StatusRuntimeException e) {
			logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
		}
		while(response.hasNext()) {
			Table table = response.next();
			tableList.add(table);
			//logger.info("Table "+table.getTableID()+" status "+status[table.getStatusValue()]);
		}
		return tableList;
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
		try {
			response = blockingStub.tables(Response
					.newBuilder()
					.setMessage("Get talbe record")
					.build());
		}
		catch (StatusRuntimeException e) {
			logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
		}
		while(response.hasNext()) {
			Table table= response.next();
			tables.add(table);
			//logger.info("Table "+table.getTableID()+" status "+status[table.getStatusValue()]);
		}
		return tables;
	}
}
