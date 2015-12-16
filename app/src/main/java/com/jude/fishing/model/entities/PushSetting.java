package com.jude.fishing.model.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mr.Jude on 2015/12/15.
 */
public class PushSetting {
    @SerializedName("zan")
    private boolean isPraiseNotify;
    @SerializedName("comment")
    private boolean isCommentNotify;
    @SerializedName("care")
    private boolean isCareNotify;
    @SerializedName("place")
    private boolean isPlaceNotify;

    public boolean isPraiseNotify() {
        return isPraiseNotify;
    }

    public void setIsPraiseNotify(boolean isPraiseNotify) {
        this.isPraiseNotify = isPraiseNotify;
    }

    public boolean isCommentNotify() {
        return isCommentNotify;
    }

    public void setIsCommentNotify(boolean isCommentNotify) {
        this.isCommentNotify = isCommentNotify;
    }

    public boolean isCareNotify() {
        return isCareNotify;
    }

    public void setIsCareNotify(boolean isCareNotify) {
        this.isCareNotify = isCareNotify;
    }

    public boolean isPlaceNotify() {
        return isPlaceNotify;
    }

    public void setIsPlaceNotify(boolean isPlaceNotify) {
        this.isPlaceNotify = isPlaceNotify;
    }
}
