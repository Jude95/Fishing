package com.jude.fishing.model.entities;

/**
 * Created by Mr.Jude on 2015/9/12.
 */
public class Evaluate {
    private int placeId;
    private String placeName;
    private String placePreview;
    private int score;
    private String content;
    private long time;
    private int id;
    private int authorId;
    private String authorAvatar;
    private String authorName;
    private String address;
    private String[] images;
    private int commentCount;

    public Evaluate(String address, String authorAvatar, int authorId, String authorName, int commentCount, String content, int id, String[] images, int placeId, String placeName, String placePreview, int score, long time) {
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

    public String[] getImages() {
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
