package com.jude.fishing.module.main;

import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.fishing.model.CommonModel;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
public class MainPresenter extends Presenter<MainActivity> {

    @Override
    protected void onCreate(MainActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        CommonModel.getInstance().checkUpdate(getView());
    }
}
