package com.jude.fishing.module.place;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.jude.fishing.model.ImageModel;
import com.jude.fishing.model.LocationModel;
import com.jude.fishing.model.entities.PlaceBrief;
import com.jude.fishing.utils.DistanceFormat;
import com.jude.fishing.widget.ScoreView;
import com.jude.utils.JUtils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/9/28.
 */
@RequiresPresenter(PlaceMapPresenter.class)
public class PlaceMapFragment extends BeamFragment<PlaceMapPresenter> implements AMap.OnMarkerClickListener, AMap.OnMapClickListener, AMap.OnInfoWindowClickListener {

    @InjectView(R.id.map)
    MapView mMapView;
    @InjectView(R.id.type)
    ImageView type;
    @InjectView(R.id.location)
    ImageView location;
    @InjectView(R.id.zoom_in)
    ImageView zoomIn;
    @InjectView(R.id.zoom_out)
    ImageView zoomOut;
    private AMap aMap;
    private UiSettings mUiSettings;
    private HashMap<Marker, PlaceBrief> mMarkerMap;

    private Marker mMyLocation;
    private int mStatus = 0;

    private static final int MIN_ZOOM_MARKER_COUNT = 2;
    private static final int MIN_ZOOM= 13;

    private final static int[] ZOOM_LEVEL = {
            500000, 500000, 500000, 500000, 500000, 200000, 100000, 50000, 30000, 20000, 10000, 5000, 2000, 1000, 500, 200, 100, 50, 25, 10
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.place_fragment_map, container, false);
        ButterKnife.inject(this,view);
        mMapView.onCreate(savedInstanceState);
        zoomIn.setOnClickListener(v -> {
            aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                    aMap.getCameraPosition().target, aMap.getCameraPosition().zoom + 1, 0, 0
            )));
        });
        zoomOut.setOnClickListener(v->{
            aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                    aMap.getCameraPosition().target, aMap.getCameraPosition().zoom - 1, 0, 0
            )));
        });
        location.setOnClickListener(v->{
            moveTo(mMyLocation.getPosition().latitude,mMyLocation.getPosition().longitude);
        });
        type.setOnClickListener(v->{
            if (mStatus==0){
                aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
                mStatus=1;
                type.setImageResource(R.drawable.ic_map_paper);
            }else{
                aMap.setMapType(AMap.MAP_TYPE_NORMAL);
                mStatus=2;
                type.setImageResource(R.drawable.ic_map_moon);
            }
        });
        initMap();
        return view;
    }


    private void initMap() {
        aMap = mMapView.getMap();
        mUiSettings = aMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setScaleControlsEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(false);
        moveTo(LocationModel.getInstance().getCurLocation().getLatitude(), LocationModel.getInstance().getCurLocation().getLongitude(), 13);
        mMyLocation = initMyPoint();
        LocationModel.getInstance().registerLocationChange(location -> {
            mMyLocation.setPosition(location.toLatLng());
            mMyLocation.setSnippet(location.getAddress());
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
                ((SimpleDraweeView) (infoContent.findViewById(R.id.preview))).setImageURI(ImageModel.getInstance().getSmallImage(placeBrief.getPreview()));

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

    private void moveToAdjustPlace(ArrayList<PlaceBrief> place) {
        double maxDistance = 0;
        double myLat = LocationModel.getInstance().getCurLocation().getLatitude();
        double myLng = LocationModel.getInstance().getCurLocation().getLongitude();
        for (PlaceBrief placeBrief : place) {
            double distance = JUtils.distance(myLat, myLng, placeBrief.getLat(), placeBrief.getLng());
            if (distance > maxDistance) {
                maxDistance = distance;
            }
        }
        int unit = (int) (maxDistance / 8);
        for (int i = ZOOM_LEVEL.length - 1; i >= 1; i--) {
            if (unit > ZOOM_LEVEL[i] && unit < ZOOM_LEVEL[i-1]) {
                moveTo(LocationModel.getInstance().getCurLocation().getLatitude(), LocationModel.getInstance().getCurLocation().getLongitude(), Math.max(i,MIN_ZOOM));
                return;
            }
        }
    }

    private Marker initMyPoint() {
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
        if (zoomMarkerList.size() < MIN_ZOOM_MARKER_COUNT) zoomMarkerList.add(place);
        if (zoomMarkerList.size() == MIN_ZOOM_MARKER_COUNT) moveToAdjustPlace(zoomMarkerList);
    }


    /**
     *  会报“the map must have a size”错误，why
     *
     * @param place
     */
    private void zoomMarker(ArrayList<PlaceBrief> place) {
        LatLngBounds.Builder boundsBuild = new LatLngBounds.Builder();
        for (PlaceBrief placeBrief : place) {
            boundsBuild.include(new LatLng(placeBrief.getLat(), placeBrief.getLng()));
        }
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuild.build(), 10));
    }


    private void moveTo(double lat, double lng, float zoom) {
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
        if (lastMarker != null) lastMarker.setIcon(BitmapDescriptorFactory
                .fromResource(mMarkerMap.get(marker).getCostType() == 0 ? R.drawable.location_point_green : R.drawable.location_point_red));
        moveTo(marker.getPosition().latitude, marker.getPosition().longitude);
        marker.setIcon(BitmapDescriptorFactory
                .fromResource(mMarkerMap.get(marker).getCostType() == 0 ? R.drawable.location_point_bigger_green : R.drawable.location_point_bigger_red));
        lastMarker = marker;
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (lastMarker != null) lastMarker.hideInfoWindow();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent i = new Intent(getContext(), PlaceDetailActivity.class);
        i.putExtra("id", mMarkerMap.get(marker).getId());
        getContext().startActivity(i);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
