package com.jude.fishing.model;

import com.jude.beam.model.AbsModel;
import com.jude.fishing.model.bean.Account;

import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
public class AccountModel extends AbsModel {
    public static AccountModel getInstance() {
        return getInstance(AccountModel.class);
    }

    public Account userAccountData = null;
    public BehaviorSubject<Account> userAccountDataBehaviorSubject = BehaviorSubject.create();

    public Account getAccount(){
        return userAccountData;
    }

    public Subscription registerAccountUpdate(Action1<Account> accountAction1){
        return userAccountDataBehaviorSubject.subscribe(accountAction1);
    }
}
