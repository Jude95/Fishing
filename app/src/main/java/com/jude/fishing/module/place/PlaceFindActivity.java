package com.jude.fishing.module.place;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListActivity;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.R;
import com.jude.fishing.model.entities.PlaceBrief;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/9/23.
 */
@RequiresPresenter(PlaceFindPresenter.class)
public class PlaceFindActivity extends BeamListActivity<PlaceFindPresenter, PlaceBrief> {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.word)
    EditText word;
    @InjectView(R.id.search)
    TextView search;
    @InjectView(R.id.recycler)
    EasyRecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this, getWindow().getDecorView());
        word.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getPresenter().search(word.getText().toString());
            }
        });
        search.setOnClickListener(v -> {
            JUtils.closeInputMethod(this);
        });
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new PlaceViewHolder(parent);
    }

    @Override
    public int getLayout() {
        return R.layout.place_activity_find;
    }

    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setRefreshAble(false);
    }
}
