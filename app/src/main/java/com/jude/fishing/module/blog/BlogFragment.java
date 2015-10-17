package com.jude.fishing.module.blog;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.github.clans.fab.FloatingActionButton;
import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.fishing.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
@RequiresPresenter(BlogPresenter.class)
public class BlogFragment extends BeamFragment<BlogPresenter> {

    @InjectView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @InjectView(R.id.vp_date)
    ViewPager vpDate;
    @InjectView(R.id.write)
    FloatingActionButton write;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("热闹");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.blog_fragment_main, container, false);
        ButterKnife.inject(this, rootView);
        vpDate.setAdapter(new BlogFragmentListAdapter(getChildFragmentManager()));
        tabs.setViewPager(vpDate);
        tabs.setTextColor(Color.WHITE);
        tabs.setBackgroundColor(getResources().getColor(R.color.blue));
        write.setOnClickListener(v->getPresenter().write());
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    class BlogFragmentListAdapter extends FragmentPagerAdapter {

        public BlogFragmentListAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f =  new BlogListFragment();
            Bundle b = new Bundle();
            b.putInt("style",position);
            f.setArguments(b);
            return f;
        }

        @Override
        public int getCount() {
            if (getPresenter().checkLogin())
                return 3;
            else
                return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:return "热闹广场";
                case 1:return "附近热闹";
                case 2:return "朋友圈";
                default:throw new RuntimeException("页数不存在");
            }
        }
    }
}
