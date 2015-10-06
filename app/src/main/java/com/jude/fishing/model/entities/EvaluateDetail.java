package com.jude.fishing.model.entities;

/**
 * Created by zhuchenxi on 15/10/3.
 */
public class EvaluateDetail extends Evaluate {
    private EvaluateComment[] comments;

    public EvaluateDetail(String address, String authorAvatar, int authorId, String authorName, int commentCount, String content, int id, String[] images, int placeId, String placeName, String placePreview, int score, long time, EvaluateComment[] comments) {
        super(address, authorAvatar, authorId, authorName, commentCount, content, id, images, placeId, placeName, placePreview, score, time);
        this.comments = comments;
    }

    public EvaluateComment[] getComments() {
        return comments;
    }



}
