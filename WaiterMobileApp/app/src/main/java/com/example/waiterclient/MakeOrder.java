package com.example.waiterclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.APIs.ChannelManager;
import com.example.APIs.DatabaseManager;
import com.example.adapter.OrderAdapter;
import com.example.model.Dish;
import com.example.model.TableOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.restaurantnetworkapp.Order;
import io.grpc.restaurantnetworkapp.Response;
import io.grpc.restaurantnetworkapp.RestaurantServiceGrpc;
import io.grpc.restaurantnetworkapp.Table;
import io.grpc.stub.StreamObserver;

public class MakeOrder extends AppCompatActivity {

    private ListView dishes;
    private DatabaseManager db;
    private ArrayList<Dish> menu;
    private TableOrder order;
    private static String TAG = MakeOrder.class.getSimpleName();
    private ManagedChannel channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);

        channel = ChannelManager.getChannelManagerInstance().getChannel();

        dishes = findViewById(R.id.dishes);
        menu = new ArrayList<>();
        db = new DatabaseManager(this);
        String option = (String) getIntent().getSerializableExtra("option");
        order = (TableOrder) getIntent().getSerializableExtra("order");

        Log.v(TAG,"MESSAGE: Making order for table "+order.getTableNumber());
        Log.v(TAG,"MESSAGE: option "+option);
        ArrayList<String> item;
        if (option!=null){
            if (option.equals("appetizers")){
                item = db.getAppetizers();
                dbParser(item);
            }else if (option.equals("specials")){
                item = db.getSpecials();
                dbParser(item);
            }else if (option.equals("salads")){
                item = db.getSalads();
                dbParser(item);
            }else if (option.equals("desserts")){
                item = db.getDesserts();
                dbParser(item);
            }else if (option.equals("drinks")){
                item = db.getDrinks();
                dbParser(item);
            }
        }else {
        }
        dishes.setAdapter(new OrderAdapter(this,menu));
    }

    public void addToChartButtonListener(View view){
        Map<String,String> map = new HashMap();
        int i = 0;
        for (Dish dish: menu){
            if (dish.isSelected()) {
                map.put(i+"",dish.getDish());
                order.getDishes().add(dish.getDish());
                order.getPrices().add(dish.getPrice());
                i++;
            }
        }
        double totalPrice = 0.0;
        for (Double price: order.getPrices()){
            totalPrice+=price;
        }
        order.setTotal(totalPrice);
        Log.v(TAG,"MESSAGE: Total "+order.getTotal());

        RestaurantServiceGrpc.RestaurantServiceStub stub =
                RestaurantServiceGrpc.newStub(channel);


        Order orderGrpc = Order.newBuilder()
                .setOrderID(Integer.parseInt(order.getTableNumber()+""+order.getOrderNumber()))
                .setOrderNo(order.getTableNumber())
                .setIsReady(false)
                .setTable(Table.newBuilder().setTableID(order.getTableNumber()).build())
                .setMessage(order.getOrder())
                .putAllOrder(map)
                .build();

        stub.order(orderGrpc, new StreamObserver<Response>() {
            @Override
            public void onNext(Response value) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        });

    }

    private void dbParser(ArrayList<String> item){
        for (String str: item){
            String[] appetizers = str.split(",");
            menu.add(new Dish(
                    Integer.parseInt(appetizers[0]),
                    Integer.parseInt(appetizers[1]),
                    appetizers[2],
                    Double.parseDouble(appetizers[3]),
                    false));
        }
    }
}