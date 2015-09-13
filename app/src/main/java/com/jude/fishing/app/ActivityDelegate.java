package com.jude.fishing.app;

import android.os.Bundle;

import com.jude.beam.bijection.ActivityLifeCycleDelegate;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.utils.JActivityManager;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Mr.Jude on 2015/9/9.
 */
public class ActivityDelegate extends ActivityLifeCycleDelegate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(getActivity());
        JActivityManager.getInstance().pushActivity(getActivity());
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());

    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(getActivity());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(getActivity());
    }
}
