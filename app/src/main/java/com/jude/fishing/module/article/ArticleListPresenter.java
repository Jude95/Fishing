package com.jude.fishing.module.article;

import android.os.Bundle;

import com.jude.beam.expansion.list.BeamListFragmentPresenter;
import com.jude.fishing.model.ArticleModel;
import com.jude.fishing.model.entities.Article;

/**
 * Created by zhuchenxi on 15/12/24.
 */
public class ArticleListPresenter extends BeamListFragmentPresenter<ArticleListFragment,Article> {
    int type;

    @Override
    protected void onCreate(ArticleListFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        type = getView().getArguments().getInt("type");
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        ArticleModel.getInstance().getArticleList(type,0).unsafeSubscribe(getRefreshSubscriber());

    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        ArticleModel.getInstance().getArticleList(type,getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }
}
