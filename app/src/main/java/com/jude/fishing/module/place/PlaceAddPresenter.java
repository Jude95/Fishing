package com.jude.fishing.module.place;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.amap.api.maps.model.LatLng;
import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.fishing.config.Constant;
import com.jude.fishing.model.ImageModel;
import com.jude.fishing.model.PlaceModel;
import com.jude.fishing.model.entities.PlaceDetail;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.utils.JUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Mr.Jude on 2015/9/22.
 */
public class PlaceAddPresenter extends BeamDataActivityPresenter<PlaceAddActivity,PlaceDetail> {
    public static final int PLACE = 1001;
    public static final int PHOTO = 1002;

    private PlaceDetail mPlaceDetail;

    private ArrayList<Uri> mPreUpload = new ArrayList<>();
    private ArrayList<Uri> mHasUpload = new ArrayList<>();

    @Override
    protected void onCreate(PlaceAddActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        mPlaceDetail = (PlaceDetail) getView().getIntent().getSerializableExtra("place");
        if (mPlaceDetail==null)mPlaceDetail = new PlaceDetail();
        dealPicture();
        publishObject(mPlaceDetail);
    }


    public PlaceDetail getPlaceDetail() {
        return mPlaceDetail;
    }

    public void startPlaceSelect(){
        getView().startActivityForResult(new Intent(getView(),PlaceLocationSelectActivity.class),PLACE);
    }

    public void startPhotoSelect(){
        Intent intent = new Intent(getView(), PlacePhotoSelectActivity.class);
        ArrayList<Uri> uris = new ArrayList<>();
        uris.addAll(mPreUpload);
        uris.addAll(mHasUpload);
        intent.putParcelableArrayListExtra("uri",uris);
        getView().startActivityForResult(intent, PHOTO);
    }

    public void setName(String name){
        mPlaceDetail.setName(name);
        publishObject(mPlaceDetail);
    }

    public void setCostType(int type){
        mPlaceDetail.setCostType(type);
        publishObject(mPlaceDetail);
    }

    public void setArea(int area){
        mPlaceDetail.setArea(Constant.AreaType[area]);
        publishObject(mPlaceDetail);
    }
    public void setDeep(int deep){
        mPlaceDetail.setDeep(Constant.DeepType[deep]);
        publishObject(mPlaceDetail);
    }
    public void setNest(int nest){
        mPlaceDetail.setNest(nest);
        publishObject(mPlaceDetail);
    }
    public void setCostAvg(int costAvg){
        mPlaceDetail.setCost(costAvg);
        publishObject(mPlaceDetail);
    }

    public void setFishType(Integer[] fishs) {
        String fishStr = "";
        for (Integer fish : fishs) {
            fishStr+=fish+",";
        }
        if (fishs.length>1){
            fishStr = fishStr.substring(0, fishStr.length() - 1);
        }
        mPlaceDetail.setFishType(fishStr);
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
            mPlaceDetail.setAddressBrief(data.getStringExtra("briefAddress"));
            mPlaceDetail.setAddress(data.getStringExtra("address"));
            publishObject(mPlaceDetail);
            return;
        }
        if (requestCode == PHOTO&&resultCode == Activity.RESULT_OK){
            mHasUpload.clear();
            mPreUpload.clear();
            ArrayList<Uri> uri = data.getParcelableArrayListExtra("uri");
            if (uri!=null){
                for (Uri temp : uri) {
                    if (temp.getScheme()!=null&&"http".equals(temp.getScheme())){
                        mHasUpload.add(temp);
                    }else{
                        mPreUpload.add(temp);
                    }
                }
                getView().setPictureCount(uri.size());
            }
        }
    }

    private void dealPicture(){
        if (mPlaceDetail.getPicture()!=null){
            for (String s : mPlaceDetail.getPicture()) {
                mHasUpload.add(Uri.parse(s));
            }
            mPlaceDetail.setPicture(null);
        }
    }

    public void submit(){

        if (TextUtils.isEmpty(mPlaceDetail.getName())){
            JUtils.Toast("请填写钓点名字");
            return;
        }
        if (TextUtils.isEmpty(mPlaceDetail.getAddress())){
            JUtils.Toast("请选择钓点地址");
            return;
        }
        if (mPreUpload.size()+mHasUpload.size()==0){
            JUtils.Toast("请至少添加一张图片");
            return;
        }
        ArrayList<String> urls = new ArrayList<>();
        for (Uri uri : mHasUpload) {
            urls.add(uri.toString());
        }
        mPlaceDetail.setPicture(urls);

        JUtils.Log(mPreUpload.get(0).getPath()+" File"+new File(mPreUpload.get(0).getPath()).exists());

        File[] files = new File[mPreUpload.size()];
        for (int i = 0; i < mPreUpload.size(); i++) {
            files[i] = new File(mPreUpload.get(i).getPath());
        }
        getView().getExpansion().showProgressDialog("开始上传");
        ImageModel.getInstance().putImage(files)
                .doOnError(throwable -> {
                    getView().getExpansion().dismissProgressDialog();
                    JUtils.Toast("图片上传失败");
                    JUtils.Log("Error:"+throwable.getLocalizedMessage());
                })
                .doOnNext(s -> {
                    getView().getExpansion().showProgressDialog("上传图片第"+(mPlaceDetail.getPicture().size()-mHasUpload.size())+"/"+mPreUpload.size()+"张");
                    mPlaceDetail.getPicture().add(s);
                })
                .toList()
                .flatMap(strings -> {
                    getView().getExpansion().showProgressDialog("上传服务器中");
                    mPlaceDetail.setPreview(mPlaceDetail.getPicture().get(0));
                    return PlaceModel.getInstance().publishPlace(mPlaceDetail);
                })
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        JUtils.Toast("提交成功");
                        getView().finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        JUtils.Log("Error:" + e.getLocalizedMessage());
                    }
                });

    }

}
