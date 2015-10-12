package com.jude.fishing.module.user;

import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.service.ServiceResponse;

/**
 * Created by heqiang on 2015/9/23.
 */
public class UserData2Presenter extends Presenter<UserData2Activity> {
    String avatar;
    String name;
    int gender;
    String region;

    @Override
    protected void onCreate(UserData2Activity view, Bundle savedState) {
        super.onCreate(view, savedState);
        avatar = getView().getIntent().getStringExtra("avatar");
        name = getView().getIntent().getStringExtra("name");
        region = getView().getIntent().getStringExtra("region");
        gender = getView().getIntent().getBooleanExtra("isMale", true) ? 1 : 0;
    }

    public void send(int age, String skill, String sign) {
        getView().getExpansion().showProgressDialog("请稍候...");
        AccountModel.getInstance().modifyUserData(avatar, name, gender, region, age, skill, sign).subscribe(new ServiceResponse<Object>() {
            @Override
            public void onNext(Object o) {
                getView().finish();
            }

            @Override
            public void onCompleted() {
                getView().getExpansion().dismissProgressDialog();
            }
        });
    }
}
