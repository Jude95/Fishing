package com.jude.fishing.module.setting;

import android.os.Bundle;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

@RequiresPresenter(AboutPresenter.class)
public class AboutActivity extends BeamBaseActivity<AboutPresenter> {

    @InjectView(R.id.tv_version)
    TextView version;
    @InjectView(R.id.tg_share)
    TAGView share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity_about);
        ButterKnife.inject(this);

        version.setText(JUtils.getAppVersionName() + " " + JUtils.getAppVersionCode());
    }
}
