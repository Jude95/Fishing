package com.jude.fishing.module.main;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;
import com.jude.fishing.module.setting.update.UpdateChecker;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends BeamBaseActivity<MainPresenter> {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.container)
    FrameLayout container;
    @InjectView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_main);
        UpdateChecker.getInstance().checkUpdate(this);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        ButterKnife.inject(this);
        mDrawerToggle = new ActionBarDrawerToggle(this
                , drawerLayout
                , (Toolbar) findViewById(R.id.toolbar)
                , 0
                , 0);
        drawerLayout.post(() -> mDrawerToggle.syncState());
        drawerLayout.setDrawerListener(mDrawerToggle);

    }

    public void closeDrawer(){
        drawerLayout.closeDrawers();
    }


    private long lastBackPressed;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            long time = System.currentTimeMillis();

            if (time-lastBackPressed>1000){
                JUtils.Toast("再次点击退出APP");
                lastBackPressed = time;
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
