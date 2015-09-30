package com.jude.fishing.module.place;

import android.os.Bundle;

import com.jude.beam.expansion.list.BeamListFragmentPresenter;
import com.jude.fishing.model.PlaceModel;
import com.jude.fishing.model.bean.PlaceBrief;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
public class PlaceListPresenter extends BeamListFragmentPresenter<PlaceListFragment,PlaceBrief> {

    @Override
    protected void onCreate(PlaceListFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        PlaceModel.getInstance().getPlaces(0).unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        PlaceModel.getInstance().getPlaces(getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }
}
