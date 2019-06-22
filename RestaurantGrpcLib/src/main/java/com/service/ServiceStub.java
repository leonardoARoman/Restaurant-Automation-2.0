package com.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Logger;

import io.grpc.restaurantnetworkapp.Order;
import io.grpc.restaurantnetworkapp.Response;
import io.grpc.restaurantnetworkapp.RestaurantServiceGrpc;
import com.service.ServiceStub;
import io.grpc.restaurantnetworkapp.Table;
import io.grpc.restaurantnetworkapp.TableRecord;
import io.grpc.stub.StreamObserver;
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
	//private static String DB = "./src/main/database/RestaurantDB.db";
	private static String URL = "./src/main/database/tableRecord.dat";
	//private static String URL = "./database/tableRecord.dat";
	private static String URL2 = "./src/main/database/orderRecord.dat";

	private ServiceStub() 
	{
		tableRecord = new ArrayList<Table>();
		orderRecord = new ArrayList<Order>();
		orderIDs = new HashMap<Integer,Order>();
		orderQuee = new LinkedList<Order>();
	}

	private ServiceStub(Collection<Table> tableRecord)
	{
		this.tableRecord = tableRecord;
		orderRecord = new ArrayList<Order>();
		orderIDs = new HashMap<Integer,Order>();
		orderQuee = new LinkedList<Order>();
	}

	public static ServiceStub getInstance() throws IOException 
	{
		if(instance==null) 
		{
			instance = new ServiceStub();
		}
		return instance;
	}

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

	@Override
	public void setup(TableRecord tablerecord, 
			StreamObserver<Table> responseObserver) 
	{
		responseObserver.onCompleted();
	}

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

		Response response = Response
				.newBuilder()
				.setMessage("Table "+table.getTableID()+" added to Restaurant record")
				.build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Override
	public void update(Table tableUpdate, 
			StreamObserver<Table> responseObserver) 
	{
		for(Table table: tableRecord) 
		{
			if(table.getTableID()==tableUpdate.getTableID())
			{
				tableRecord.remove(table);
				tableRecord.add(tableUpdate);
				break;
			}
		}
		try 
		{
			updateTableRecord();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		for(Table table: tableRecord) 
		{ 
			responseObserver.onNext(table); 
		}

		responseObserver.onCompleted();
	}

	@Override
	public void tables(Response request,
			StreamObserver<Table> responseObserver) {
		for(Table table: tableRecord) { responseObserver.onNext(table); }

		responseObserver.onCompleted();
	}
	
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

	@Override
	public void orderqueue(Response request,
			StreamObserver<Order> responseObserver) {
		Object[] orders = orderQuee.toArray();
		for(Object o: orders) { responseObserver.onNext((Order)o); }
		
		responseObserver.onCompleted();
	}
	
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