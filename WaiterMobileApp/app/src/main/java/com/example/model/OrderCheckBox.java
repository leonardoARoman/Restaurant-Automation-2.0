package com.example.model;

import android.widget.CheckBox;
import android.widget.TextView;

public class OrderCheckBox {
    private boolean isChecked;
    private CheckBox checkBox;
    private TextView textView;

    public OrderCheckBox() { isChecked = false; }

    public boolean isChecked() { return isChecked; }

    public void setChecked(boolean checked) { isChecked = checked; }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
