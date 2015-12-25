package com.jude.fishing.module.blog;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.fishing.R;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.module.main.MainActivity;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscription;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
@RequiresPresenter(BlogPresenter.class)
public class BlogFragment extends BeamFragment<BlogPresenter> {

    @InjectView(R.id.tabs)
    TabLayout tabs;
    @InjectView(R.id.vp_date)
    ViewPager vpDate;
    @InjectView(R.id.write)
    FloatingActionButton write;
    FragmentPagerAdapter pagerAdapter;
    Subscription subscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.blog_fragment_main, container, false);
        ButterKnife.inject(this, rootView);
        subscription = AccountModel.getInstance().registerAccountUpdate(account -> {
            if (account!=null){
                pagerAdapter = new BlogFragmentListAdapter(getChildFragmentManager());
            }else {
                pagerAdapter = new BlogFragmentListAdapterLogOut(getChildFragmentManager());
            }
            vpDate.setAdapter(pagerAdapter);
            tabs.setupWithViewPager(vpDate);
        });
        vpDate.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0 && isFirst && openDrawer && positionOffsetPixels == 0) {
                    isFirst = false;
                    JUtils.Log("pos:" + positionOffset + " " + positionOffsetPixels);
                    ((MainActivity) getActivity()).openDrawer();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                JUtils.Log("state:" + state);
                if (1 == state) {
                    openDrawer = true;
                    isFirst = true;
                } else openDrawer = false;
            }
        });
        tabs.setTabTextColors(getResources().getColor(R.color.gray_light), getResources().getColor(R.color.white));
        write.setOnClickListener(v -> getPresenter().write());
        return rootView;
    }
    boolean openDrawer = false,isFirst = true;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        subscription.unsubscribe();
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
//            if (getPresenter().checkLogin())
                return 3;
//            else
//                return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:return "渔获广场";
                case 1:return "附近渔获";
                case 2:return "朋友圈";
                default:throw new RuntimeException("页数不存在");
            }
        }
    }

    class BlogFragmentListAdapterLogOut extends FragmentPagerAdapter {

        public BlogFragmentListAdapterLogOut(FragmentManager fm) {
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
                return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:return "渔获广场";
                case 1:return "附近渔获";
                default:throw new RuntimeException("页数不存在");
            }
        }
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null)
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
    }
}
