package com.jude.fishing.module.place;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.amap.api.maps.model.LatLng;
import com.jude.beam.expansion.data.BeamDataActivityPresenter;
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

    private PlaceDetail mPlaceDetail = new PlaceDetail();

    private ArrayList<Uri> mPreUpload = new ArrayList<>();
    private ArrayList<Uri> mHasUpload = new ArrayList<>();

    @Override
    protected void onCreateView(PlaceAddActivity view) {
        super.onCreateView(view);
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
            mPlaceDetail.setBriefAddr(data.getStringExtra("briefAddress"));
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
                    if (temp.getScheme()!=null&&temp.getScheme().equals("http")){
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

    int uploadSize = 0;
    public void submit(){

        if (TextUtils.isEmpty(mPlaceDetail.getName())){
            JUtils.Toast("请填写钓点名字");
            return;
        }
        if (TextUtils.isEmpty(mPlaceDetail.getAddress())){
            JUtils.Toast("请选择钓点地址");
            return;
        }
        if (TextUtils.isEmpty(mPlaceDetail.getFishType())){
            JUtils.Toast("请填写钓点鱼种");
            return;
        }
        if (mPreUpload.size()+mHasUpload.size()==0){
            JUtils.Toast("请至少添加一张图片");
            return;
        }

        String[] urls = new String[mHasUpload.size()+mPreUpload.size()];
        for (int i = 0; i < mHasUpload.size(); i++) {
            urls[i] = mHasUpload.get(i).getPath();
        }
        mPlaceDetail.setPicture(urls);
        File[] files = new File[mPreUpload.size()];
        for (int i = 0; i < mPreUpload.size(); i++) {
            files[i] = new File(mPreUpload.get(i).toString());
        }
        uploadSize = 0;
        getView().getExpansion().showProgressDialog("开始上传");
        ImageModel.getInstance().putImage(files)
                .doOnError(throwable -> {
                    getView().getExpansion().dismissProgressDialog();
                    JUtils.Toast("图片上传失败");
                    JUtils.Log(throwable.getLocalizedMessage());
                })
                .filter(s -> {
                    getView().getExpansion().showProgressDialog("上传图片中,第" + (mHasUpload.size() + uploadSize) + "/" + mPlaceDetail.getPicture().length + "张");
                    mPlaceDetail.getPicture()[mHasUpload.size() + uploadSize] = s;
                    return true;
                })
                .buffer(files.length)
                .flatMap(strings -> {
                    getView().getExpansion().showProgressDialog("上传服务器中");
                    mPlaceDetail.setPreview(mPlaceDetail.getPicture()[0]);
                    return PlaceModel.getInstance().publishPlace(mPlaceDetail);
                })
                .subscribe(new ServiceResponse() {
                    @Override
                    public void onNext(Object o) {
                        getView().getExpansion().dismissProgressDialog();
                        JUtils.Toast("提交成功");
                        getView().finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        getView().getExpansion().dismissProgressDialog();
                    }
                });

    }

}
