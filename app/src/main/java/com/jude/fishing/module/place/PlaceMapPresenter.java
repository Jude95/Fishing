package com.jude.fishing.module.place;

import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.fishing.model.LocationModel;
import com.jude.fishing.model.PlaceModel;

import rx.Observable;

/**
 * Created by Mr.Jude on 2015/9/28.
 */
public class PlaceMapPresenter extends Presenter<PlaceMapFragment> {

    @Override
    protected void onCreate(PlaceMapFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
    }

    @Override
    protected void onCreateView(PlaceMapFragment view) {
        super.onCreateView(view);
        PlaceModel.getInstance().updatePlacesByDistance(LocationModel.getInstance().getCurLocation().getLatitude(), LocationModel.getInstance().getCurLocation().getLongitude())
                .doOnNext(placeBriefs -> getView().clearMarker())
                .flatMap(Observable::from)
                .subscribe(placeBrief -> {
                    getView().addMarker(placeBrief);
                });
    }
}
