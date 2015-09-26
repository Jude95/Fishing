package com.jude.fishing.module.user;

import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.fishing.model.SocialModel;
import com.jude.fishing.model.bean.PersonDetail;

/**
 * Created by Mr.Jude on 2015/9/18.
 */
public class UserDetailPresenter extends BeamDataActivityPresenter<UserDetailActivity,PersonDetail> {
    private int id;

    @Override
    protected void onCreate(UserDetailActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        id = getView().getIntent().getIntExtra("id",0);
//        if (id == 0)AccountModel.getInstance().registerAccountUpdate(this);
//        else SocialModel.getInstance().getUserDetail(id).subscribe(this);
    }

    public void attention(){
        SocialModel.getInstance().attention(id);
    }
    public void unAttention(){
        SocialModel.getInstance().unAttention(id);
    }
}
