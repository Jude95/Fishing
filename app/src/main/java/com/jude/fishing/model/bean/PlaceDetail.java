package com.jude.fishing.model.bean;

/**
 * Created by Mr.Jude on 2015/9/23.
 */
public class PlaceDetail extends PlaceBrief{
    private String tel;
    private String content;
    private String[] picture;

    public String getTel() {
        return tel;
    }

    public String getContent() {
        return content;
    }

    public String[] getPicture() {
        return picture;
    }

    public PlaceDetail(int id, String name, String preview, String address, float score, int distance, int cost, int costType, String fishType, int poolType, int[] serviceType, double lat, double lng, String tel, String content, String[] picture) {
        super(id, name, preview, address, score, distance, cost, costType, fishType, poolType, serviceType, lat, lng);
        this.tel = tel;
        this.content = content;
        this.picture = picture;
    }
}
