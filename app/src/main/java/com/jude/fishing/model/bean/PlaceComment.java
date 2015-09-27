package com.jude.fishing.model.bean;

/**
 * Created by Mr.Jude on 2015/9/12.
 */
public class PlaceComment {
    private int placeId;
    private String placeName;
    private String placePreview;
    private int score;
    private String content;
    private long time;

    public int getPlaceId() {
        return placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPlacePreview() {
        return placePreview;
    }

    public int getScore() {
        return score;
    }

    public String getContent() {
        return content;
    }

    public long getTime() {
        return time;
    }

    public PlaceComment(int placeId, String placeName, String placePreview, int score, String content, long time) {

        this.placeId = placeId;
        this.placeName = placeName;
        this.placePreview = placePreview;
        this.score = score;
        this.content = content;
        this.time = time;
    }
}
