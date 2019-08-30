package com.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.logging.Logger;

import io.grpc.restaurantnetworkapp.Dish;
import io.grpc.restaurantnetworkapp.MakeOrder;
import io.grpc.restaurantnetworkapp.Order;
import io.grpc.restaurantnetworkapp.Response;
import io.grpc.restaurantnetworkapp.RestaurantServiceGrpc;

import com.google.common.base.Optional;
import com.service.ServiceStub;
import io.grpc.restaurantnetworkapp.Table;
import io.grpc.restaurantnetworkapp.TableRecord;
import io.grpc.stub.StreamObserver;

public class ServiceStub extends 
RestaurantServiceGrpc.RestaurantServiceImplBase 
{

	private static final Logger logger = Logger.getLogger(ServiceStub.class.getName());
	private static ServiceStub instance;
	private static Collection<Table> tableRecord;
	private static Collection<Order> orderRecord;
	private static Map<Integer,Order> orderIDs;
	private static Queue<Order> orderQuee;
	private static Collection<Order> orderList;
	//private static String DB = "./src/main/database/RestaurantDB.db";
	private static String URL = "./src/main/database/tableRecord.dat";
	//private static String URL = "./database/tableRecord.dat";
	private static String URL2 = "./src/main/database/orderRecord.dat";

	///////////////////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////////////////
	private ServiceStub() 
	{
		tableRecord = new ArrayList<Table>();		// For table inventory
		orderRecord = new ArrayList<Order>();		// For record storage
		orderIDs = new HashMap<Integer,Order>();	// For quick access transaction
		orderList = new ArrayList<Order>();			// To display in kitchen monitors
		orderQuee = new LinkedList<Order>();		// To remove from queue and update orderlist
	}

	private ServiceStub(Collection<Table> tableRecord)
	{
		this.tableRecord = tableRecord;
		orderRecord = new ArrayList<Order>();
		orderIDs = new HashMap<Integer,Order>();
		orderList = new ArrayList<Order>();
		orderQuee = new LinkedList<Order>();
	}
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public static ServiceStub getInstance() throws IOException 
	{
		if(instance==null) 
		{
			instance = new ServiceStub();
		}
		return instance;
	}
	/**
	 * 
	 * @param tableRecord
	 * @return
	 * @throws IOException
	 */
	public static ServiceStub getInstance(Collection<Table> tableRecord) throws IOException 
	{
		if(instance==null) 
		{
			instance = new ServiceStub(tableRecord);
			try 
			{
				updateTableRecord();
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		return instance;
	}

	///////////////////////////////////////////////////////////////////////////////////////
	// setup: not being used
	///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void setup(TableRecord tablerecord, 
			StreamObserver<Table> responseObserver) 
	{
		responseObserver.onCompleted();
	}

	///////////////////////////////////////////////////////////////////////////////////////
	// add: To add tables to the restaurant
	///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void add(Table table,
			StreamObserver<Response> responseObserver) 
	{
		tableRecord.add(table);
		try 
		{
			updateTableRecord();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}

		responseObserver.onNext(Response
				.newBuilder()
				.setMessage("Table "+table.getTableID()+" added to Restaurant record")
				.build());
		
		responseObserver.onCompleted();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	// update: Updates the state of a given table; Clean, Taken or Dirty
	///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void update(Table tableUpdate, 
			StreamObserver<Table> responseObserver) 
	{
		Table table = tableRecord
				.stream()
				.filter(t->t.getTableID()==tableUpdate.getTableID())
				.findFirst()
				.orElse(null);
		
		if(!table.equals(null)) {
			tableRecord.remove(table);
			tableRecord.add(tableUpdate);	
			try 
			{
				updateTableRecord();
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
			tableRecord.forEach(t->responseObserver.onNext(t));
		}
		responseObserver.onCompleted();
	}

	///////////////////////////////////////////////////////////////////////////////////////
	// tables: 
	///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void tables(Response request,
			StreamObserver<Table> responseObserver) {
		for(Table table: tableRecord) { responseObserver.onNext(table); }

		responseObserver.onCompleted();
	}

	///////////////////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void order(Order order, 
			StreamObserver<Response> responseObserver) {
		Response response = null;
		// If the order exists then the order is being updated
		if(orderIDs.containsKey(order.getOrderID())) {
			// Remove old order from map by order ID.
			//orderRecord.remove(orderIDs.get(order.getOrderID()));
			orderQuee.remove(orderIDs.get(order.getOrderID()));
			orderIDs.remove(order.getOrderID());
			// Add new order in argument.
			orderRecord.add(order);
			orderIDs.put(order.getOrderID(),order);

			response = Response
					.newBuilder()
					.setMessage("Order number "+order.getOrderNo()+" is ready.")
					.build();
		} else {
			orderIDs.put(order.getOrderID(),order);
			orderQuee.add(order);
			orderRecord.add(order);
			orderList.add(order);
			response = Response
					.newBuilder()
					.setMessage("Order number "+order.getOrderNo()+" is inqueue.")
					.build();
		}

		try {
			saveOrder();
		} catch (IOException e) {
			e.printStackTrace();
		}
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	///////////////////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void orderqueue(Response request,
			StreamObserver<Order> responseObserver) {
		if(request.getMessage().equals("remove")) {
			Order order = orderQuee.remove();
			//To do: notify waiter after removing order. It means order is ready
			orderList.remove(order);
		}
		for(Object o: orderList) { responseObserver.onNext((Order)o); }

		responseObserver.onCompleted();
	}

	///////////////////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void status(Response request, 
			StreamObserver<Order> responseObserver) 
	{
		int orderNumber = Integer.parseInt(request.getMessage());
		responseObserver.onNext(orderIDs.get(orderNumber));
		responseObserver.onCompleted();
	}

	private static void updateTableRecord() throws IOException 
	{
		ObjectOutputStream outputStream = null;
		FileOutputStream oututfile = null;
		try 
		{
			oututfile = new FileOutputStream(URL);
			outputStream = new ObjectOutputStream(oututfile);
			outputStream.writeObject(tableRecord);
		} 
		catch (IOException  e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			if (outputStream != null)
			{
				outputStream.close();
			}
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////
	// Constructors
	///////////////////////////////////////////////////////////////////////////////////////
	private static void saveOrder() throws IOException 
	{
		ObjectOutputStream outputStream = null;
		FileOutputStream oututfile = null;
		try 
		{
			oututfile = new FileOutputStream(URL2);
			outputStream = new ObjectOutputStream(oututfile);
			outputStream.writeObject(orderRecord);
		} 
		catch (IOException  e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			if (outputStream != null)
			{
				outputStream.close();
			}
		}
	}
	public StreamObserver<MakeOrder> orderstream(StreamObserver<Dish> responseObserver)
	{
		return null;
	}
	/*
	private Collection<Table> deserialize() throws IOException, ClassNotFoundException
	{
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try
		{
			fis = new FileInputStream(URL);
			ois = new ObjectInputStream(fis);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		Collection<Table> alp = (ArrayList<Table>)ois.readObject();
		return alp;
	}
	 */

}