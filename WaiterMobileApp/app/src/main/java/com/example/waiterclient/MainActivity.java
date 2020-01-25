package com.example.waiterclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.APIs.ChannelManager;
import com.example.APIs.DatabaseManager;
import com.example.model.TableState;

import java.util.ArrayList;
import io.grpc.ManagedChannel;
import io.grpc.restaurantnetworkapp.ReceivedTable;
import io.grpc.restaurantnetworkapp.Response;
import io.grpc.restaurantnetworkapp.RestaurantServiceGrpc;
import io.grpc.restaurantnetworkapp.Table;
import io.grpc.stub.StreamObserver;


public class MainActivity extends AppCompatActivity {
    private ArrayList<TableState> tables;
    private static String[] status = {"CLEAN","TAKEN","DIRTY"};
    private static int tableState = 0;
    private static String TAG = MainActivity.class.getSimpleName();
    private ManagedChannel channel;
    private DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        channel = ChannelManager.getChannelManagerInstance().getChannel();
        db = new DatabaseManager(this);
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

    private void setState(Button btn1,TableState table){
        if (table.getTableState()==1){
            Log.v(TAG,"MESSAGE: red ");
            btn1.setActivated(true);
            btn1.setPressed(false);
        }else if (table.getTableState()==2){
            Log.v(TAG,"MESSAGE: blue ");
            btn1.setSelected(true);
            btn1.setActivated(true);
        }else{
            btn1.setActivated(false);
            btn1.setSelected(false);
        }
    }

    /**
     * Helper on click method called by any table button pressed by user.
     * The Button that was pressed will be determined by its view id.
     * @param view
     */
    public void updateTable(View view)
    {
        Button tableButton = (Button)findViewById(view.getId());// get the button that was pressed
        for (TableState table: tables){
            // find the button pressed in our list of buttons to change to next state.
            if (tableButton.equals(table.getButton())){
                table.changeState();// changes button to next state.
                setState(tableButton,table);// set button view to next color according to button state number
                Log.v(TAG,"MESSAGE: table "+table.getTableNumber()+" is now "+status[table.getTableState()]);
                tableState = table.getTableState();// get new table state to broadcast update
            }
        }

        String btnStr = tableButton.getText().toString();
        String[] str = btnStr.split(" ");
        final int tableNo = Integer.parseInt(str[1]); // tables 1-12

        /**
         * Hold button pressed to take table's order.
         */
        tableButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(MainActivity.this.getBaseContext(),TakeOrder.class);
                // Pass table number to the next activity to make order with table number
                intent.putExtra("Table Number",tableNo);// current location
                startActivity(intent);
                return false;
            }
        });

        Table tableRequest = Table
                .newBuilder()
                .setTableID(tableNo)// 1-12
                .setStatusValue(tableState)
                .build();

        RestaurantServiceGrpc.RestaurantServiceStub stub =
                RestaurantServiceGrpc.newStub(channel);

        stub.updatetable(new StreamObserver<ReceivedTable>()
        {
            @Override
            public void onNext(ReceivedTable value) {
                TableState tempTable = tables.stream()
                        .filter(t->t.getTableNumber()==value.getTable().getTableID())
                        .findFirst()
                        .get();
                tempTable.changeState();
                setState(tempTable.getButton(),tempTable);
            }

            @Override
            public void onError(Throwable t)
            {
                Log.v(TAG,"MESSAGE: STUB faulted connection\n[exception] "+t.getMessage());
            }

            @Override
            public void onCompleted()
            {
                //Log.v(TAG,"MESSAGE: Table "+tableNo+" updated.");
            }
        }).onNext(tableRequest);
    }

    /**
     * Helper on click button called by the refresh button. This will get list of all tables
     * to determine if any table have change state from hostess app action.
     * @param view
     */
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