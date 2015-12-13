package com.jude.fishing.module.setting;


import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.smssdk_mob.SMSManager;


/**
 * Created by Mr.Jude on 2015/8/13.
 */
public class BindChangePresenter extends Presenter<BindChangeActivity> {
    String phone;

    @Override
    protected void onCreate(BindChangeActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
    }

    @Override
    protected void onCreateView(BindChangeActivity view) {
        super.onCreateView(view);
        SMSManager.getInstance().registerTimeListener(getView());
    }

    public void reSendMessage(String phone) {
        this.phone = phone;
        SMSManager.getInstance().sendMessage(getView(),"86", phone);
    }

    public void send(String code, String password) {
        getView().getExpansion().showProgressDialog("重新绑定");
        AccountModel.getInstance().bindTel(phone,code,password)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        getView().finish();
                    }
                });
    }
}
