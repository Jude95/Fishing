package com.jude.fishing.module.bbs;

import android.view.ViewGroup;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListFragment;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.R;
import com.jude.fishing.model.bean.Seed;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
@RequiresPresenter(BBSPresenter.class)
public class BBSFragment extends BeamListFragment<BBSPresenter,Seed> {

    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setLoadmoreAble(true);
    }

    @Override
    public int getLayout() {
        return R.layout.bbs_fragment_main;
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup viewGroup, int i) {
        return new SeedViewHolder(viewGroup);
    }
}
