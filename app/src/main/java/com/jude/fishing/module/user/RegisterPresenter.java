package com.jude.fishing.module.user;

import android.content.Intent;

import com.jude.beam.bijection.Presenter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.service.ServiceResponse;

/**
 * Created by Mr.Jude on 2015/9/13.
 */
public class RegisterPresenter extends Presenter<RegisterActivity> {
    public void checkTel(String tel, String password) {
        getView().getExpansion().showProgressDialog("注册中...");
        AccountModel.getInstance().checkTel(tel).subscribe(new ServiceResponse<Object>() {
            @Override
            public void onNext(Object o) {
                Intent i = new Intent(getView(), RegisterVerifyActivity.class);
                i.putExtra("number", tel);
                i.putExtra("password", password);
                getView().startActivity(i);
                getView().finish();
            }

            @Override
            public void onCompleted() {
                getView().getExpansion().dismissProgressDialog();
            }
        });
    }
}
