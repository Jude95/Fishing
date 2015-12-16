package com.jude.fishing.model.entities;

import java.io.Serializable;

/**
 * Created by Mr.Jude on 2015/1/28.
 */
public class Location implements Serializable{

    public String address = "重庆市南岸区";
    public double altitude = 0;
    public String city = "重庆市";
    public String country = "中国";
    public String district = "南岸区";
    public double latitude = 29.53;
    public double longitude = 106.60;
    public String province = "重庆市";
    public int regionCode = 110000;


    public Location(){}


    public int getRegionCode() {
        return regionCode;
    }
    public double getAltitude() {
        return altitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }



    public android.location.Location toLocation(){
        android.location.Location location = new android.location.Location("");
        location.setAltitude(altitude);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getCountry() {
        return country;
    }


    public String getProvince() {
        return province;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Location) {
            return (address.equals(((Location) o).address));
        }
        return false;
    }
}
