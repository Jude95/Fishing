package com.jude.fishing.utils;

import java.text.DecimalFormat;

/**
 * Created by zhuchenxi on 15/10/2.
 */
public class DistanceFormat {
    public static String parse(double distance){
        String unit = "m";
        if (distance>1000){
            unit = "km";
            distance=distance/1000;
        }
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(distance)+unit;
    }
}
