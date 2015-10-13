package com.jude.fishing.module.main;

import android.content.Intent;
import android.os.Bundle;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;

/**
 * Created by zhuchenxi on 15/10/13.
 */
@RequiresPresenter(LaunchPresenter.class)
public class LaunchActivity extends BeamBaseActivity<LaunchPresenter>{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
