package com.jude.fishing.model.bean;

import java.io.Serializable;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
public class Seed implements Serializable{
    private int id;
    private int authorId;
    private String authorAvatar;
    private String authorName;
    private long time;
    private String address;
    private String content;
    private String[] images;
    private int praiseCount;
    private boolean praiseStatus;
    private int commentCount;


    public Seed(int id, int authorId, String authorAvatar,String authorName, long time, String address, String content, String[] images, int praiseCount, boolean praiseStatus, int commentCount) {
        this.id = id;
        this.authorId = authorId;
        this.authorAvatar = authorAvatar;
        this.authorName = authorName;
        this.time = time;
        this.address = address;
        this.content = content;
        this.images = images;
        this.praiseCount = praiseCount;
        this.praiseStatus = praiseStatus;
        this.commentCount = commentCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public boolean getPraiseStatus() {
        return praiseStatus;
    }

    public void setPraiseStatus(boolean praiseStatus) {
        this.praiseStatus = praiseStatus;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
