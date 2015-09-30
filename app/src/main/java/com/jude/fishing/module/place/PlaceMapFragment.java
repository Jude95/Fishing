package com.jude.fishing.module.place;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.fishing.R;
import com.jude.fishing.model.LocationModel;
import com.jude.utils.JUtils;

import rx.Subscription;

/**
 * Created by Mr.Jude on 2015/9/28.
 */
@RequiresPresenter(PlaceMapPresenter.class)
public class PlaceMapFragment extends BeamFragment<PlaceMapPresenter> {
    private MapView mMapView;
    private AMap aMap;
    private UiSettings mUiSettings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMapView = new MapView(getContext());
        mMapView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mMapView.onCreate(savedInstanceState);
        initMap();
        return mMapView;
    }


    private void initMap(){
        aMap = mMapView.getMap();
        mUiSettings = aMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setMyLocationButtonEnabled(true);
        moveTo(LocationModel.getInstance().getCurLocation().getLatitude(), LocationModel.getInstance().getCurLocation().getLongitude(),16);
    }


    private void addMarker(double lat, double lng){

    }


    private void moveTo(double lat, double lng,float zoom) {
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(lat, lng), zoom, 0, 0
        )));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
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
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }


}
