package com.jude.fishing.model.entities;

/**
 * Created by zhuchenxi on 15/10/16.
 */
public class PersonAround extends PersonBrief {
    private double lat;
    private double lng;
    private int distance;

    public PersonAround(String avatar, int id, String name, boolean relation, String sign) {
        super(avatar, id, name, relation, sign);
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
