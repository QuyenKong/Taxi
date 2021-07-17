package com.example.taxi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SqliteDB_2209 extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Taxi_Manger";

//    int ma;
//    String soXe;
//    float quangDuong;
//    int donGia;
//    int khuyenMai;

    private static final String TABLE_TAXI = "Taxi_01";

    private static final String COLUMN_Taxi_ma ="Taxi_ma";
    private static final String COLUMN_Taxi_So_Xe ="Taxi_So_Xe";
    private static final String COLUMN_Taxi_Quang_Duong ="Taxi_Quang_Duong";
    private static final String COLUMN_Taxi_Don_Gia ="Taxi_Don_Gia";
    private static final String COLUMN_Taxi_Khuyen_Mai = "Taxi_Khuyen_Mai";


    public SqliteDB_2209 (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Script.
        String script = "CREATE TABLE " + TABLE_TAXI + "("
                + COLUMN_Taxi_ma + " INTEGER PRIMARY KEY,"
                + COLUMN_Taxi_So_Xe + " TEXT,"
                + COLUMN_Taxi_Quang_Duong + " REAL,"
                + COLUMN_Taxi_Don_Gia + " INTEGER,"
                + COLUMN_Taxi_Khuyen_Mai + " INTEGER" + ")";
        // Execute Script.
        db.execSQL(script);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAXI);

        // Create tables again
        onCreate(db);
    }

    public void createDefaultTaxisIfNeed()  {
        int count = this.getTaxiCount();
        if(count ==0 ) {
            Taxi_01 Taxi1 = new Taxi_01("29D2-283.34",
                    100.1f,100,1);
            Taxi_01 Taxi2 = new Taxi_01("29D2-283.34",
                    100.1f,200,1);
            Taxi_01 Taxi3 = new Taxi_01("29D2-283.34",
                    100.1f,300,1);
            Taxi_01 Taxi4 = new Taxi_01("29D2-283.34",
                    100.1f,400,1);
            Taxi_01 Taxi5 = new Taxi_01("29D2-283.34",
                    100.1f,500,1);
            Taxi_01 Taxi6 = new Taxi_01("29D2-283.34",
                    100.1f,600,1);
            Taxi_01 Taxi7 = new Taxi_01("29D2-283.34",
                    100.1f,700,1);

            this.addTaxi(Taxi1);
            this.addTaxi(Taxi2);
            this.addTaxi(Taxi3);
            this.addTaxi(Taxi4);
            this.addTaxi(Taxi5);
            this.addTaxi(Taxi6);
            this.addTaxi(Taxi7);
        }
    }

    public void addTaxi(Taxi_01 taxi1) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_Taxi_So_Xe, taxi1.getSoXe());
        values.put(COLUMN_Taxi_Quang_Duong, taxi1.getQuangDuong());
        values.put(COLUMN_Taxi_Don_Gia, taxi1.getDonGia());
        values.put(COLUMN_Taxi_Khuyen_Mai, taxi1.getKhuyenMai());

        // Inserting Row
        db.insert(TABLE_TAXI, null, values);

        // Closing database connection
        db.close();
    }

    public int getTaxiCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TAXI;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    public List<Taxi_01> getAllTaxi() {

        List<Taxi_01> arrTaxi = new ArrayList<Taxi_01>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TAXI;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Taxi_01 taxi = new Taxi_01();
                taxi.setMa(Integer.parseInt(cursor.getString(0)));
                taxi.setSoXe(cursor.getString(1));
                taxi.setQuangDuong(Float.parseFloat(cursor.getString(2)));
                taxi.setDonGia(Integer.parseInt(cursor.getString(3)));
                taxi.setKhuyenMai(Integer.parseInt(cursor.getString(4)));
                // Adding arrTaxi to list
                arrTaxi.add(taxi);
            } while (cursor.moveToNext());
        }

        // return arrTaxi list
        return arrTaxi;
    }

    public int updateTaxi(Taxi_01 taxi) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_Taxi_So_Xe, taxi.getSoXe());
        values.put(COLUMN_Taxi_Quang_Duong, taxi.getQuangDuong());
        values.put(COLUMN_Taxi_Don_Gia, taxi.getDonGia());
        values.put(COLUMN_Taxi_Khuyen_Mai, taxi.getKhuyenMai());

        // updating row
        return db.update(TABLE_TAXI, values, COLUMN_Taxi_ma + " = ?",
                new String[]{String.valueOf(taxi.getMa())});
    }

    public void deleteTaxi(Taxi_01 taxi) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TAXI, COLUMN_Taxi_ma + " = ?",
                new String[] { String.valueOf(taxi.getMa()) });
        db.close();
    }
}
