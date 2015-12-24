package com.jude.fishing.model.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhuchenxi on 15/12/24.
 */
public class Article {
    private int id;
    private String title;
    private String url;
    private int type;
    private String source;
    @SerializedName("source_avatar")
    private String sourceAvatar;
    @SerializedName("praise_count")
    private int praiseCount;
    @SerializedName("praised")
    private boolean isPraised;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceAvatar() {
        return sourceAvatar;
    }

    public void setSourceAvatar(String sourceAvatar) {
        this.sourceAvatar = sourceAvatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isPraised() {
        return isPraised;
    }

    public void setPraised(boolean praised) {
        isPraised = praised;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }
}
