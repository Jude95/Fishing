package com.jude.fishing.model.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mr.Jude on 2015/9/12.
 */
public class Evaluate {
    @SerializedName("pid")
    private int placeId;
    private String placeName;
    private String placePreview;
    private int score;
    private String content;
    private long time;
    private int id;

    @SerializedName("uid")
    private int authorId;
    private String authorAvatar;
    private String authorName;
    private String address;
    private List<String> images;
    private int commentCount;

    public Evaluate(String address, String authorAvatar, int authorId, String authorName, int commentCount, String content, int id, List<String> images, int placeId, String placeName, String placePreview, int score, long time) {
        this.address = address;
        this.authorAvatar = authorAvatar;
        this.authorId = authorId;
        this.authorName = authorName;
        this.commentCount = commentCount;
        this.content = content;
        this.id = id;
        this.images = images;
        this.placeId = placeId;
        this.placeName = placeName;
        this.placePreview = placePreview;
        this.score = score;
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public int getId() {
        return id;
    }

    public List<String> getImages() {
        return images;
    }

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


}
