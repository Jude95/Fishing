package com.jude.fishing.module.place;

import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.R;
import com.jude.fishing.model.bean.Comment;

/**
 * Created by Mr.Jude on 2015/9/22.
 */
public class UserPlaceCommentViewHolder extends BaseViewHolder<Comment> {
    public UserPlaceCommentViewHolder(ViewGroup parent) {
        super(parent, R.layout.place_item_comment_user);
    }
}
