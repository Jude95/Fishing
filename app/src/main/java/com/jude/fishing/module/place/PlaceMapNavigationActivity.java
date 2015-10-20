package com.jude.fishing.module.place;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;
import com.jude.fishing.model.ImageModel;
import com.jude.fishing.model.LocationModel;
import com.jude.fishing.model.entities.PlaceDetail;
import com.jude.fishing.utils.DistanceFormat;
import com.jude.fishing.widget.ScoreView;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.utils.JUtils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by zhuchenxi on 15/10/16.
 */
@RequiresPresenter(PlaceMapNavigationPresenter.class)
public class PlaceMapNavigationActivity extends BeamBaseActivity<PlaceMapNavigationPresenter> implements AMapNaviViewListener, AMapNaviListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.map)
    MapView mMapView;
    @InjectView(R.id.drive)
    RadioButton drive;
    @InjectView(R.id.walk)
    RadioButton walk;
    @InjectView(R.id.info)
    TextView info;
    @InjectView(R.id.ok)
    Button ok;

    private PlaceDetail mPlaceDetail;
    private AMap mAMap;
    private AMapNavi mAMapNavi;
    private Marker mMyLocation;
    private Marker mPlaceLocation;

    // 规划线路
    private RouteOverLay mRouteOverLay;
    // 起点终点坐标
    private NaviLatLng mNaviStart;
    private NaviLatLng mNaviEnd;
    // 起点终点列表
    private ArrayList<NaviLatLng> mStartPoints = new ArrayList<NaviLatLng>();
    private ArrayList<NaviLatLng> mEndPoints = new ArrayList<NaviLatLng>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_activity_navigation);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        ButterKnife.inject(this);
        mMapView.onCreate(savedInstanceState);
        mPlaceDetail = (PlaceDetail) getIntent().getSerializableExtra("place");
        initAMapView();
        calculateDriveRoute();
        drive.setOnClickListener(v -> calculateDriveRoute());
        walk.setOnClickListener(v->calculateFootRoute());
    }

    private void initAMapView() {

        mNaviStart = new NaviLatLng(LocationModel.getInstance().getCurLocation().getLatitude(), LocationModel.getInstance().getCurLocation().getLongitude());
        mNaviEnd = new NaviLatLng(mPlaceDetail.getLat(), mPlaceDetail.getLng());


        mStartPoints.add(mNaviStart);
        mEndPoints.add(mNaviEnd);

        mAMap = mMapView.getMap();
        mRouteOverLay = new RouteOverLay(mAMap, null);
        initMyPoint();
        initPlacePoint();

        UiSettings mUiSettings = mAMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setMyLocationButtonEnabled(false);

        mAMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View infoContent = getLayoutInflater().inflate(
                        R.layout.place_window_info, null);
                ((SimpleDraweeView) (infoContent.findViewById(R.id.preview))).setImageURI(ImageModel.getInstance().getSmallImage(mPlaceDetail.getPreview()));
                ((TextView) (infoContent.findViewById(R.id.name))).setText(mPlaceDetail.getName());
                ((ScoreView) (infoContent.findViewById(R.id.score_image))).setScore(mPlaceDetail.getScore());
                ((TextView) (infoContent.findViewById(R.id.score))).setText(mPlaceDetail.getScore() + "");
                ((TextView) (infoContent.findViewById(R.id.distance))).setText(DistanceFormat.parse(LocationModel.getInstance().getDistance(mPlaceDetail.getLat(), mPlaceDetail.getLng())) + "/" + mPlaceDetail.getCost() + "¥");
                return infoContent;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });

        zoomMarker();

        mAMapNavi = AMapNavi.getInstance(this);
        if (mAMapNavi == null) return;
        mAMapNavi.setAMapNaviListener(this);
    }

    //计算驾车路线
    private void calculateDriveRoute() {
        boolean isSuccess = mAMapNavi.calculateDriveRoute(mStartPoints,
                mEndPoints, null, AMapNavi.DrivingDefault);
        if (!isSuccess) {
            JUtils.Toast("路线计算失败");
        }
    }

    //计算步行路线
    private void calculateFootRoute() {
        boolean isSuccess = mAMapNavi.calculateWalkRoute(mNaviStart, mNaviEnd);
        if (!isSuccess) {
            JUtils.Log("calculateFootRoute 路线计算失败");
        }

    }


    private void initMyPoint() {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.icon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker));
        markerOption.title("我的位置")
        ;
        mMyLocation = mAMap.addMarker(markerOption);
        LocationModel.getInstance().registerLocationChange(location -> {
            mMyLocation.setPosition(location.toLatLng());
            mMyLocation.setSnippet(location.getAddress());
        });
    }

    public void initPlacePoint() {
        MarkerOptions markerOption = new MarkerOptions();
        JUtils.Log("Place" + mPlaceDetail.getLat() + ":" + mPlaceDetail.getLng());
        markerOption.position(new LatLng(mPlaceDetail.getLat(), mPlaceDetail.getLng()));
        markerOption.title(mPlaceDetail.getName()).snippet(mPlaceDetail.getAddressBrief());
        markerOption.icon(BitmapDescriptorFactory
                .fromResource(mPlaceDetail.getCostType() == 0 ? R.drawable.location_point_green : R.drawable.location_point_red));
        mPlaceLocation = mAMap.addMarker(markerOption);
    }

    private void zoomMarker() {
        mMapView.post(() -> {
            LatLngBounds.Builder boundsBuild = new LatLngBounds.Builder();
            boundsBuild.include(mMyLocation.getPosition());
            boundsBuild.include(mPlaceLocation.getPosition());
            mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuild.build(), 10));
            mAMap.moveCamera(CameraUpdateFactory.zoomOut());
        });
    }

    @Override
    public void onNaviSetting() {

    }

    @Override
    public void onNaviCancel() {

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
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        if (mAMapNavi != null) mAMapNavi.stopNavi();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        //删除监听
        if (mAMapNavi != null) mAMapNavi.removeAMapNaviListener(this);

    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onInitNaviSuccess() {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onGetNavigationText(int i, String s) {

    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onArriveDestination() {

    }

    @Override
    public void onCalculateRouteSuccess() {
        AMapNaviPath naviPath = mAMapNavi.getNaviPath();
        if (naviPath == null) {
            return;
        }
        String timeStr = "";
        if (naviPath.getAllTime()/3600>0){
            timeStr+=naviPath.getAllTime()/3600+"小时";
        }
        timeStr+=(naviPath.getAllTime()%3600)/60+"分钟";
        info.setText("全长" + DistanceFormat.parse(naviPath.getAllLength()) + "/" + timeStr);
        // 获取路径规划线路，显示到地图上
        mRouteOverLay.setRouteInfo(naviPath);
        mRouteOverLay.addToMap();
        zoomMarker();
    }

    @Override
    public void onCalculateRouteFailure(int i) {
        JUtils.Log("onCalculateRouteFailure 路线计算失败:" + i);
    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onGpsOpenStatus(boolean b) {

    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {

    }
}
