package com.jude.fishing.module.user;

import android.os.Bundle;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;

import butterknife.ButterKnife;

/**
 * Created by heqiang on 2015/9/23.
 */
@RequiresPresenter(UserData2Presenter.class)
public class UserData2Activity extends BeamBaseActivity<UserData2Presenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_data2);
        ButterKnife.inject(this);
    }
}