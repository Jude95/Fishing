package com.jude.fishing.module.setting;

import android.os.Bundle;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;

@RequiresPresenter(MsgSetPresenter.class)
public class MsgSetActivity extends BeamBaseActivity<MsgSetPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity_msg_set);
    }
}
