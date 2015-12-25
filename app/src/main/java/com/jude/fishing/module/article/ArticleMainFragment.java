package com.jude.fishing.module.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.fishing.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhuchenxi on 15/12/24.
 */
@RequiresPresenter(ArticleMainPresenter.class)
public class ArticleMainFragment extends BeamFragment<ArticleMainPresenter> {

    @InjectView(R.id.tabs)
    TabLayout tabs;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;

    FragmentPagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.article_fragment_main,container,false);
        ButterKnife.inject(this, view);
        viewpager.setAdapter(pagerAdapter = new ArticleFragmentListAdapter(getChildFragmentManager()));
        tabs.setupWithViewPager(viewpager);
        //设置当前是选中第几项
        tabs.setTabTextColors(getResources().getColor(R.color.gray_light), getResources().getColor(R.color.white));
        return view;
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
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:return "资讯";
                case 1:return "技术";
                default:throw new RuntimeException("页数不存在");
            }
        }
    }
}
