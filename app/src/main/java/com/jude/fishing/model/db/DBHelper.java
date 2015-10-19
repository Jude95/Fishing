package com.jude.fishing.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhuchenxi on 15/10/2.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "fishing.db";
    public static final int DATABASE_VERSION = 11;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PlaceDBTable.getInstance().create());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE "+ PlaceDBTable.TABLE_NAME);
        db.execSQL(PlaceDBTable.getInstance().create());
    }
}
