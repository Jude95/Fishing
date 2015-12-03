package com.jude.fishing.model.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by heqiang on 2015/12/2.
 */
public class FishingSeed implements Serializable {
    private String id;
    private int authorId;
    private String authorAvatar;
    private String authorName;
    private long time;
    private String content;
    private List<PersonBrief> enrollMember;
    private long acTime;
    private String address;
    private String title;
    public FishingSeed(){}

    public FishingSeed(String id, int authorId, String authorAvatar, String authorName, long time, String content, long acTime, String address, String title) {
        this.id = id;
        this.authorId = authorId;
        this.authorAvatar = authorAvatar;
        this.authorName = authorName;
        this.time = time;
        this.content = content;
        this.acTime = acTime;
        this.address = address;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<PersonBrief> getEnrollMember() {
        return enrollMember;
    }

    public void setEnrollMember(List<PersonBrief> praiseMember) {
        this.enrollMember = praiseMember;
    }

    public long getAcTime() {
        return acTime;
    }

    public void setAcTime(long acTime) {
        this.acTime = acTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
