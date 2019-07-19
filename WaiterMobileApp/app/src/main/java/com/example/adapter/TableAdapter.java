package com.example.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.model.TableOrder;
import com.example.waiterclient.MakeOrder;
import com.example.waiterclient.R;
import com.example.waiterclient.TakeOrder;
import java.util.ArrayList;


public class TableAdapter extends BaseAdapter {
    private TakeOrder activity;
    private ArrayList<String> menu;
    private int[] images;
    private TableOrder order;

    public TableAdapter(TakeOrder activity, ArrayList<String> menu, int[] images, TableOrder order){
        this.activity = activity;
        this.menu = menu;
        this.images = images;
        this.order = order;
    }

    @Override
    public int getCount() {
        return menu.size();
    }

    @Override
    public Object getItem(int position) {
        return menu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View m_view = inflater.inflate(R.layout.table_adapter,null);

        ImageButton imageButton = m_view.findViewById(R.id.imageBtn);
        TextView textView = m_view.findViewById(R.id.textView);

        imageButton.setImageResource(images[position]);
        textView.setText(menu.get(position));

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getBaseContext(), MakeOrder.class);
                intent.putExtra("option",menu.get(position));// pass the menu option clicked by user to access correct table in database and get dishes.
                intent.putExtra("order",order);// pass the order in process to add any dishes selected from the option menu.
                activity.startActivity(intent);
            }
        });

        return m_view;
    }
}
