package com.jude.fishing.module.blog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListFragment;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.R;
import com.jude.fishing.model.entities.Seed;
import com.jude.utils.JUtils;

/**
 * Created by Mr.Jude on 2015/9/17.
 */
@RequiresPresenter(BlogListPresenter.class)
public class BlogListFragment extends BeamListFragment<BlogListPresenter,Seed> {
    @Override
    protected BaseViewHolder getViewHolder(ViewGroup viewGroup, int i) {
        return new SeedViewHolder(viewGroup,this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JUtils.Log("View onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        JUtils.Log("onCreateView begin");
        View v = super.onCreateView(inflater, container, savedInstanceState);
        JUtils.Log("onCreateView end");
        return v;
    }

    @Override
    public int getLayout() {
        return R.layout.include_recyclerview;
    }

    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setLoadmoreAble(true);
    }
}
