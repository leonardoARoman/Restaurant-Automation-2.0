package com.example.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.example.model.Dish;
import com.example.model.OrderCheckBox;
import com.example.waiterclient.R;
import java.util.ArrayList;

public class OrderAdapter extends BaseAdapter {
    private static String TAG = OrderAdapter.class.getSimpleName();
    private Context context;
    private ArrayList<Dish> dishes;
    private Dish dish;

    public OrderAdapter(Context context, ArrayList<Dish> dishes) {
        this.context = context;
        this.dishes = dishes;
    }

    @Override
    public int getCount() {
        return dishes.size();
    }

    @Override
    public Object getItem(int position) {
        return dishes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final OrderCheckBox checkBox;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            checkBox = new OrderCheckBox();
            convertView = inflater.inflate(R.layout.order_adapter,parent, false);

            checkBox.setCheckBox((CheckBox) convertView.findViewById(R.id.checkbox));
            checkBox.setTextView((TextView) convertView.findViewById(R.id.dishTextView));

            convertView.setTag(checkBox);
        }else {
            checkBox = (OrderCheckBox)convertView.getTag();
        }
        dish = dishes.get(position);
        checkBox.getTextView().setText(dish.getMenuNo()+":"+dish.getDishNo()+" "+dish.getDish()+" "+dish.getPrice());
        checkBox.getTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        checkBox.getCheckBox().setOnCheckedChangeListener(null);
        checkBox.getCheckBox().setChecked(dishes.get(position).isSelected());
        checkBox.getCheckBox().setTag(position);
        checkBox.getCheckBox().setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        checkBox.setChecked(isChecked);
                        Dish d = dishes.get(position);
                        dishes.get(position).setSelected(isChecked);
                        Log.v(TAG,"MESSAGE: "+d.getDish()+" was selected "+d.isSelected());
                    }
                }
        );

        return convertView;
    }
}
