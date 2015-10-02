package com.jude.fishing.module.blog;

import android.os.Bundle;

import com.jude.beam.expansion.list.BeamListFragmentPresenter;
import com.jude.fishing.model.BlogModel;
import com.jude.fishing.model.entities.Seed;

/**
 * Created by Mr.Jude on 2015/9/17.
 */
public class BlogListPresenter extends BeamListFragmentPresenter<BlogListFragment,Seed> {

    @Override
    protected void onCreate(BlogListFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        onRefresh();
    }

    @Override
    public void onLoadMore() {
        BlogModel.getInstance().getSeed(0,getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }

    @Override
    public void onRefresh() {
        BlogModel.getInstance().getSeed(0,getCurPage()).unsafeSubscribe(getRefreshSubscriber());
    }
}
