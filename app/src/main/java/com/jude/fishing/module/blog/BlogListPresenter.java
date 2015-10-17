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
        JUtils.Log("onCreate" + style);
    }

    @Override
    protected void onCreateView(BlogListFragment view) {
        super.onCreateView(view);
        onRefresh();
        JUtils.Log("onCreateView"+style);
    }

    @Override
    protected void onDestroyView() {
        super.onDestroyView();
        JUtils.Log("onDestroyView"+style);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JUtils.Log("onDestroy"+style);
    }

    @Override
    public void onLoadMore() {
        if (0 == style) {
            BlogModel.getInstance().getBlogGround(getCurPage()).unsafeSubscribe(getMoreSubscriber());
        } else if (1 == style) {
            BlogModel.getInstance().getBlogNearby(getCurPage(), 20, location.getLatitude(), location.getLongitude()).unsafeSubscribe(getMoreSubscriber());
        } else if (2 == style) {
            BlogModel.getInstance().getBlogFriend(getCurPage()).unsafeSubscribe(getMoreSubscriber());
        }
    }

    @Override
    public void onRefresh() {
        if (0 == style) {
            BlogModel.getInstance().getBlogGround(0).unsafeSubscribe(getRefreshSubscriber());
        } else if (1 == style) {
            BlogModel.getInstance().getBlogNearby(0,20,location.getLatitude(),location.getLongitude()).unsafeSubscribe(getRefreshSubscriber());
        } else if (2 == style) {
            BlogModel.getInstance().getBlogFriend(0).unsafeSubscribe(getRefreshSubscriber());
        }
    }
}
