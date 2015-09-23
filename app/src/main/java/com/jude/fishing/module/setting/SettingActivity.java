package com.jude.fishing.module.setting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;
import com.jude.fishing.module.user.LoginActivity;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by heqiang on 2015/9/22.
 */
@RequiresPresenter(SettingPresenter.class)
public class SettingActivity extends BeamBaseActivity<SettingPresenter> {
    @InjectView(R.id.ll_change_password)
    LinearLayout changePwd;
    @InjectView(R.id.ll_bind_phone)
    LinearLayout bindPhone;
    @InjectView(R.id.ll_msg_setting)
    LinearLayout msgSet;
    @InjectView(R.id.ll_feed)
    LinearLayout feed;
    @InjectView(R.id.ll_update)
    LinearLayout update;
    @InjectView(R.id.ll_about)
    LinearLayout about;
    @InjectView(R.id.ll_logout)
    LinearLayout logout;
    @InjectView(R.id.tv_version)
    TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity_set);
        ButterKnife.inject(this);

        changePwd.setOnClickListener(v -> startActivity(new Intent(this, PwdChangeActivity.class)));
        bindPhone.setOnClickListener(v -> startActivity(new Intent(this, BindChangeActivity.class)));
        msgSet.setOnClickListener(v -> startActivity(new Intent(this, MsgSetActivity.class)));
        feed.setOnClickListener(v -> startActivity(new Intent(this, FeedActivity.class)));
        about.setOnClickListener(v -> startActivity(new Intent(this, AboutActivity.class)));
        logout.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));

        version.setText("当前版本：" + JUtils.getAppVersionName() + " " + JUtils.getAppVersionCode());
    }

}
