package com.jude.fishing.module.article;

import android.view.ViewGroup;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListFragment;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.R;
import com.jude.fishing.model.entities.Article;

/**
 * Created by zhuchenxi on 15/12/24.
 */
@RequiresPresenter(ArticleListPresenter.class)
public class ArticleListFragment extends BeamListFragment<ArticleListPresenter,Article> {
    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new ArticleViewHolder(parent);
    }

    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setRefreshAble(true).setLoadmoreAble(true);
    }

    @Override
    public int getLayout() {
        return R.layout.include_recyclerview;
    }
}
