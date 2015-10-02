package com.jude.fishing.module.place;

import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.fishing.model.PlaceModel;
import com.jude.fishing.model.entities.PlaceDetail;

/**
 * Created by Mr.Jude on 2015/9/23.
 */
public class PlaceDetailPresenter extends BeamDataActivityPresenter<PlaceDetailActivity,PlaceDetail> {

    @Override
    protected void onCreate(PlaceDetailActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        PlaceModel.getInstance().getPlaceDetail(getView().getIntent().getIntExtra("id",0)).subscribe(this);
    }
}
