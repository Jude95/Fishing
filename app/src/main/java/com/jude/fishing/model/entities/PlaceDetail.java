package com.jude.fishing.model.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.Jude on 2015/9/23.
 */
public class PlaceDetail extends PlaceBrief implements Serializable{
    private String tel;
    private String content;
    private String address;
    private int evaluateCount;
    @SerializedName("collectStatus")
    private boolean collected;
    private List<String> picture;
    private String area;
    private String deep;
    @SerializedName("hole")
    private int nest;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDeep() {
        return deep;
    }

    public void setDeep(String deep) {
        this.deep = deep;
    }

    public int getNest() {
        return nest;
    }

    public void setNest(int nest) {
        this.nest = nest;
    }

    public PlaceDetail() {
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setEvaluateCount(int evaluateCount) {
        this.evaluateCount = evaluateCount;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }



    public boolean isCollected() {
        return collected;
    }

    public int getEvaluateCount() {
        return evaluateCount;
    }

    public String getTel() {
        return tel;
    }

    public String getContent() {
        return content;
    }

    public List<String> getPicture() {
        return picture;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PlaceDetail(int id, String name, String preview, String address, float score,  int cost, int costType, String fishType, int poolType, String serviceType, double lat, double lng, String tel, String content, List<String> picture) {
        super(id, name, preview, address, score, cost, costType, fishType, poolType, serviceType, lat, lng);
        this.tel = tel;
        this.address = address;
        this.content = content;
        this.picture = picture;
    }
}
