package com.jude.fishing.module.blog;

import android.os.Bundle;

import com.jude.beam.expansion.list.BeamListFragmentPresenter;
import com.jude.fishing.model.BlogModel;
import com.jude.fishing.model.LocationModel;
import com.jude.fishing.model.entities.Location;
import com.jude.fishing.model.entities.Seed;
import com.jude.utils.JUtils;

/**
 * Created by Mr.Jude on 2015/9/17.
 */
public class BlogListPresenter extends BeamListFragmentPresenter<BlogListFragment, Seed> {
    int style;
    Location location;

    @Override
    protected void onCreate(BlogListFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        style = getView().getArguments().getInt("style", 0);
        location = LocationModel.getInstance().getCurLocation();
        JUtils.Log("location"+location.getLatitude()+"-"+location.getLongitude());
        onRefresh();
    }

    @Override
    public void onLoadMore() {
//        BlogModel.getInstance().getSeed(0,getCurPage()).unsafeSubscribe(getMoreSubscriber());
        if (0 == style) {
            BlogModel.getInstance().getWeiboGround(getCurPage()).unsafeSubscribe(getMoreSubscriber());
        } else if (1 == style) {
            BlogModel.getInstance().getWeiboFriend(getCurPage()).unsafeSubscribe(getMoreSubscriber());
        } else if (2 == style) {
            BlogModel.getInstance().getWeiboNearby(getCurPage(),20,location.getLatitude(),location.getLongitude()).unsafeSubscribe(getMoreSubscriber());
        } else {
            BlogModel.getInstance().getWeiboMy(getCurPage()).unsafeSubscribe(getMoreSubscriber());
        }
    }

    @Override
    public void onRefresh() {
//        BlogModel.getInstance().getSeed(0,0).unsafeSubscribe(getRefreshSubscriber());
        if (0 == style) {
            BlogModel.getInstance().getWeiboGround(0).unsafeSubscribe(getRefreshSubscriber());
        } else if (1 == style) {
            BlogModel.getInstance().getWeiboFriend(0).unsafeSubscribe(getRefreshSubscriber());
        } else if (2 == style) {
            BlogModel.getInstance().getWeiboNearby(0,20,location.getLatitude(),location.getLongitude()).unsafeSubscribe(getRefreshSubscriber());
        } else {
            BlogModel.getInstance().getWeiboMy(0).unsafeSubscribe(getRefreshSubscriber());
        }
    }
}
