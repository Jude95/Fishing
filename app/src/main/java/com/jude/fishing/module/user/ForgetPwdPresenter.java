package com.jude.fishing.module.user;


import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.utils.JUtils;

import cn.smssdk.gui.SMSManager;

/**
 * Created by Mr.Jude on 2015/8/13.
 */
public class ForgetPwdPresenter extends Presenter<ForgetPwdActivity> {
    SMSManager smsManager;
    String phone;

    @Override
    protected void onCreate(ForgetPwdActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        smsManager = new SMSManager();
    }

    @Override
    protected void onCreateView(ForgetPwdActivity view) {
        super.onCreateView(view);
        smsManager.registerTimeListener(getView());
    }

    public void reSendMessage(String phone) {
        this.phone = phone;
        smsManager.sendMessage(getView(), phone);
    }

    public void resetPass(String code,String password) {
        getView().getExpansion().showProgressDialog("重新绑定");
        AccountModel.getInstance().resetPass(phone, code, password)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        JUtils.Toast("重置密码成功");
                        getView().finish();
                    }
                });
    }
}
