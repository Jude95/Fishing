package com.jude.fishing.model.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhuchenxi on 15/9/27.
 */
public class SeedComment {
    private int id ;
    @SerializedName("fid")
    private int originalId;
    @SerializedName("uid")
    private int authorId;
    private String authorName ;
    private String authorAvatar ;
    private long time ;
    private String content ;
    private SeedComment[] child = new SeedComment[0];

    public SeedComment(String authorAvatar, int authorId, String authorName, int id, int originalId, long time, String content) {
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

    public SeedComment setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
        return this;
    }

    public int getAuthorId() {
        return authorId;
    }

    public SeedComment setAuthorId(int authorId) {
        this.authorId = authorId;
        return this;
    }

    public String getAuthorName() {
        return authorName;
    }

    public SeedComment setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public SeedComment[] getChild() {
        return child;
    }

    public SeedComment setChild(SeedComment[] child) {
        this.child = child;
        return this;
    }

    public String getContent() {
        return content;
    }

    public SeedComment setContent(String content) {
        this.content = content;
        return this;
    }

    public int getId() {
        return id;
    }

    public SeedComment setId(int id) {
        this.id = id;
        return this;
    }

    public int getOriginalId() {
        return originalId;
    }

    public SeedComment setOriginalId(int originalId) {
        this.originalId = originalId;
        return this;
    }

    public long getTime() {
        return time;
    }

    public SeedComment setTime(long time) {
        this.time = time;
        return this;
    }
}
