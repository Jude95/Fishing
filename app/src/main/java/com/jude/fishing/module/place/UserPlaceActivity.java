package com.jude.fishing.module.place;

import android.view.ViewGroup;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListActivity;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.model.entities.PlaceBrief;

/**
 * Created by Mr.Jude on 2015/9/23.
 */
@RequiresPresenter(UserPlacePresenter.class)
public class UserPlaceActivity extends BeamListActivity<UserPlacePresenter,PlaceBrief> {
    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setRefreshAble(false);
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new PlaceStatusViewHolder(parent);
    }
}
