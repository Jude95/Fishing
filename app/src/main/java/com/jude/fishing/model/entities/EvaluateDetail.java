package com.jude.fishing.model.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zhuchenxi on 15/10/3.
 */
public class EvaluateDetail extends Evaluate {
    @SerializedName("comment")
    private List<EvaluateComment> comments;

    public EvaluateDetail(String address, String authorAvatar, int authorId, String authorName, int commentCount, String content, int id, List<String> images, int placeId, String placeName, String placePreview, int score, long time, List<EvaluateComment> comments) {
        super(address, authorAvatar, authorId, authorName, commentCount, content, id, images, placeId, placeName, placePreview, score, time);
        this.comments = comments;
    }

    public List<EvaluateComment> getComments() {
        return comments;
    }



}
