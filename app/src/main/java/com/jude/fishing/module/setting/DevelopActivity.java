package com.jude.fishing.module.setting;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;
import com.jude.fishing.model.AccountModel;
import com.jude.utils.JUtils;
import com.umeng.message.UmengRegistrar;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/12/13.
 */
@RequiresPresenter(DevelopPresenter.class)
public class DevelopActivity extends BeamBaseActivity<DevelopPresenter> {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tv_user_id)
    TextView tvUserId;
    @InjectView(R.id.container_user_id)
    LinearLayout containerUserId;
    @InjectView(R.id.tv_user_token)
    TextView tvUserToken;
    @InjectView(R.id.container_user_token)
    LinearLayout containerUserToken;
    @InjectView(R.id.tv_rongyun_token)
    TextView tvRongyunToken;
    @InjectView(R.id.container_rongyun_token)
    LinearLayout containerRongyunToken;
    @InjectView(R.id.tv_device_token)
    TextView tvDeviceToken;
    @InjectView(R.id.container_device_token)
    LinearLayout containerDeviceToken;
    @InjectView(R.id.switch_user_debug_server)
    Switch switchUserDebugServer;
    @InjectView(R.id.container_server_debug)
    LinearLayout containerServerDebug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity_develop);
        ButterKnife.inject(this);
        tvUserId.setText(AccountModel.getInstance().getAccount().getUID() + "");
        containerUserId.setOnClickListener(v -> {
            new MaterialDialog.Builder(this)
                    .items(new String[]{"Copy user id"})
                    .itemsCallback((materialDialog, view, i, charSequence) -> JUtils.copyToClipboard(AccountModel.getInstance().getAccount().getUID() + ""))
                    .show();
        });
        tvDeviceToken.setText(UmengRegistrar.getRegistrationId(this));
        containerDeviceToken.setOnClickListener(v -> {
            new MaterialDialog.Builder(this)
                    .items(new String[]{"Copy device token"})
                    .itemsCallback((materialDialog, view, i, charSequence) -> JUtils.copyToClipboard(UmengRegistrar.getRegistrationId(this)))
                    .show();
        });
        tvRongyunToken.setText(AccountModel.getInstance().getAccount().getRongToken());
        containerRongyunToken.setOnClickListener(v -> {
            new MaterialDialog.Builder(this)
                    .items(new String[]{"Copy rongyun token"})
                    .itemsCallback((materialDialog, view, i, charSequence) -> JUtils.copyToClipboard(AccountModel.getInstance().getAccount().getRongToken()))
                    .show();
        });
        tvUserToken.setText(AccountModel.getInstance().getAccount().getToken());
        containerUserToken.setOnClickListener(v -> {
            new MaterialDialog.Builder(this)
                    .items(new String[]{"Copy user token"})
                    .itemsCallback((materialDialog, view, i, charSequence) -> JUtils.copyToClipboard(AccountModel.getInstance().getAccount().getToken()))
                    .show();
        });
        switchUserDebugServer.setChecked(JUtils.getSharedPreference().getBoolean("DebugServer",false));
        switchUserDebugServer.setOnCheckedChangeListener((buttonView, isChecked) -> {
            JUtils.getSharedPreference().edit().putBoolean("DebugServer",isChecked).apply();
            JUtils.Toast("退出重启生效");
        });
    }


}
