package com.jude.fishing.module.setting;


import android.os.Bundle;

import com.jude.beam.bijection.Presenter;

import cn.smssdk.gui.SMSManager;

/**
 * Created by Mr.Jude on 2015/8/13.
 */
public class BindChangePresenter extends Presenter<BindChangeActivity> {
    SMSManager smsManager;

    @Override
    protected void onCreate(BindChangeActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        smsManager = new SMSManager();
    }

    @Override
    protected void onCreateView(BindChangeActivity view) {
        super.onCreateView(view);
        smsManager.registerTimeListener(getView());
    }

    public void reSendMessage(String phone) {
        smsManager.sendMessage(getView(), phone);
    }

    public void send(String code) {
        getView().getExpansion().showProgressDialog("注册中");
    }
}
