package com.jude.fishing.module.date;

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
import com.jude.fishing.model.entities.Date;

/**
 * Created by heqiang on 2015/12/2.
 */
@RequiresPresenter(DatePresenter.class)
public class DateFragment extends BeamListFragment<DatePresenter,Date>{
    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new DateViewHolder(parent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.findViewById(R.id.write).setOnClickListener(v -> getPresenter().goToWrite());
        return view;
    }

    @Override
    public int getLayout() {
        return R.layout.date_fragment_main;
    }

    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setLoadmoreAble(true);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null)
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
    }
}
