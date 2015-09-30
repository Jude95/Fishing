package com.jude.fishing.module.place;

import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.beam.expansion.data.BeamDataFragmentPresenter;
import com.jude.fishing.model.PlaceModel;
import com.jude.fishing.model.bean.PlaceBrief;

/**
 * Created by Mr.Jude on 2015/9/28.
 */
public class PlaceMapPresenter extends BeamDataFragmentPresenter<PlaceMapFragment,PlaceBrief[]> {

    @Override
    protected void onCreate(PlaceMapFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        PlaceModel.getInstance().getPlaces(0).subscribe(this);
    }
}
