package com.jude.fishing.module.place;

import android.os.Bundle;

import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.fishing.model.PlaceModel;
import com.jude.fishing.model.entities.PlaceBrief;

/**
 * Created by Mr.Jude on 2015/9/23.
 */
public class UserPlacePresenter extends BeamListActivityPresenter<UserPlaceActivity,PlaceBrief> {

    @Override
    protected void onCreate(UserPlaceActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        PlaceModel.getInstance().getUserPlaces().subscribe(getRefreshSubscriber());
    }
}
