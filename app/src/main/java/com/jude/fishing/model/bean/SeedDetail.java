package com.jude.fishing.model.bean;

/**
 * Created by zhuchenxi on 15/9/27.
 */
public class SeedDetail extends Seed{
    private PersonBrief[] praiseMember;
    private SeedComment[] comment;

    public SeedComment[] getComment() {
        return comment;
    }

    public PersonBrief[] getPraiseMember() {
        return praiseMember;
    }

    public SeedDetail(String id, String authorId, String authorAvatar, String authorName, long time, String address, String content, String[] images, int praiseCount, boolean praiseStatus, int commentCount) {
        super(id, authorId, authorAvatar, authorName, time, address, content, images, praiseCount, praiseStatus, commentCount);
    }
}
