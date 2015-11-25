package com.jude.fishing.module.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;
import com.jude.fishing.module.blog.BlogFragment;
import com.jude.fishing.module.place.PlaceFragment;
import com.jude.fishing.module.social.MessageFragment;
import com.jude.fishing.module.user.UserFragment;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends BeamBaseActivity<MainPresenter> implements DrawerFragment.DrawerChangedListener{
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
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        ButterKnife.inject(this);
        mDrawerToggle = new ActionBarDrawerToggle(this
                , drawerLayout
                , (Toolbar) findViewById(R.id.toolbar)
                , 0
                , 0);
        drawerLayout.post(() -> mDrawerToggle.syncState());
        drawerLayout.setDrawerListener(mDrawerToggle);

        init();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        重新建立的时候getActivity为null，暂时完全不保存状态
//        super.onSaveInstanceState(outState);
    }

    private FragmentPagerAdapter pagerAdapter;
    DrawerFragment drawerFragment;
    private void init() {
        drawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawerFragment.setDrawerChangedListener(this);
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (R.id.blog==position){
                    return new BlogFragment();
                }else if (R.id.place==position){
                    return new PlaceFragment();
                }else if (R.id.message==position){
                    return new MessageFragment();
                }else if (R.id.user==position){
                    return new UserFragment();
                }
                return null;
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position){
                    case R.id.blog:return "渔获";
                    case R.id.place:return "钓点";
                    case R.id.message:return "消息";
                    case R.id.user:return "个人中心";
                    default:throw new RuntimeException("页数不存在");
                }
            }
        };
    }

    public void closeDrawer(){
        drawerLayout.closeDrawers();
    }


    private long lastBackPressed;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            long time = System.currentTimeMillis();

            if (time-lastBackPressed>2000){
                JUtils.Toast("再次点击退出APP");
                lastBackPressed = time;
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onChange(View v) {
        Fragment fragment = (Fragment) pagerAdapter.instantiateItem(container, v.getId());
        pagerAdapter.setPrimaryItem(container, 0, fragment);
        pagerAdapter.finishUpdate(container);
        setTitle(pagerAdapter.getPageTitle(v.getId()));
        closeDrawer();
    }
}
