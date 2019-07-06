package com.example.waiterclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import com.example.APIs.DatabaseManager;
import com.example.adapter.TableAdapter;
import com.example.model.TableOrder;

import java.util.ArrayList;

public class TakeOrder extends AppCompatActivity {

    private GridView gridView;
    private DatabaseManager db;
    private ArrayList<String> menu;
    private TableOrder order;
    private int[] images = {
            R.drawable._appetizers,
            R.drawable._specials,
            R.drawable._salads,
            R.drawable._desserts,
            R.drawable._drinks
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_order);

        int tableNo = (int) getIntent().getSerializableExtra("Table Number");
        if (tableNo!=0){
            order = new TableOrder(tableNo);
        }
        gridView = findViewById(R.id.itemingridView);
        db = new DatabaseManager(this);
        menu = db.getMenu();

        gridView.setAdapter(new TableAdapter(TakeOrder.this,menu,images,order));
    }
}
