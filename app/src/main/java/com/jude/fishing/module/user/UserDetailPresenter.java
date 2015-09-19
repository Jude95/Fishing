package com.jude.fishing.module.user;

import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.bean.PersonDetail;
import com.jude.utils.JUtils;

import rx.functions.Action1;

/**
 * Created by Mr.Jude on 2015/9/18.
 */
public class UserDetailPresenter extends BeamDataActivityPresenter<UserDetailActivity,PersonDetail> {

    @Override
    protected void onCreate(UserDetailActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        AccountModel.getInstance().registerAccountUpdate(this);
        getDataSubJect().subscribe(new Action1<PersonDetail>() {
            @Override
            public void call(PersonDetail personDetail) {
                JUtils.Log("T");
            }
        });
    }
}
