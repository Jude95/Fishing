package com.jude.fishing.module.place;

import android.os.Bundle;

import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.fishing.model.PlaceModel;
import com.jude.fishing.model.entities.Evaluate;

/**
 * Created by zhuchenxi on 15/10/3.
 */
public class EvaluatePresenter extends BeamListActivityPresenter<EvaluateActivity,Evaluate> {
    @Override
    protected void onCreate(EvaluateActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        PlaceModel.getInstance().getPlacesComments(getView().getIntent().getIntExtra("id",0),0).unsafeSubscribe(getRefreshSubscriber());
    }

    @Override
    public void onLoadMore() {
        PlaceModel.getInstance().getPlacesComments(getView().getIntent().getIntExtra("id",0), getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }
}
