package com.jude.fishing.module.social;

import android.view.ViewGroup;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListActivity;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.model.entities.PersonBrief;
import com.jude.fishing.module.user.UserAttentionViewHolder;

/**
 * Created by Mr.Jude on 2015/9/23.
 */
@RequiresPresenter(AroundPresenter.class)
public class AroundActivity extends BeamListActivity<AroundPresenter,PersonBrief> {
    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setLoadmoreAble(true);
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new UserAttentionViewHolder(parent);
    }
}
