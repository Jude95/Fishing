package com.jude.fishing.module.place;

import android.os.Bundle;

import com.jude.beam.expansion.list.BeamListFragmentPresenter;
import com.jude.fishing.model.LocationModel;
import com.jude.fishing.model.PlaceModel;
import com.jude.fishing.model.entities.PlaceBrief;

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
        PlaceModel.getInstance().updatePlacesByDistance(LocationModel.getInstance().getCurLocation().getLatitude(), LocationModel.getInstance().getCurLocation().getLongitude())
                .unsafeSubscribe(getRefreshSubscriber());
    }
}
