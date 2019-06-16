package com.example.waiterclient;

import android.graphics.Color;
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
    private TableState button1,button2,button3,button4,button5,button6;
    private TableState button7,button8,button9,button10,button11,button12;
    private static String[] status = {"CLEAN","TAKEN","DIRTY"};
    private static int tableState = 0;
    private static String TAG = MainActivity.class.getSimpleName();
    private ManagedChannel channel;
    String host = "172.31.99.177";
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
        button1 = new TableState((Button) findViewById(R.id.button_1),1);
        button2 = new TableState((Button) findViewById(R.id.button_2),2);
        button3 = new TableState((Button) findViewById(R.id.button_3),3);
        button4 = new TableState((Button) findViewById(R.id.button_4),4);
        button5 = new TableState((Button) findViewById(R.id.button_5),5);
        button6 = new TableState((Button) findViewById(R.id.button_6),6);
        button7 = new TableState((Button) findViewById(R.id.button_7),7);
        button8 = new TableState((Button) findViewById(R.id.button_8),8);
        button9 = new TableState((Button) findViewById(R.id.button_9),9);
        button10 = new TableState((Button) findViewById(R.id.button_10),10);
        button11 = new TableState((Button) findViewById(R.id.button_11),11);
        button12 = new TableState((Button) findViewById(R.id.button_12),12);
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
        if (btn.equals(button1.getButton())){
            button1.changeState();
            setState(btn,button1);
            Log.v(TAG,"MESSAGE: table "+button1.getTableNumber()+" is now "+status[button1.getTableState()]);
            tableState = button1.getTableState();
        }
        else if (btn.equals(button2.getButton())){
            button2.changeState();
            setState(btn,button2);
            Log.v(TAG,"MESSAGE: table "+button2.getTableNumber()+" is now "+status[button2.getTableState()]);
            tableState = button2.getTableState();
        }
        else if (btn.equals(button3.getButton())){
            button3.changeState();
            setState(btn,button3);
            Log.v(TAG,"MESSAGE: table "+button3.getTableNumber()+" is now "+status[button3.getTableState()]);
            tableState = button3.getTableState();
        }
        else if (btn.equals(button4.getButton())){
            button4.changeState();
            setState(btn,button4);
            Log.v(TAG,"MESSAGE: table "+button4.getTableNumber()+" is now "+status[button4.getTableState()]);
            tableState = button4.getTableState();
        }
        else if (btn.equals(button5.getButton())){
            button5.changeState();
            setState(btn,button5);
            Log.v(TAG,"MESSAGE: table "+button5.getTableNumber()+" is now "+status[button5.getTableState()]);
            tableState = button5.getTableState();
        }
        else if (btn.equals(button6.getButton())){
            button6.changeState();
            setState(btn,button6);
            Log.v(TAG,"MESSAGE: table "+button6.getTableNumber()+" is now "+status[button6.getTableState()]);
            tableState = button6.getTableState();
        }
        else if (btn.equals(button7.getButton())){
            button7.changeState();
            setState(btn,button7);
            Log.v(TAG,"MESSAGE: table "+button7.getTableNumber()+" is now "+status[button7.getTableState()]);
            tableState = button7.getTableState();
        }
        else if (btn.equals(button8.getButton())){
            button8.changeState();
            setState(btn,button8);
            Log.v(TAG,"MESSAGE: table "+button8.getTableNumber()+" is now "+status[button8.getTableState()]);
            tableState = button8.getTableState();
        }
        else if (btn.equals(button9.getButton())){
            button9.changeState();
            setState(btn,button9);
            Log.v(TAG,"MESSAGE: table "+button9.getTableNumber()+" is now "+status[button9.getTableState()]);
            tableState = button9.getTableState();
        }
        else if (btn.equals(button10.getButton())){
            button10.changeState();
            setState(btn,button10);
            Log.v(TAG,"MESSAGE: table "+button10.getTableNumber()+" is now "+status[button10.getTableState()]);
            tableState = button10.getTableState();
        }
        else if (btn.equals(button11.getButton())){
            button11.changeState();
            setState(btn,button11);
            Log.v(TAG,"MESSAGE: table "+button11.getTableNumber()+" is now "+status[button11.getTableState()]);
            tableState = button11.getTableState();
        }
        else if (btn.equals(button12.getButton())){
            button12.changeState();
            setState(btn,button12);
            Log.v(TAG,"MESSAGE: table "+button12.getTableNumber()+" is now  "+status[button12.getTableState()]);
            tableState = button12.getTableState();
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
}