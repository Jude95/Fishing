package com.jude.fishing.module.user;

import android.os.Bundle;

import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.fishing.model.SocialModel;
import com.jude.fishing.model.entities.PersonBrief;

/**
 * Created by zhuchenxi on 15/9/20.
 */
public class AttentionPresenter extends BeamListActivityPresenter<AttentionActivity,PersonBrief> {
    int id;

    @Override
    protected void onCreate(AttentionActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        id = getView().getIntent().getIntExtra("id",0);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        SocialModel.getInstance().getAttentions(id).unsafeSubscribe(getRefreshSubscriber());
    }
}
