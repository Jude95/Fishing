package com.jude.fishing.module.blog;

import android.os.Bundle;

import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.fishing.model.BlogModel;
import com.jude.fishing.model.entities.Seed;

/**
 * Created by Mr.Jude on 2015/9/19.
 */
public class UserBlogPresenter extends BeamListActivityPresenter<UserBlogActivity,Seed> {

    @Override
    protected void onCreate(UserBlogActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        BlogModel.getInstance().getSeed(0,getCurPage()).unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        BlogModel.getInstance().getSeed(0,getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }
}
