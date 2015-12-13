package com.jude.fishing.module.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.CommonModel;
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
    @InjectView(R.id.tv_version)
    TextView version;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.ll_develop)
    LinearLayout llDevelop;
    @InjectView(R.id.divider_develop)
    View dividerDevelop;
    @InjectView(R.id.divider_develop2)
    View dividerDevelop2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity_set);
        ButterKnife.inject(this);

        changePwd.setOnClickListener(v -> getPresenter().changePwd());
        bindPhone.setOnClickListener(v -> getPresenter().bindPhone());
        msgSet.setOnClickListener(v -> startActivity(new Intent(this, MsgSetActivity.class)));
        feed.setOnClickListener(v -> startActivity(new Intent(this, FeedActivity.class)));
        about.setOnClickListener(v -> startActivity(new Intent(this, AboutActivity.class)));
        llDevelop.setOnClickListener(v -> startActivity(new Intent(this, DevelopActivity.class)));
        update.setOnClickListener(v -> {
            CommonModel.getInstance().forceUpdate(this);
        });
        version.setText("当前版本：" + JUtils.getAppVersionName());
        if (AccountModel.getInstance().checkIsSuper()) {
            llDevelop.setVisibility(View.VISIBLE);
            dividerDevelop.setVisibility(View.VISIBLE);
            dividerDevelop2.setVisibility(View.VISIBLE);
        }
    }

}
