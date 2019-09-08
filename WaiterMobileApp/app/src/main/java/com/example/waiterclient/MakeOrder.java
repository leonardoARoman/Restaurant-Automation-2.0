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
import java.util.List;
import java.util.Map;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.restaurantnetworkapp.Order;
import io.grpc.restaurantnetworkapp.Response;
import io.grpc.restaurantnetworkapp.RestaurantServiceGrpc;
import io.grpc.restaurantnetworkapp.SendOrder;
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
        // ListView of strings(dish names) to be displayed
        dishes = findViewById(R.id.dishes);
        // Array of dishes to be display in dishes ListView
        menu = new ArrayList<>();
        db = new DatabaseManager(this);
        // Menu button clicked by waiter(appetizers,specials,salads,desserts,drinks)
        String option = (String) getIntent().getSerializableExtra("option");
        // To get the table object from whom is making order
        order = (TableOrder) getIntent().getSerializableExtra("order");

        Log.v(TAG,"MESSAGE: Making order for table "+order.getTableNumber());
        Log.v(TAG,"MESSAGE: option "+option);
        ArrayList<String> item;
        // Populates menu list with dishes found by the option selected to display
        // in ListView for dish selection
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
        List<io.grpc.restaurantnetworkapp.Dish> dishList = new ArrayList<>();

        int i = 0;
        for (Dish dish: menu){
            if (dish.isSelected()) {
                dishList.add(io.grpc.restaurantnetworkapp.Dish.newBuilder()
                        .setId(dish.getDishNo())
                        .setName(dish.getDish())
                        .setComment("default")
                        .setIsReady(false)
                        .build());
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

        io.grpc.restaurantnetworkapp.MakeOrder sendOrder =
                io.grpc.restaurantnetworkapp.MakeOrder.newBuilder()
                .setNumber(order.getOrderNumber())
                .addAllDishes(dishList)
                .build();

        stub.orderstream(new StreamObserver<SendOrder>() {
            @Override
            public void onNext(SendOrder value) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        }).onNext(sendOrder);
    }

    /**
     * Helper function splits elements in item array(array of Dish attributes)
     * @param item Array of dish attributes to create Dish object and add to menu List
     */
    private void dbParser(ArrayList<String> item){
        for (String str: item){
            String[] appetizers = str.split(",");
            menu.add(new Dish(
                    /* String   dish       */ Integer.parseInt(appetizers[0]),
                    /* int      menuNo     */ Integer.parseInt(appetizers[1]),
                    /* int      dishNo     */ appetizers[2],
                    /* double   price      */ Double.parseDouble(appetizers[3]),
                    /* boolean  isSelected */false));
        }
    }
}