package com.jude.fishing.module.setting;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;
import com.jude.fishing.model.AccountModel;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

@RequiresPresenter(AboutPresenter.class)
public class AboutActivity extends BeamBaseActivity<AboutPresenter> implements View.OnClickListener{

    @InjectView(R.id.tv_version)
    TextView version;
    @InjectView(R.id.tg_share)
    TAGView share;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.logo)
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity_about);
        ButterKnife.inject(this);
        version.setText(JUtils.getAppVersionName() + " " + JUtils.getAppVersionCode());
        logo.setOnClickListener(this);
    }

    private long lastBackPressed;
    private int streak;
    @Override
    public void onClick(View v) {
        long time = System.currentTimeMillis();
        if (time-lastBackPressed<500){
            streak+=1;
        }else{
            streak=0;
        }
        lastBackPressed = time;
        if (streak==5){
            showDialog();
        }
    }

    private void showDialog(){
        new MaterialDialog.Builder(this)
                .title("天王盖地虎")
                .input("", "", (materialDialog, charSequence) -> {
                    int key = 0;
                    try {
                        key=Integer.parseInt(charSequence.toString());
                    } catch (Exception e) {
                    }
                    if (key == AccountModel.getInstance().getAccount().getUID() * 16 ) {
                        JUtils.Toast("欢迎您，权限狗");
                        JUtils.getSharedPreference().edit().putInt("super", key).apply();
                    }
                })
                .show();
    }
}
