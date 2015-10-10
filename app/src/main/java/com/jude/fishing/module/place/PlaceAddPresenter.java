package com.jude.fishing.module.place;

import android.app.Activity;
import android.content.Intent;

import com.amap.api.maps.model.LatLng;
import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.fishing.model.entities.PlaceDetail;

/**
 * Created by Mr.Jude on 2015/9/22.
 */
public class PlaceAddPresenter extends BeamDataActivityPresenter<PlaceAddActivity,PlaceDetail> {
    public static final int PLACE = 1001;
    private PlaceDetail mPlaceDetail = new PlaceDetail();

    @Override
    protected void onCreateView(PlaceAddActivity view) {
        super.onCreateView(view);
        publishObject(mPlaceDetail);
    }

    public PlaceDetail getPlaceDetail() {
        return mPlaceDetail;
    }

    public void startPlaceSelect(){
        getView().startActivityForResult(new Intent(getView(),PlaceSelectActivity.class),PLACE);
    }

    public void setName(String name){
        mPlaceDetail.setName(name);
        publishObject(mPlaceDetail);
    }

    public void setCostType(int type){
        mPlaceDetail.setCostType(type);
        publishObject(mPlaceDetail);
    }

    public void setCostAvg(int costAvg){
        mPlaceDetail.setCost(costAvg);
        publishObject(mPlaceDetail);
    }

    public void setFishType(String fishType) {
        mPlaceDetail.setFishType(fishType);
        publishObject(mPlaceDetail);
    }

    public void setPoolType(int poolType) {
        mPlaceDetail.setPoolType(poolType);
        publishObject(mPlaceDetail);
    }

    public void setServerType(Integer[] types){
        String typeStr = "";
        for (Integer type : types) {
            typeStr+=type+",";
        }
        if (types.length>1){
            typeStr = typeStr.substring(0, typeStr.length() - 1);
        }
        mPlaceDetail.setServiceType(typeStr);
        publishObject(mPlaceDetail);
    }

    public void setContact(String contact) {
        mPlaceDetail.setTel(contact);
        publishObject(mPlaceDetail);
    }

    public void setContent(String content){
        mPlaceDetail.setContent(content);
        publishObject(mPlaceDetail);
    }


    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE&&resultCode == Activity.RESULT_OK){
            LatLng point = data.getParcelableExtra("point");
            if (point!=null){
                mPlaceDetail.setLat(point.latitude);
                mPlaceDetail.setLng(point.longitude);
            }
            mPlaceDetail.setAddress(data.getStringExtra("address"));
            publishObject(mPlaceDetail);
        }
    }

    public void submit(){

    }

}
