package com.jude.fishing.module.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;
import com.jude.fishing.module.bbs.BBSFragment;
import com.jude.fishing.module.message.MassageFragment;
import com.jude.fishing.module.mine.MineFragment;
import com.jude.fishing.module.place.PlaceFragment;
import com.jude.fishing.module.setting.UpdateLogActivity;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.umeng.update.UmengUpdateAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends BeamBaseActivity<MainPresenter> {

    @InjectView(R.id.avatar)
    SimpleDraweeView avatar;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tab_layout)
    TabLayout tabLayout;
    @InjectView(R.id.viewPager)
    ViewPager viewPager;

    private MainPagerAdapter mMainPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mian_activity_main);
        UmengUpdateAgent.forceUpdate(this);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        ButterKnife.inject(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        tabLayout.setTabTextColors(getResources().getColor(R.color.white_trans80), getResources().getColor(R.color.white));
        viewPager.setAdapter(mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        name.setText("Jude");
        avatar.setImageURI(Uri.parse("http://img5.imgtn.bdimg.com/it/u=34863218,524059131&fm=21&gp=0.jpg"));

    }

    public class MainPagerAdapter extends FragmentStatePagerAdapter {

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PlaceFragment();
                case 1:
                    return new BBSFragment();
                case 2:
                    return new MassageFragment();
                case 3:
                    return new MineFragment();
                default:
                    throw new RuntimeException("NO fragment should provide");
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "钓点";
                case 1:
                    return "热闹";
                case 2:
                    return "消息";
                case 3:
                    return "我的";
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.update) {
            startActivity(new Intent(this, UpdateLogActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
