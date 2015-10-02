package com.jude.fishing.module.place;

import android.os.Bundle;

import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.fishing.model.PlaceModel;
import com.jude.fishing.model.entities.PlaceBrief;

/**
 * Created by Mr.Jude on 2015/9/22.
 */
public class CollectionPlacePresenter extends BeamListActivityPresenter<CollectionPlaceActivity,PlaceBrief>{

    @Override
    protected void onCreate(CollectionPlaceActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        PlaceModel.getInstance().getCollectionPlaces(getView().getIntent().getIntExtra("id",0)).subscribe(getRefreshSubscriber());
    }

}
