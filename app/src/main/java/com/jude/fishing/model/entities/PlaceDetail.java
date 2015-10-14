package com.jude.fishing.model.entities;

import java.util.List;

/**
 * Created by Mr.Jude on 2015/9/23.
 */
public class PlaceDetail extends PlaceBrief{
    private String tel;
    private String content;

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

    public void setCollectStatus(boolean collectStatus) {
        this.collectStatus = collectStatus;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    private int evaluateCount;
    private boolean collectStatus;
    private List<String> picture;

    public boolean isCollectStatus() {
        return collectStatus;
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

    public PlaceDetail(int id, String name, String preview, String address, float score,  int cost, int costType, String fishType, int poolType, String serviceType, double lat, double lng, String tel, String content, List<String> picture) {
        super(id, name, preview, address,address, score, cost, costType, fishType, poolType, serviceType, lat, lng);
        this.tel = tel;
        this.content = content;
        this.picture = picture;
    }
}
