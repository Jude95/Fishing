package com.jude.fishing.module.place;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;
import com.jude.fishing.model.LocationModel;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/10/10.
 */
@RequiresPresenter(PlaceSelectPresenter.class)
public class PlaceSelectActivity extends BeamBaseActivity<PlaceSelectPresenter> implements AMap.OnMapClickListener, GeocodeSearch.OnGeocodeSearchListener {
    @InjectView(R.id.map)
    MapView mMapView;
    @InjectView(R.id.address)
    TextView address;
    @InjectView(R.id.ok)
    Button ok;
    @InjectView(R.id.info)
    CardView info;

    private AMap aMap;
    private UiSettings mUiSettings;
    private GeocodeSearch mGeocoderSearch;
    private LatLng mPoint;
    private String mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_activity_select);
        ButterKnife.inject(this);
        mMapView.onCreate(savedInstanceState);
        initMap();
        ok.setOnClickListener(v->{
            Intent i = new Intent();
            i.putExtra("point",mPoint);
            i.putExtra("address",mAddress);
            setResult(Activity.RESULT_OK, i);
            finish();
        });
    }

    private void initMap() {
        aMap = mMapView.getMap();
        mUiSettings = aMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setScaleControlsEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(false);
        moveTo(LocationModel.getInstance().getCurLocation().getLatitude(), LocationModel.getInstance().getCurLocation().getLongitude(), 13);
        Marker myLocation = initMyPoint();
        LocationModel.getInstance().registerLocationChange(location -> {
            myLocation.setPosition(location.toLatLng());
        });
        aMap.setOnMapClickListener(this);
        mGeocoderSearch = new GeocodeSearch(this);
        mGeocoderSearch.setOnGeocodeSearchListener(this);
    }

    private Marker initMyPoint() {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.icon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker));
        return aMap.addMarker(markerOption);
    }

    private void moveTo(double lat, double lng, float zoom) {
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(lat, lng), zoom, 0, 0
        )));
    }

    private Marker mLastMarker;

    @Override
    public void onMapClick(LatLng latLng) {
        if (mLastMarker != null) mLastMarker.destroy();
        mPoint = latLng;
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.icon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_point_bigger_red));
        mLastMarker = aMap.addMarker(markerOption);
        mGeocoderSearch.getFromLocationAsyn(new RegeocodeQuery(new LatLonPoint(latLng.latitude,latLng.longitude), 50,GeocodeSearch.AMAP));
    }


    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        address.setText(regeocodeResult.getRegeocodeAddress().getFormatAddress());
        mAddress = regeocodeResult.getRegeocodeAddress().getFormatAddress();
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
