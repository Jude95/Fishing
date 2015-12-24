package com.jude.fishing.module.article;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;
import com.jude.swipbackhelper.SwipeBackHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhuchenxi on 15/12/24.
 */
@RequiresPresenter(ArticleMainPresenter.class)
public class ArticleMainFragment extends BeamBaseActivity<ArticleMainPresenter> {

    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.tabs)
    TabLayout tabs;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;

    FragmentPagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_fragment_main);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        ButterKnife.inject(this);
        viewpager.setAdapter(pagerAdapter = new ArticleFragmentListAdapter(getSupportFragmentManager()));
        tabs.setupWithViewPager(viewpager);
        title.setText(pagerAdapter.getPageTitle(getIntent().getIntExtra("type",0)));

        //设置当前是选中第几项
        viewpager.setCurrentItem(getIntent().getIntExtra("type",0));
        tabs.setTabTextColors(getResources().getColor(R.color.gray),getResources().getColor(R.color.orange));
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                title.setText(tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    class ArticleFragmentListAdapter extends FragmentPagerAdapter {

        public ArticleFragmentListAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f =  new ArticleListFragment();
            Bundle b = new Bundle();
            b.putInt("type",position);
            f.setArguments(b);
            return f;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:return "精选秀";
                case 1:return "好友秀";
                case 2:return "附近秀";
                case 3:return "最新秀";
                default:throw new RuntimeException("页数不存在");
            }
        }
    }
}
