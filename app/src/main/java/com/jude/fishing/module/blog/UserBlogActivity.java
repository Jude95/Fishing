package com.jude.fishing.module.blog;

import android.view.ViewGroup;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListActivity;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.model.bean.Seed;

/**
 * Created by Mr.Jude on 2015/9/19.
 */
@RequiresPresenter(UserBlogPresenter.class)
public class UserBlogActivity extends BeamListActivity<UserBlogPresenter,Seed> {
    
    @Override
    protected BaseViewHolder getViewHolder(ViewGroup viewGroup, int i) {
        return new SeedViewHolder(viewGroup);
    }

    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setLoadmoreAble(true);
    }
}
