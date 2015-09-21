package com.jude.fishing.module.user;

import android.view.ViewGroup;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.model.bean.PersonBrief;

/**
 * Created by zhuchenxi on 15/9/20.
 */
@RequiresPresenter(FansPresenter.class)
public class FansActivity extends BeamListActivity<FansPresenter,PersonBrief> {

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup viewGroup, int i) {
        return new UserAttentionViewHolder(viewGroup);
    }

}
