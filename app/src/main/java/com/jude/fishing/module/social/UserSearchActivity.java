package com.jude.fishing.module.social;

import android.os.Bundle;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;

/**
 * Created by Mr.Jude on 2015/9/23.
 */
@RequiresPresenter(UserSearchPresenter.class)
public class UserSearchActivity extends BeamBaseActivity<UserSearchPresenter>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
