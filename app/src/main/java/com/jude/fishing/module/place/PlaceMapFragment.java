package com.jude.fishing.module.place;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.fishing.R;
import com.jude.fishing.model.LocationModel;
import com.jude.fishing.model.entities.PlaceBrief;
import com.jude.fishing.utils.DistanceFormat;
import com.jude.fishing.widget.ScoreView;
import com.jude.utils.JUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mr.Jude on 2015/9/28.
 */
@RequiresPresenter(PlaceMapPresenter.class)
public class PlaceMapFragment extends BeamFragment<PlaceMapPresenter> implements AMap.OnMarkerClickListener, AMap.OnMapClickListener, AMap.OnInfoWindowClickListener {
    private MapView mMapView;
    private AMap aMap;
    private UiSettings mUiSettings;
    private HashMap<Marker,PlaceBrief> mMarkerMap;

    private static final int MIN_ZOOM_MARKER_COUNT = 5;

    private final static int[] ZOOM_LEVEL={
            500000,500000,500000,500000,500000,200000,100000,50000,30000,20000,10000,5000,2000,1000,500,200,100,50,25,10
    };

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
        mUiSettings.setScaleControlsEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(false);
        moveTo(LocationModel.getInstance().getCurLocation().getLatitude(), LocationModel.getInstance().getCurLocation().getLongitude(), 13);
        Marker myLocation = initMyPoint();
        LocationModel.getInstance().registerLocationChange(location -> {
            myLocation.setPosition(location.toLatLng());
            myLocation.setSnippet(location.getAddress());
        });
        aMap.setOnInfoWindowClickListener(this);
        aMap.setOnMarkerClickListener(this);
        aMap.setOnMapClickListener(this);
        aMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                PlaceBrief placeBrief = mMarkerMap.get(marker);
                if (placeBrief == null) return null;
                View infoContent = getActivity().getLayoutInflater().inflate(
                        R.layout.place_window_info, null);
                ((SimpleDraweeView) (infoContent.findViewById(R.id.preview))).setImageURI(Uri.parse(placeBrief.getPreview()));

                ((TextView) (infoContent.findViewById(R.id.name))).setText(placeBrief.getName());
                ((ScoreView) (infoContent.findViewById(R.id.score_image))).setScore(placeBrief.getScore());
                ((TextView) (infoContent.findViewById(R.id.score))).setText(placeBrief.getScore() + "");
                ((TextView) (infoContent.findViewById(R.id.distance))).setText(DistanceFormat.parse(LocationModel.getInstance().getDistance(placeBrief.getLat(), placeBrief.getLng())) + "/" + placeBrief.getCost() + "¥");
                return infoContent;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });
        mMarkerMap = new HashMap<>();
    }

    private void moveToAdjustPlace(PlaceBrief[] data){
        double maxDistance = 0;
        double myLat = LocationModel.getInstance().getCurLocation().getLatitude();
        double myLng = LocationModel.getInstance().getCurLocation().getLongitude();
        for (PlaceBrief placeBrief : data) {
            double distance = JUtils.distance(myLat,myLng,placeBrief.getLat(),placeBrief.getLng());
            if (distance>maxDistance){
                maxDistance = distance;
            }
        }
        int unit = (int) (maxDistance/8);
        for (int i = ZOOM_LEVEL.length - 2; i >= 0; i--) {
            if (unit>ZOOM_LEVEL[i+1]&&unit<ZOOM_LEVEL[i]){
                moveTo(LocationModel.getInstance().getCurLocation().getLatitude(), LocationModel.getInstance().getCurLocation().getLongitude(), i);
                return;
            }
        }
    }

    private Marker initMyPoint(){
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.icon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker));
        markerOption.title("我的位置");
        return aMap.addMarker(markerOption);
    }

    ArrayList<PlaceBrief> zoomMarkerList = new ArrayList<>();
    public void addMarker(PlaceBrief place) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(new LatLng(place.getLat(), place.getLng()));
        markerOption.title(place.getName()).snippet(place.getAddress());
        markerOption.icon(BitmapDescriptorFactory
                .fromResource(place.getCostType() == 0 ? R.drawable.location_point_green : R.drawable.location_point_red));
        Marker marker = aMap.addMarker(markerOption);
        mMarkerMap.put(marker, place);
        if (zoomMarkerList.size()<MIN_ZOOM_MARKER_COUNT) zoomMarkerList.add(place);
        if (zoomMarkerList.size()==MIN_ZOOM_MARKER_COUNT) zoomMarker(zoomMarkerList);
    }

    private void zoomMarker(ArrayList<PlaceBrief> place){
        LatLngBounds.Builder boundsBuild = new LatLngBounds.Builder();
        for (PlaceBrief placeBrief : place) {
            boundsBuild.include(new LatLng(placeBrief.getLat(),placeBrief.getLng()));
        }
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuild.build(), 10));
    }


    private void moveTo(double lat, double lng,float zoom) {
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(lat, lng), zoom, 0, 0
        )));
    }
    private void moveTo(double lat, double lng) {
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(lat, lng), aMap.getCameraPosition().zoom, 0, 0
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

    Marker lastMarker;
    @Override
    public boolean onMarkerClick(Marker marker) {
        if (lastMarker!=null)lastMarker.setIcon(BitmapDescriptorFactory
                .fromResource(mMarkerMap.get(marker).getCostType() == 0 ? R.drawable.location_point_green : R.drawable.location_point_red));
        moveTo(marker.getPosition().latitude, marker.getPosition().longitude);
        marker.setIcon(BitmapDescriptorFactory
                .fromResource(mMarkerMap.get(marker).getCostType() == 0 ? R.drawable.location_point_bigger_green : R.drawable.location_point_bigger_red));
        lastMarker = marker;
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (lastMarker!=null)lastMarker.hideInfoWindow();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent i = new Intent(getContext(), PlaceDetailActivity.class);
        i.putExtra("id", mMarkerMap.get(marker).getId());
        getContext().startActivity(i);
    }
}
