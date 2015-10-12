package com.jude.fishing.module.setting;


import android.content.Intent;

import com.jude.beam.bijection.Presenter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.module.user.LoginActivity;

/**
 * Created by Mr.Jude on 2015/8/13.
 */
public class SettingPresenter extends Presenter<SettingActivity> {
    public boolean checkLogin() {
        if (AccountModel.getInstance().getAccount() == null) {
            getView().startActivity(new Intent(getView(), LoginActivity.class));
            return false;
        } else {
            return true;
        }
    }

    public void changePwd() {
        if (checkLogin()) getView().startActivity(new Intent(getView(), PwdChangeActivity.class));
    }

    public void bindPhone() {
        if (checkLogin()) getView().startActivity(new Intent(getView(), BindChangeActivity.class));
    }
}
