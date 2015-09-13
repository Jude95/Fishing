package com.jude.fishing.module.bbs;

import android.os.Bundle;

import com.jude.beam.expansion.list.BeamListFragmentPresenter;
import com.jude.fishing.model.BBSModel;
import com.jude.fishing.model.bean.Seed;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
public class BBSPresenter extends BeamListFragmentPresenter<BBSFragment,Seed> {

    @Override
    protected void onCreate(BBSFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        onRefresh();
    }

    @Override
    public void onLoadMore() {
        BBSModel.getInstance().getSeed(0,getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }

    @Override
    public void onRefresh() {
        BBSModel.getInstance().getSeed(0,getCurPage()).unsafeSubscribe(getRefreshSubscriber());
    }
}
