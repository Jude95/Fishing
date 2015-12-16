package com.jude.fishing.module.main;

import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.entities.Account;
import com.jude.fishing.model.service.ServiceResponse;

/**
 * Created by zhuchenxi on 15/10/13.
 */
public class LaunchPresenter extends Presenter<LaunchActivity> {

    @Override
    protected void onCreate(LaunchActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        AccountModel.getInstance().updateMyInfo().subscribe(new ServiceResponse<Account>());
    }
}
