package com.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;
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
	private static ArrayList<Table> tables;
	private static String DB = "./src/main/database/RestaurantDB.db";
	private static String URL = "./src/main/database/tableRecord.dat";

	private ServiceStub() 
	{
		tableRecord = new ArrayList<Table>();
		//tables = new ArrayList<Table>();
	}

	private ServiceStub(Collection<Table> tableRecord)
	{
		this.tableRecord = tableRecord;
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
		for(Table table: tableRecord) 
		{ 
			responseObserver.onNext(table); 
		}

		responseObserver.onCompleted();
	}

	@Override
	public void tables(Response request,
			StreamObserver<Table> responseObserver)
	{
		for(Table table: tableRecord) { responseObserver.onNext(table); }

		responseObserver.onCompleted();
	}
	
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
}