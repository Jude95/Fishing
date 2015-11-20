package com.jude.fishing.module.setting;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

@RequiresPresenter(MsgSetPresenter.class)
public class MsgSetActivity extends BeamBaseActivity<MsgSetPresenter> implements CompoundButton.OnCheckedChangeListener {

    public static final String CHAT_NOTIFY = "chatNotify";

    @InjectView(R.id.sc_chat_notify)
    SwitchCompat chatNotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity_msg_set);
        ButterKnife.inject(this);

        initView();

        chatNotify.setOnCheckedChangeListener(this);
    }

    private void initView() {
        chatNotify.setChecked(getPresenter().getPrefer(CHAT_NOTIFY));
    }

    @OnClick(R.id.ll_chat_notify)
    public void changeChat() {
        chatNotify.toggle();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.sc_chat_notify) {
            getPresenter().setPrefer(CHAT_NOTIFY, isChecked);
        }
    }
}
