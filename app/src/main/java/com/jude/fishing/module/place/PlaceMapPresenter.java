package com.jude.fishing.module.place;

import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.fishing.model.LocationModel;
import com.jude.fishing.model.PlaceModel;
import com.jude.fishing.model.entities.PlaceBrief;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.utils.JUtils;

import rx.Observable;
import rx.Subscription;

/**
 * Created by Mr.Jude on 2015/9/28.
 */
public class PlaceMapPresenter extends Presenter<PlaceMapFragment> {
    private Subscription subscription;
    @Override
    protected void onCreate(PlaceMapFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
    }

    @Override
    protected void onCreateView(PlaceMapFragment view) {
        super.onCreateView(view);
        subscribe();
    }

    public void subscribe(){
        JUtils.Log("subscribe");
        if (subscription!=null)subscription.unsubscribe();
        subscription = PlaceModel.getInstance().getPlacesByDistance(LocationModel.getInstance().getCurLocation().getLatitude(), LocationModel.getInstance().getCurLocation().getLongitude())
                .doOnNext(placeBriefs -> getView().clearMarker())
                .flatMap(Observable::from)
                .subscribe(new ServiceResponse<PlaceBrief>() {
                    @Override
                    public void onNext(PlaceBrief placeBrief) {
                        getView().addMarker(placeBrief);
                    }
                });
    }
}
