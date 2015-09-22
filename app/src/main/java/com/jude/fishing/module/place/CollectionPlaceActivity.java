package com.jude.fishing.module.place;

import android.view.ViewGroup;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListActivity;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.model.bean.PlaceBrief;

/**
 * Created by Mr.Jude on 2015/9/22.
 */
@RequiresPresenter(CollectionPlacePresenter.class)
public class CollectionPlaceActivity extends BeamListActivity<CollectionPlacePresenter,PlaceBrief> {

    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setRefreshAble(false);
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new PlaceViewHolder(parent);
    }
}
