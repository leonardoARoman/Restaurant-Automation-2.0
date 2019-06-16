package com.example.waiterclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.restaurantnetworkapp.Response;
import io.grpc.restaurantnetworkapp.RestaurantServiceGrpc;
import io.grpc.restaurantnetworkapp.Table;
import io.grpc.stub.StreamObserver;


public class MainActivity extends AppCompatActivity
{
    private ArrayList<TableState> tables;
    private static String[] status = {"CLEAN","TAKEN","DIRTY"};
    private static int tableState = 0;
    private static String TAG = MainActivity.class.getSimpleName();
    private ManagedChannel channel;
    String host = "10.0.0.107";
    int port = 8080;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext(true)
                .build();

        tables = new ArrayList<TableState>();
        tables.add(new TableState((Button) findViewById(R.id.button_1),1));
        tables.add(new TableState((Button) findViewById(R.id.button_2),2));
        tables.add(new TableState((Button) findViewById(R.id.button_3),3));
        tables.add(new TableState((Button) findViewById(R.id.button_4),4));
        tables.add(new TableState((Button) findViewById(R.id.button_5),5));
        tables.add(new TableState((Button) findViewById(R.id.button_6),6));
        tables.add(new TableState((Button) findViewById(R.id.button_7),7));
        tables.add(new TableState((Button) findViewById(R.id.button_8),8));
        tables.add(new TableState((Button) findViewById(R.id.button_9),9));
        tables.add(new TableState((Button) findViewById(R.id.button_10),10));
        tables.add(new TableState((Button) findViewById(R.id.button_11),11));
        tables.add(new TableState((Button) findViewById(R.id.button_12),12));
    }

    private void setState(Button btn1,TableState btn2){
        if (btn2.getTableState()==1){
            Log.v(TAG,"MESSAGE: red ");
            btn1.setActivated(true);
            btn1.setPressed(false);
        }else if (btn2.getTableState()==2){
            Log.v(TAG,"MESSAGE: blue ");
            btn1.setSelected(true);
            btn1.setActivated(true);
        }else{
            btn1.setActivated(false);
            btn1.setSelected(false);
        }
    }

    public void updateTable(View view)
    {
        Button btn = (Button)findViewById(view.getId());
        for (TableState table: tables){
            if (btn.equals(table.getButton())){
                table.changeState();
                setState(btn,table);
                Log.v(TAG,"MESSAGE: table "+table.getTableNumber()+" is now "+status[table.getTableState()]);
                tableState = table.getTableState();
            }
        }
        String btnStr = btn.getText().toString();
        String[] str = btnStr.split(" ");
        final int tableNo = Integer.parseInt(str[1]); // tables 1-12

        Table request = Table
                .newBuilder()
                .setTableID(tableNo)// 1-12
                .setStatusValue(tableState)
                .build();

        RestaurantServiceGrpc.RestaurantServiceStub stub =
                RestaurantServiceGrpc.newStub(channel);

        stub.update(request, new StreamObserver<Table>()
        {
            @Override
            public void onNext(Table value)
            {
                Log.v(TAG,"MESSAGE: Table "+value.getTableID()+" is "+status[value.getStatusValue()]);
            }

            @Override
            public void onError(Throwable t)
            {
                Log.v(TAG,"MESSAGE: STUB faulted connection\n[exception] "+t.getMessage());
            }

            @Override
            public void onCompleted()
            {
                Log.v(TAG,"MESSAGE: Table "+tableNo+" updated.");
            }
        });
    }

    public void getTableRecords(View view) {
        Log.v(TAG,"MESSAGE: refresh pressed ");
        Response request = Response
                .newBuilder()
                .setMessage("Get the table records")
                .build();
        RestaurantServiceGrpc.RestaurantServiceStub stub =
                RestaurantServiceGrpc.newStub(channel);

        stub.tables(request, new StreamObserver<Table>() {
            @Override
            public void onNext(Table value) {
                int id = (int) value.getTableID();
                if (id < 13) {
                    TableState table = tables.get(id - 1);
                    Button btn = (Button)findViewById(table.getButton().getId());
                    table.setTableState(value.getStatusValue());
                    setState(btn,table);
                    Log.v(TAG,"MESSAGE: table "+id+" "+status[table.getTableState()]);
                }
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        });
    }
}