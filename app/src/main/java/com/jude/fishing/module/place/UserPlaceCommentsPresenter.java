package com.jude.fishing.module.place;

import android.os.Bundle;

import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.fishing.model.PlaceModel;
import com.jude.fishing.model.entities.PlaceComment;

/**
 * Created by Mr.Jude on 2015/9/22.
 */
public class UserPlaceCommentsPresenter extends BeamListActivityPresenter<UserPlaceCommentsActivity,PlaceComment> {

    @Override
    protected void onCreate(UserPlaceCommentsActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        PlaceModel.getInstance().getUserPlacesComments(getView().getIntent().getIntExtra("id",0)).subscribe(getRefreshSubscriber());
    }
}
