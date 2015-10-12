package com.jude.fishing.model.entities;

import java.util.List;

/**
 * Created by zhuchenxi on 15/9/27.
 */
public class SeedDetail extends Seed{
    private List<PersonBrief> praiseMember;
    private List<SeedComment> comments;

    public List<SeedComment> getComments() {
        return comments;
    }

    public List<PersonBrief> getPraiseMember() {
        return praiseMember;
    }

    public SeedDetail(int id, int authorId, String authorAvatar, String authorName, long time, String address, String content, String[] images, int praiseCount, boolean praiseStatus, int commentCount,List<PersonBrief> praiseMember, List<SeedComment> comment) {
        super(id, authorId, authorAvatar, authorName, time, address, content, images, praiseCount, praiseStatus, commentCount);
        this.comments = comment;
        this.praiseMember = praiseMember;
    }
}
