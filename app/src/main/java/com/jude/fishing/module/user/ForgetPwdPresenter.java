package com.jude.fishing.module.user;


import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.smssdk_mob.SMSManager;
import com.jude.utils.JUtils;


/**
 * Created by Mr.Jude on 2015/8/13.
 */
public class ForgetPwdPresenter extends Presenter<ForgetPwdActivity> {
    String phone;

    @Override
    protected void onCreate(ForgetPwdActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
    }

    @Override
    protected void onCreateView(ForgetPwdActivity view) {
        super.onCreateView(view);
        SMSManager.getInstance().registerTimeListener(getView());
    }

    public void reSendMessage(String phone) {
        this.phone = phone;
        SMSManager.getInstance().sendMessage(getView(),"86", phone);
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
