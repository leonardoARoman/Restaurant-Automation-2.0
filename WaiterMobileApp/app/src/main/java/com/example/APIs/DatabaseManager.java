package com.example.APIs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database";
    private static final String URL = "menu_ddl.sql";
    private String[] queries;
    private Context context;
    private static String TAG = DatabaseManager.class.getSimpleName();

    public DatabaseManager(Context context) {
        super(context,DATABASE_NAME,null,1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            InputStream inputStream = context.getAssets().open(URL);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            String ddl = new String(buffer);
            queries = ddl.split(";");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<String> getMenu() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT category_name FROM menu";
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                String name = cursor.getString(cursor.getColumnIndex("category_name"));
                list.add(name);
                //Log.v(TAG,"MESSAGE: name "+name);
                cursor.moveToNext();
            }
        }
        return list;
    }

    public ArrayList<String> getAppetizers() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM appetizers";
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int number = cursor.getInt(cursor.getColumnIndex("number"));
                int plateNo = cursor.getInt(cursor.getColumnIndex("plateNo"));
                String dish = cursor.getString(cursor.getColumnIndex("dish"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                list.add(number+","+plateNo+","+dish+","+price);
                cursor.moveToNext();
            }
        }
        return list;
    }

    public ArrayList<String> getSpecials() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM specials";
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int number = cursor.getInt(cursor.getColumnIndex("number"));
                int plateNo = cursor.getInt(cursor.getColumnIndex("plateNo"));
                String dish = cursor.getString(cursor.getColumnIndex("dish"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                list.add(number+","+plateNo+","+dish+","+price);
                cursor.moveToNext();
            }
        }
        return list;
    }

    public ArrayList<String> getSalads() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM salads";
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int number = cursor.getInt(cursor.getColumnIndex("number"));
                int plateNo = cursor.getInt(cursor.getColumnIndex("plateNo"));
                String dish = cursor.getString(cursor.getColumnIndex("salad"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                list.add(number+","+plateNo+","+dish+","+price);
                cursor.moveToNext();
            }
        }
        return list;
    }

    public ArrayList<String> getDesserts() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM desserts";
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int number = cursor.getInt(cursor.getColumnIndex("number"));
                int plateNo = cursor.getInt(cursor.getColumnIndex("plateNo"));
                String dish = cursor.getString(cursor.getColumnIndex("dessert"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                list.add(number+","+plateNo+","+dish+","+price);
                cursor.moveToNext();
            }
        }
        return list;
    }

    public ArrayList<String> getDrinks() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM drinks";
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int number = cursor.getInt(cursor.getColumnIndex("number"));
                int drinkNo = cursor.getInt(cursor.getColumnIndex("drinkNo"));
                String dish = cursor.getString(cursor.getColumnIndex("drink"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                list.add(number+","+drinkNo+","+dish+","+price);
                cursor.moveToNext();
            }
        }
        return list;
    }
}
/*
    // select a single item from a table
    String query = "SELECT * FROM appetizers WHERE dish = ?";
    Cursor cursor = db.rawQuery(query,new String[]{name});
    cursor.moveToFirst();
    double price = cursor.getDouble(cursor.getColumnIndex("price"));
 */