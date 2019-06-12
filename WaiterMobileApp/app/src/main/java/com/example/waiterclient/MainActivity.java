package com.example.waiterclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.restaurantnetworkapp.RestaurantServiceGrpc;
import io.grpc.restaurantnetworkapp.Table;
import io.grpc.stub.StreamObserver;


public class MainActivity extends AppCompatActivity
{
    private static String[] status = {"CLEAN","TAKEN","DIRTY"};
    private static String TAG = MainActivity.class.getSimpleName();
    private ManagedChannel channel;
    String host = "192.168.1.11";
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
    }

    public void updateTable(View view)
    {
        Button btn = (Button)findViewById(view.getId());
        String btnStr = btn.getText().toString();
        String[] str = btnStr.split(" ");
        final int tableNo = Integer.parseInt(str[1]);
        int tableState = 0;
        /*
        TO DO: TABLE STATE
        get the button color: tableState = {green=0, red=1, blue=2}
        if color green: set table status value to 1 (taken)
        if color red: set table status value to 2 (dirty)
        if color blue set table status value to 0
         */
        Table request = Table
                .newBuilder()
                .setTableID(tableNo)
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
}