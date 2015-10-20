package com.jude.fishing.module.social;

import android.view.ViewGroup;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.model.entities.Contact;

/**
 * Created by heqiang on 2015/10/20.
 */
@RequiresPresenter(ContactPresenter.class)
public class ContactActivity extends BeamListActivity<ContactPresenter,Contact>{

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new ContactAttentionViewHolder(parent);
    }
}
