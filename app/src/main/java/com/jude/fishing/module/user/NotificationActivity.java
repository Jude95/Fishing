package com.jude.fishing.module.user;

import android.view.ViewGroup;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListActivity;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.model.entities.Notification;

/**
 * Created by zhuchenxi on 15/10/23.
 */
@RequiresPresenter(NotificationPresenter.class)
public class NotificationActivity extends BeamListActivity<NotificationPresenter,Notification> {
    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setLoadmoreAble(true);
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new NotificationViewHolder(parent);
    }
}
