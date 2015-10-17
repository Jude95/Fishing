package com.jude.fishing.module.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.service.ServiceResponse;

import cn.smssdk.gui.SMSManager;

/**
 * Created by Mr.Jude on 2015/9/13.
 */
public class RegisterVerifyPresenter extends Presenter<RegisterVerifyActivity> {
    SMSManager smsManager;
    private String number;
    private String password;

    @Override
    protected void onCreate(RegisterVerifyActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        number = getView().getIntent().getStringExtra("number");
        password = getView().getIntent().getStringExtra("password");
        smsManager = new SMSManager();
    }

    @Override
    protected void onCreateView(RegisterVerifyActivity view) {
        super.onCreateView(view);
        smsManager.registerTimeListener(getView());
        smsManager.sendMessage(getView(), number);
    }

    public void reSendMessage() {
        smsManager.sendMessage(getView(), number);
    }

    public void send(String code) {
        getView().getExpansion().showProgressDialog("注册中");
        AccountModel.getInstance().register(number, password, code)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        getView().startActivity(new Intent(getView(), UserDataActivity.class));
                        getView().setResult(Activity.RESULT_OK);
                        getView().finish();
                    }
                });
    }

}
