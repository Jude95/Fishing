package com.jude.fishing.model.entities;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps2d.model.LatLng;

import java.io.Serializable;

/**
 * Created by Mr.Jude on 2015/1/28.
 */
public class Location implements Serializable{

    private String address = "重庆市南岸区";
    private double altitude = 0;
    private String city = "重庆市";
    private String country = "中国";
    private String district = "南岸区";
    private String floor = "";
    private double latitude = 29.53;
    private double longitude = 106.60;
    private String province = "重庆市";
    private String street = "";
    private int regionCode = 110000;


    public Location(){}

    public Location(AMapLocation location) {
        setLocation(location);
    }

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

    public void setLocation(AMapLocation location){
        address = location.getAddress();
        altitude = location.getAltitude();
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        city = location.getCity();
        country = location.getCountry();
        district = location.getDistrict();
        floor = location.getFloor();
        province = location.getProvince();
        street = location.getStreet();
        regionCode = Integer.parseInt(location.getAdCode());
    }

    public android.location.Location toLocation(){
        android.location.Location location = new android.location.Location("");
        location.setAltitude(altitude);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }

    public LatLng toLatLng(){
        return new LatLng(latitude,longitude);
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

    public String getFloor() {
        return floor;
    }

    public String getProvince() {
        return province;
    }

    public String getStreet() {
        return street;
    }

}
