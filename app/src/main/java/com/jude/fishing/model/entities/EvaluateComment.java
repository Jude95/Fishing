package com.jude.fishing.model.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhuchenxi on 15/10/3.
 */
public class EvaluateComment {
    private int id ;
    @SerializedName("fid")
    private int originalId;
    @SerializedName("uid")
    private int authorId;
    private String authorName ;
    private String authorAvatar ;
    private long time ;
    private String content ;
    private EvaluateComment[] child = new EvaluateComment[0];

    public EvaluateComment(String authorAvatar, int authorId, String authorName, int id, int originalId, long time, String content) {
        this.authorAvatar = authorAvatar;
        this.authorId = authorId;
        this.authorName = authorName;
        this.id = id;
        this.originalId = originalId;
        this.time = time;
        this.content = content;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public EvaluateComment setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
        return this;
    }

    public int getAuthorId() {
        return authorId;
    }

    public EvaluateComment setAuthorId(int authorId) {
        this.authorId = authorId;
        return this;
    }

    public String getAuthorName() {
        return authorName;
    }

    public EvaluateComment setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public EvaluateComment[] getChild() {
        return child;
    }

    public EvaluateComment setChild(EvaluateComment[] child) {
        this.child = child;
        return this;
    }

    public String getContent() {
        return content;
    }

    public EvaluateComment setContent(String content) {
        this.content = content;
        return this;
    }

    public int getId() {
        return id;
    }

    public EvaluateComment setId(int id) {
        this.id = id;
        return this;
    }

    public int getOriginalId() {
        return originalId;
    }

    public EvaluateComment setOriginalId(int originalId) {
        this.originalId = originalId;
        return this;
    }

    public long getTime() {
        return time;
    }

    public EvaluateComment setTime(long time) {
        this.time = time;
        return this;
    }
}
