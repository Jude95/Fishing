package com.jude.fishing.utils;

import android.content.Context;

import com.jude.fishing.model.db.DBHelper;
import com.jude.fishing.model.db.PlaceDBTable;
import com.jude.utils.JFileManager;
import com.jude.utils.JUtils;
import com.squareup.sqlbrite.SqlBrite;

/**
 * Created by Mr.Jude on 2015/9/4.
 */
public class DataCleaner {
    public static void Update(Context ctx,int version){
        int versionOld = JUtils.getSharedPreference().getInt("data_version",0);
        if (version>versionOld){
            Clean(ctx);
            JUtils.getSharedPreference().edit().putInt("data_version", version).apply();
        }
    }

    public static void Clean(Context ctx){
        JUtils.getSharedPreference().edit().clear().apply();
        JFileManager.getInstance().clearAllData();
        SqlBrite.create().wrapDatabaseHelper(new DBHelper(ctx)).delete(PlaceDBTable.TABLE_NAME,"");
    }
}
