package com.jude.fishing.module.bbs;

import android.os.Bundle;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;

/**
 * Created by Mr.Jude on 2015/9/13.
 */

@RequiresPresenter(WritePresenter.class)
public class WriteActivity extends BeamBaseActivity<WritePresenter>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
