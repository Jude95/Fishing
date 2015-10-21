package com.jude.fishing.module.place;

import android.os.Bundle;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.AMapNaviViewOptions;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;
import com.jude.fishing.utils.TTSController;
import com.jude.swipbackhelper.SwipeBackHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by Mr.Jude on 2015/10/20.
 */
@RequiresPresenter(PlaceNavigationPresenter.class)
public class PlaceNavigationActivity extends BeamBaseActivity<PlaceNavigationPresenter> implements AMapNaviViewListener {

    @InjectView(R.id.navigation)
    AMapNaviView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_activity_navigation);
        ButterKnife.inject(this);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        TTSController.getInstance(this).startSpeaking();

        navigation.onCreate(savedInstanceState);
        navigation.setAMapNaviViewListener(this);
        setAmapNaviViewOptions();
        AMapNavi.getInstance(this).startNavi(AMapNavi.GPSNaviMode);

    }

    private void setAmapNaviViewOptions() {
        if (navigation == null) {
            return;
        }

        AMapNaviViewOptions viewOptions = new AMapNaviViewOptions();
        viewOptions.setSettingMenuEnabled(false);//设置菜单按钮是否在导航界面显示
        viewOptions.setNaviNight(false);//设置导航界面是否显示黑夜模式
        viewOptions.setReCalculateRouteForYaw(true);//设置偏航时是否重新计算路径
        viewOptions.setReCalculateRouteForTrafficJam(true);//前方拥堵时是否重新计算路径
        viewOptions.setTrafficInfoUpdateEnabled(true);//设置交通播报是否打开
        viewOptions.setCameraInfoUpdateEnabled(true);//设置摄像头播报是否打开
        viewOptions.setScreenAlwaysBright(true);//设置导航状态下屏幕是否一直开启。
        viewOptions.setNaviViewTopic(AMapNaviViewOptions.BLUE_COLOR_TOPIC);//设置导航界面的主题
        navigation.setViewOptions(viewOptions);
    }

    @Override
    public void onNaviSetting() {

    }

    @Override
    public void onNaviCancel() {
        finish();
    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }

    @Override
    public void onNaviMapMode(int i) {

    }

    @Override
    public void onNaviTurnClick() {

    }

    @Override
    public void onNextRoadClick() {

    }

    @Override
    public void onScanViewButtonClick() {

    }

    @Override
    public void onLockMap(boolean b) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        navigation.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        navigation.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        navigation.onPause();
        AMapNavi.getInstance(this).stopNavi();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        navigation.onDestroy();
    }
}
