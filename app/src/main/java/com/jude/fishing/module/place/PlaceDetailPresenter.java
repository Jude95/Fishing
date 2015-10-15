package com.jude.fishing.module.place;

import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.fishing.model.PlaceModel;
import com.jude.fishing.model.entities.PlaceDetail;

/**
 * Created by Mr.Jude on 2015/9/23.
 */
public class PlaceDetailPresenter extends BeamDataActivityPresenter<PlaceDetailActivity,PlaceDetail> {
    private boolean collected;
    private int id;
    @Override
    protected void onCreate(PlaceDetailActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        id = getView().getIntent().getIntExtra("id",0);
        PlaceModel.getInstance().getPlaceDetail(getView().getIntent().getIntExtra("id",0))
                .doOnNext(placeDetail -> collected = placeDetail.isCollected())
                .subscribe(this);
    }

    public boolean collect(){
        if (collected)
            PlaceModel.getInstance().unCollectPlace(id).subscribe();
        else
            PlaceModel.getInstance().collectPlace(id).subscribe();
        collected = !collected;
        return collected;
    }
}
