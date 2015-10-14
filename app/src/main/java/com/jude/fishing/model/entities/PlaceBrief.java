package com.jude.fishing.model.entities;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
public class PlaceBrief {
    private int id;
    private String name;
    private String preview;
    private String address;
    private String briefAddr;
    private float score;
    private int cost;
    private int costType;
    private String fishType;
    private int poolType;
    private String serviceType;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setCostType(int costType) {
        this.costType = costType;
    }

    public void setFishType(String fishType) {
        this.fishType = fishType;
    }

    public void setPoolType(int poolType) {
        this.poolType = poolType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getBriefAddr() {
        return briefAddr;
    }

    public void setBriefAddr(String briefAddr) {
        this.briefAddr = briefAddr;
    }

    public PlaceBrief() {
    }

    private double lat;
    private double lng;

    public PlaceBrief(int id, String name, String preview, String address, float score, int distance, int cost, int costType, String fishType, int poolType, String serviceType) {
        this.id = id;
        this.name = name;
        this.preview = preview;
        this.address = address;
        this.score = score;
        this.cost = cost;
        this.costType = costType;
        this.fishType = fishType;
        this.poolType = poolType;
        this.serviceType = serviceType;
    }

    public PlaceBrief(int id, String name, String preview, String briefAddr, String address, float score,  int cost, int costType, String fishType, int poolType, String serviceType, double lat, double lng) {
        this.id = id;
        this.name = name;
        this.preview = preview;
        this.briefAddr = briefAddr;
        this.address = address;
        this.score = score;
        this.cost = cost;
        this.costType = costType;
        this.fishType = fishType;
        this.poolType = poolType;
        this.serviceType = serviceType;
        this.lat = lat;
        this.lng = lng;
    }

    public int getCost() {
        return cost;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPreview() {
        return preview;
    }

    public String getAddress() {
        return address;
    }

    public float getScore() {
        return score;
    }
    public int getCostType() {
        return costType;
    }

    public String getFishType() {
        return fishType;
    }

    public int getPoolType() {
        return poolType;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
    public String getServiceType() {
        return serviceType;
    }

}
