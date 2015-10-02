package com.jude.fishing.module.user;

import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataFragmentPresenter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.entities.Account;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
public class UserPresenter extends BeamDataFragmentPresenter<UserFragment,Account> {
    @Override
    protected void onCreate(UserFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        AccountModel.getInstance().registerAccountUpdate(this);
    }
}
