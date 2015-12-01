package com.jude.fishing.module.user;

import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataFragmentPresenter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.entities.Account;

import rx.Subscription;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
public class UserPresenter extends BeamDataFragmentPresenter<UserFragment,Account> {
    private Subscription notificationCount;

    @Override
    protected void onCreate(UserFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        AccountModel.getInstance().registerAccountUpdate(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        AccountModel.getInstance().updateNotificationCount();
    }

    @Override
    protected void onCreateView(UserFragment view) {
        super.onCreateView(view);
        notificationCount = AccountModel.getInstance().registerNotificationCountUpdate(integer -> getView().setNotificationCount(integer));
    }


    @Override
    protected void onDestroyView() {
        super.onDestroyView();
        notificationCount.unsubscribe();
    }

}
