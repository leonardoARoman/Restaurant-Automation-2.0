package com.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Logger;

import io.grpc.restaurantnetworkapp.RecievedOrder;
import io.grpc.restaurantnetworkapp.Order;
import io.grpc.restaurantnetworkapp.ReceivedTable;
import io.grpc.restaurantnetworkapp.Response;
import io.grpc.restaurantnetworkapp.RestaurantServiceGrpc;
import io.grpc.restaurantnetworkapp.SendOrder;

import com.service.ServiceStub;
import io.grpc.restaurantnetworkapp.Table;
import io.grpc.restaurantnetworkapp.TableRecord;
import io.grpc.stub.StreamObserver;

public class ServiceStub extends 
RestaurantServiceGrpc.RestaurantServiceImplBase 
{

	private static final Logger logger = Logger.getLogger(ServiceStub.class.getName());
	private static LinkedHashSet<StreamObserver<RecievedOrder>> dishObservers;
	private static LinkedHashSet<StreamObserver<ReceivedTable>> tableObservers;
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
		dishObservers 	= new LinkedHashSet<StreamObserver<RecievedOrder>>();
		tableObservers	= new LinkedHashSet<StreamObserver<ReceivedTable>>();
		tableRecord 	= new ArrayList<Table>();		// For table inventory
		orderRecord 	= new ArrayList<Order>();		// For record storage
		orderIDs 		= new HashMap<Integer,Order>();	// For quick access transaction
		orderList 		= new ArrayList<Order>();		// To display in kitchen monitors
		orderQuee 		= new LinkedList<Order>();		// To remove from queue and update orderlist
	}
	/**
	 * 
	 * @param tableRecord list of tables to configure at boot up state
	 */
	private ServiceStub(Collection<Table> tableRecord)
	{
		ServiceStub.tableRecord = tableRecord;
		dishObservers			= new LinkedHashSet<StreamObserver<RecievedOrder>>();
		tableObservers			= new LinkedHashSet<StreamObserver<ReceivedTable>>();
		orderRecord 			= new ArrayList<Order>();
		orderIDs 				= new HashMap<Integer,Order>();
		orderList 				= new ArrayList<Order>();
		orderQuee 				= new LinkedList<Order>();
	}
	/**
	 * 
	 * @return singleton of type ServiceStub
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
	 * @param tableRecord List of tables at ready state for boot up
	 * @return singleton of type ServiceStub with tables configured at instantiation
	 * @throws IOException Empty container
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
			StreamObserver<Table> responseObserver) {
		responseObserver.onCompleted();
	}

	///////////////////////////////////////////////////////////////////////////////////////
	// @Admin
	// add: To add tables to the restaurant
	///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void add(Table table,
			StreamObserver<Response> responseObserver) {
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
	// @Clients -Admin
	// update: Updates the state of a given table; Clean, Taken or Dirty
	///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public StreamObserver<Table> updatetable(StreamObserver<ReceivedTable> responseObserver) {
		tableObservers.add(responseObserver);
		return new StreamObserver<Table>() {
			@Override
			public void onNext(Table value) {
				System.out.println(value);	
				Table table = tableRecord
						.stream()
						.filter(t->t.getTableID()==value.getTableID())
						.findFirst()
						.orElse(null);
				if(!table.equals(null)) {
					tableRecord.remove(table);
					tableRecord.add(value);	
					try {
						updateTableRecord();
					} catch (IOException e) {
						e.printStackTrace();
					}
					//tableRecord.forEach(t->responseObserver.onNext(t));
				}
				tableObservers
				.forEach(o->o.onNext(ReceivedTable
						.newBuilder()
						.setTable(value)
						.build()));
			}
			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub
			}
		};		
	}

	///////////////////////////////////////////////////////////////////////////////////////
	// @Clients -Admin, Waiter, Hostess
	// @Param request -input request
	// @Param responseObserver -returns an observer to stream all tables
	///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void tables(Response request,
			StreamObserver<Table> responseObserver) {
		for(Table table: tableRecord) { responseObserver.onNext(table); }

		responseObserver.onCompleted();
	}

	///////////////////////////////////////////////////////////////////////////////////////
	// Clients -Kitchen, Waiter
	// bidirectional streaming to send orders to kitchen monitors
	// @Param responseObserver - observers registered to get RecievedOrder streaming
	///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public StreamObserver<SendOrder> orderstream(StreamObserver<RecievedOrder> responseObserver) {
		dishObservers.add(responseObserver);
		return new StreamObserver<SendOrder>() {
			@Override
			public void onNext(SendOrder value) {
				System.out.println(value);

				//System.out.println(value);
				dishObservers
				.forEach(o->o.onNext(RecievedOrder
						.newBuilder()
						.setOrder(value)
						.build()));
			}
			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub
			}
		};
	}

	///////////////////////////////////////////////////////////////////////////////////////
	// @Clients: Kitchen
	// orderqueue: Removed order from queue once ready
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
	// @Clients: Admin, Waiter, Host
	// status: returns the state of a given order
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
	// Program helper method to save order records
	// private!
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