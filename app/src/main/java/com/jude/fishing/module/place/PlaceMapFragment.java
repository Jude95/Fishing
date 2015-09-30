package com.jude.fishing.module.place;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataFragment;
import com.jude.fishing.R;
import com.jude.fishing.model.LocationModel;
import com.jude.fishing.model.bean.PlaceBrief;

/**
 * Created by Mr.Jude on 2015/9/28.
 */
@RequiresPresenter(PlaceMapPresenter.class)
public class PlaceMapFragment extends BeamDataFragment<PlaceMapPresenter,PlaceBrief[]> {
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
        mUiSettings.setMyLocationButtonEnabled(false);
        moveTo(LocationModel.getInstance().getCurLocation().getLatitude(), LocationModel.getInstance().getCurLocation().getLongitude(), 16);
        Marker myLocation = initMyPoint();
        LocationModel.getInstance().registerLocationChange(location -> {
            myLocation.setPosition(location.toLatLng());
            myLocation.setSnippet(location.getAddress());
        });
    }

    public Marker initMyPoint(){
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.icon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker));
        markerOption.title("我的位置");
        return aMap.addMarker(markerOption);
    }

    @Override
    public void setData(PlaceBrief[] data) {
        aMap.clear();
        for (PlaceBrief placeBrief : data) {
            addMarker(placeBrief);
        }
    }

    public void addMarker(PlaceBrief place){
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(new LatLng(place.getLat(), place.getLng()));
        markerOption.title(place.getName()).snippet(place.getAddress());
        markerOption.icon(BitmapDescriptorFactory
                .fromResource(place.getCostType()==0?R.drawable.location_point_green:R.drawable.location_point_blue));
        aMap.addMarker(markerOption);
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
