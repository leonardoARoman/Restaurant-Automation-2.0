package com.example.waiterclient;

import android.graphics.drawable.ColorDrawable;
import android.widget.Button;

/**
 * @Autthor Leonardo Roman
 * Table State class structure defines each custumed button
 * to mutate state at user button click.
 */
public class TableState {
    private static int buttonCount = 0;
    private Button button;
    private int tableState,tableNumber;
    private ColorDrawable tableColorState;

    public TableState(){
        buttonCount++;
        tableState = 0;
    }
    public TableState(Button button,int tableNumber/*, ColorDrawable tableColorState*/){
        buttonCount++;
        this.button = button;
        this.tableNumber = tableNumber;
        this.tableState = 0;
        //this.tableColorState = tableColorState;
    }

    public int getTableNumber()                 { return tableNumber;             }
    public int getTableState()                  { return tableState;              }
    public void setTableState(int tableState)   { this.tableState = tableState;   }
    public void changeState()                   { tableState = (tableState+1)%3;  }
    public Button getButton()                   { return button;                  }
}
