package com.jude.fishing.module.setting;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;
import com.jude.fishing.R;
import com.jude.fishing.model.entities.PushSetting;

import butterknife.ButterKnife;
import butterknife.InjectView;

@RequiresPresenter(MsgSetPresenter.class)
public class MsgSetActivity extends BeamDataActivity<MsgSetPresenter,PushSetting> {

    public static final String CHAT_NOTIFY = "chatNotify";
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.switch_praise)
    SwitchCompat switchPraise;
    @InjectView(R.id.switch_comment)
    SwitchCompat switchComment;
    @InjectView(R.id.switch_care)
    SwitchCompat switchCare;
    @InjectView(R.id.switch_address)
    SwitchCompat switchAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity_msg_set);
        ButterKnife.inject(this);
        switchPraise.setOnCheckedChangeListener(getPresenter());
        switchComment.setOnCheckedChangeListener(getPresenter());
        switchCare.setOnCheckedChangeListener(getPresenter());
        switchAddress.setOnCheckedChangeListener(getPresenter());
    }

    @Override
    public void setData(PushSetting data) {
        super.setData(data);
        switchPraise.setChecked(data.isPraiseNotify());
        switchComment.setChecked(data.isCommentNotify());
        switchAddress.setChecked(data.isPlaceNotify());
        switchCare.setChecked(data.isCareNotify());
    }


}
