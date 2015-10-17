package com.jude.fishing.module.user;

import android.app.Activity;
import android.content.Intent;

import com.jude.beam.bijection.Presenter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.entities.Account;
import com.jude.fishing.model.service.ServiceResponse;

/**
 * Created by Mr.Jude on 2015/9/13.
 */
public class LoginPresenter extends Presenter<LoginActivity> {
    public static final int REGISTER = 1000;
    public void register(){
        getView().startActivityForResult(new Intent(getView(), RegisterActivity.class), REGISTER);
    }

    public void login(String name,String password){
        getView().getExpansion().showProgressDialog("登录中");
        AccountModel.getInstance().login(name, password)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Account>() {
                    @Override
                    public void onNext(Account account) {
                        getView().finish();
                    }
                });
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        if(requestCode == REGISTER && resultCode == Activity.RESULT_OK){
            getView().finish();
        }
    }
}
