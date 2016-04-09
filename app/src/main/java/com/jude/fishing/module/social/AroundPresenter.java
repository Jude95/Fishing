package com.jude.fishing.module.social;

import android.os.Bundle;

import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.fishing.model.SocialModel;
import com.jude.fishing.model.entities.PersonAround;

import rx.Observable;

/**
 * Created by Mr.Jude on 2015/9/23.
 */
public class AroundPresenter extends BeamListActivityPresenter<AroundActivity,PersonAround>{
    @Override
    protected void onCreate(AroundActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        onRefresh();
    }

    @Override
    public void onLoadMore() {
        SocialModel.getInstance().getAround(getCurPage())
                .flatMap(Observable::from)
                .filter(personAround -> personAround.getLat() != 29.530000||personAround.getLng()!=106.600000)
                .toList()
                .unsafeSubscribe(getMoreSubscriber());
    }

    @Override
    public void onRefresh() {
        SocialModel.getInstance().getAround(0)
                .flatMap(Observable::from)
                .filter(personAround -> personAround.getLat() != 29.530000||personAround.getLng()!=106.600000)
                .toList()
                .unsafeSubscribe(getRefreshSubscriber());
    }
}
