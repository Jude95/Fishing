package com.jude.fishing.module.place;

import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.fishing.model.PlaceModel;
import com.jude.fishing.model.entities.PlaceBrief;

/**
 * Created by Mr.Jude on 2015/9/23.
 */
public class PlaceFindPresenter extends BeamListActivityPresenter<PlaceFindActivity,PlaceBrief> {

    @Override
    protected void onCreateView(PlaceFindActivity view) {
        super.onCreateView(view);
        view.getListView().showRecycler();
        search("");
    }

    public void search(String word){
        PlaceModel.getInstance().findPlacesByKey(word)
                .unsafeSubscribe(getRefreshSubscriber());
    }
}
