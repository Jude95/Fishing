package com.jude.fishing.utils;

import com.jude.utils.JFileManager;
import com.jude.utils.JUtils;

/**
 * Created by Mr.Jude on 2015/9/4.
 */
public class DataCleaner {
    public static final void Update(int version){
        int versionOld = JUtils.getSharedPreference().getInt("data_version",0);
        if (version>versionOld){
            Clean();
            JUtils.getSharedPreference().edit().putInt("data_version", version).apply();
        }
    }

    public static final void Clean(){
        JUtils.getSharedPreference().edit().clear().apply();
        JFileManager.getInstance().clearAllData();
    }
}
