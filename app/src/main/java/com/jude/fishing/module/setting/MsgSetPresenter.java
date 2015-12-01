package com.jude.fishing.module.setting;


import com.jude.beam.bijection.Presenter;
import com.jude.utils.JUtils;

/**
 * Created by Mr.Jude on 2015/8/13.
 */
public class MsgSetPresenter extends Presenter<MsgSetActivity> {
    public void setPrefer(String key, boolean chatNotify) {
        JUtils.getSharedPreference().edit().putBoolean(key, chatNotify).apply();
    }

    public boolean getPrefer(String key) {
        return JUtils.getSharedPreference().getBoolean(key, true);
    }
}
