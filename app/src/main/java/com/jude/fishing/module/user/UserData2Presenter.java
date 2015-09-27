package com.jude.fishing.module.user;

import android.os.Bundle;

import com.jude.beam.bijection.Presenter;

/**
 * Created by heqiang on 2015/9/23.
 */
public class UserData2Presenter extends Presenter<UserData2Activity> {

    @Override
    protected void onCreate(UserData2Activity view, Bundle savedState) {
        super.onCreate(view, savedState);
        getView().getIntent().getStringExtra("name");
        getView().getIntent().getStringExtra("region");
        getView().getIntent().getBooleanExtra("isMale", true);
    }
}
