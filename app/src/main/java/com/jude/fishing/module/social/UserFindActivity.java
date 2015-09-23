package com.jude.fishing.module.social;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.list.BeamListActivity;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.fishing.R;
import com.jude.fishing.model.bean.PersonBrief;
import com.jude.fishing.module.user.UserAttentionViewHolder;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/9/23.
 */
@RequiresPresenter(UserFindPresenter.class)
public class UserFindActivity extends BeamListActivity<UserFindPresenter, PersonBrief> {

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
        search.setOnClickListener(v -> {
            JUtils.closeInputMethod(this);
            recycler.showProgress();
            getPresenter().search(word.getText().toString());
        });
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new UserAttentionViewHolder(parent);
    }

    @Override
    public int getLayout() {
        return R.layout.social_activity_find;
    }

    @Override
    protected ListConfig getConfig() {
        return super.getConfig().setRefreshAble(false);
    }
}
