package com.jude.fishing.module.place;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListActivity;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.R;
import com.jude.fishing.model.entities.Evaluate;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhuchenxi on 15/10/3.
 */
@RequiresPresenter(EvaluatePresenter.class)
public class EvaluateActivity extends BeamListActivity<EvaluatePresenter, Evaluate> {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.recycler)
    EasyRecyclerView recycler;
    @InjectView(R.id.write)
    FloatingActionButton write;

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup viewGroup, int i) {
        return new EvaluateViewHolder(viewGroup);
    }

    @Override
    public int getLayout() {
        return R.layout.place_activity_evaluate;
    }

    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setLoadmoreAble(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
        write.setOnClickListener(v->{
            startActivity(new Intent(this,WriteEvaluateActivity.class));
        });
    }
}
