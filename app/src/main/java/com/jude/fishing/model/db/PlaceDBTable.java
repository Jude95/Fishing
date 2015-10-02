package com.jude.fishing.model.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.jude.fishing.model.entities.PlaceBrief;

/**
 * Created by zhuchenxi on 15/10/2.
 */
public class PlaceDBTable extends AbsDBTable<PlaceBrief> {


    public static final String TABLE_NAME = "place";
    public static final String COLUMN_ID = "id";

    public static final String COLUMN_PID = "pid";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PREVIEW = "preview";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_SCORE = "score";
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_COST_TYPE = "costType";
    public static final String COLUMN_FISH_TYPE = "fishType";
    public static final String COLUMN_POOL_TYPE = "poolType";
    public static final String COLUMN_SERVICE_TYPE = "serviceType";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LNG = "lng";


    PlaceDBTable() {
    }

    public String create() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_PID + " INT NOT NULL,"+
                COLUMN_NAME + " CHAR(10) NOT NULL," +
                COLUMN_PREVIEW + " CHAR(100) NOT NULL,"+
                COLUMN_ADDRESS + " CHAR(30) NOT NULL," +
                COLUMN_SCORE + " FLOAT NOT NULL,"+
                COLUMN_COST + " INT NOT NULL,"+
                COLUMN_COST_TYPE + " INT NOT NULL," +
                COLUMN_FISH_TYPE + " CHAR(50) NOT NULL,"+
                COLUMN_POOL_TYPE + " INT NOT NULL,"+
                COLUMN_SERVICE_TYPE + " CHAR(20) NOT NULL,"+
                COLUMN_LAT + " DOUBLE NOT NULL,"+
                COLUMN_LNG + " DOUBLE NOT NULL"
                + ");";
    }

    @Override
    public ContentValues to(PlaceBrief object) {
        ContentValues vals = new ContentValues();
        vals.put(COLUMN_PID, object.getId());
        vals.put(COLUMN_NAME, object.getName());
        vals.put(COLUMN_PREVIEW, object.getPreview());
        vals.put(COLUMN_ADDRESS, object.getAddress());
        vals.put(COLUMN_SCORE, object.getScore());
        vals.put(COLUMN_COST, object.getCost());
        vals.put(COLUMN_FISH_TYPE, object.getFishType());
        vals.put(COLUMN_COST_TYPE, object.getCostType());
        vals.put(COLUMN_POOL_TYPE, object.getPoolType());
        vals.put(COLUMN_SERVICE_TYPE, object.getServiceType());
        vals.put(COLUMN_LAT, object.getLat());
        vals.put(COLUMN_LNG, object.getLng());
        return vals;
    }

    @Override
    public PlaceBrief from(Cursor cursor) {
        PlaceBrief placeBrief = new PlaceBrief(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PREVIEW)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_SCORE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COST)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COST_TYPE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FISH_TYPE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_POOL_TYPE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE_TYPE)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LAT)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LNG))
                );
        return placeBrief;
    }
}
