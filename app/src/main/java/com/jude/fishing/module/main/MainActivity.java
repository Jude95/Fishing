package com.jude.fishing.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.module.article.ArticleMainFragment;
import com.jude.fishing.module.blog.BlogFragment;
import com.jude.fishing.module.date.DateFragment;
import com.jude.fishing.module.place.PlaceFragment;
import com.jude.fishing.module.social.MessageFragment;
import com.jude.fishing.module.user.LoginActivity;
import com.jude.fishing.module.user.UserDataActivity;
import com.jude.fishing.module.user.UserFragment;
import com.jude.fishing.widget.FragmentManagerAdapter;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends BeamBaseActivity<MainPresenter> implements DrawerFragment.DrawerChangedListener {
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
    protected void onResume() {
        super.onResume();
        //保证没名字的用户进不来
        if (AccountModel.getInstance().getAccount()!=null&& TextUtils.isEmpty(AccountModel.getInstance().getAccount().getName())){
            startActivity(new Intent(this, UserDataActivity.class));
        }
    }

    private FragmentManagerAdapter mFragmentAdapter;
    DrawerFragment drawerFragment;

    private void init() {
        drawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawerFragment.setDrawerChangedListener(this);
        mFragmentAdapter = new MyFragmentManagerAdapter(getSupportFragmentManager());
    }

    public void closeDrawer() {
        drawerLayout.closeDrawers();
    }

    public void openDrawer() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }


    private long lastBackPressed;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long time = System.currentTimeMillis();

            if (time - lastBackPressed > 2000) {
                JUtils.Toast("再次点击退出APP");
                lastBackPressed = time;
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    int lastNavId = 0;

    @Override
    public void onChange(View v) {
        switch (v.getId()){
            case R.id.nav_message:
            case R.id.nav_user:
            case R.id.nav_logout:
                if (!AccountModel.getInstance().isLogin()){
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
        }
        mFragmentAdapter.applyItem(container, v.getId());
        setTitle(mFragmentAdapter.getTitle(v.getId()));
        closeDrawer();
        lastNavId = v.getId();
    }

    private class MyFragmentManagerAdapter extends FragmentManagerAdapter {

        public MyFragmentManagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int itemId) {
            switch (itemId){
                case R.id.nav_blog:
                    return new BlogFragment();
                case R.id.nav_place:
                    return new PlaceFragment();
                case R.id.nav_message:
                    return new MessageFragment();
                case R.id.nav_article:
                    return new ArticleMainFragment();
                case R.id.nav_user:
                    return new UserFragment();
                case R.id.nav_date:
                    return new DateFragment();
            }
            return null;
        }


        @Override
        public String getTitle(int position) {
            switch (position) {
                case R.id.nav_blog:
                    return "渔获";
                case R.id.nav_place:
                    return "钓点";
                case R.id.nav_message:
                    return "消息";
                case R.id.nav_article:
                    return "文章";
                case R.id.nav_user:
                    return "个人中心";
                case R.id.nav_date:
                    return "约钓";
                default:
                    throw new RuntimeException("页数不存在");
            }
        }
    }
}
