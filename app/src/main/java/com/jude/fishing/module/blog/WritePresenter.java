package com.jude.fishing.module.blog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.google.gson.Gson;
import com.jude.beam.bijection.Presenter;
import com.jude.exgridview.PieceViewGroup;
import com.jude.fishing.model.BlogModel;
import com.jude.fishing.model.ImageModel;
import com.jude.fishing.model.LocationModel;
import com.jude.fishing.model.entities.Location;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.jude.utils.JUtils;

import java.io.File;
import java.util.ArrayList;

import rx.functions.Action1;

/**
 * Created by Mr.Jude on 2015/9/13.
 */
public class WritePresenter extends Presenter<WriteActivity> implements PieceViewGroup.OnViewDeleteListener, GeocodeSearch.OnGeocodeSearchListener {
    private ImageProvider provider;
    private ArrayList<Uri> uriArrayList = new ArrayList<>();
    private GeocodeSearch mGeocoderSearch;
    Location location;
    String address = "";
    OnImageSelectListener listener = new OnImageSelectListener() {

        @Override
        public void onImageSelect() {
            getView().getExpansion().showProgressDialog("加载中");
        }

        @Override
        public void onImageLoaded(Uri uri) {
            getView().getExpansion().dismissProgressDialog();
            getView().addImage(ImageProvider.readImageWithSize(uri, 300, 300));
            uriArrayList.add(uri);
        }

        @Override
        public void onError() {

        }
    };

    @Override
    protected void onCreate(WriteActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        provider = new ImageProvider(getView());
        mGeocoderSearch = new GeocodeSearch(getView());
        mGeocoderSearch.setOnGeocodeSearchListener(this);
        location = LocationModel.getInstance().getCurLocation();
        if (location != null) {
            mGeocoderSearch.getFromLocationAsyn(new RegeocodeQuery(new LatLonPoint(location.getLatitude(), location.getLongitude()), 50, GeocodeSearch.AMAP));
        }
    }

    public void editFace(int style) {
        if (uriArrayList.size()>=9){
            JUtils.Toast("最多上传9张图片");
            return;
        }
        switch (style) {
            case 0:
                provider.getImageFromCamera(listener);
                break;
            case 1:
                provider.getImageFromAlbum(listener,9-uriArrayList.size());
                break;
            case 2:
                provider.getImageFromNet(listener);
                break;
        }
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        provider.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onViewDelete(int i) {
        uriArrayList.remove(i);
    }

    public void writeBlog(String content) {
        getView().getExpansion().showProgressDialog("请稍候");
        int size = uriArrayList.size();
        if (size > 0) {
            File[] files = new File[size];
            for (int i = 0; i < size; i++) {
                files[i] = new File(uriArrayList.get(i).getPath());
            }
            ImageModel.getInstance().putImageAsync(files)
                    .doOnError(new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            getView().getExpansion().dismissProgressDialog();
                            JUtils.Toast("图片上传失败");
                        }
                    })
                    .toList()
                    .flatMap(strings -> {
                        getView().getExpansion().showProgressDialog("上传服务器中");
                        Gson gson = new Gson();
                        String gsonStr = gson.toJson(strings);
                        JUtils.Log(gsonStr);
                        return BlogModel.getInstance().addBlog(content, gsonStr, address, location.getLongitude(), location.getLatitude());
                    })
                    .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                    .subscribe(new ServiceResponse<Object>() {
                        @Override
                        public void onNext(Object o) {
                            JUtils.ToastLong("提交成功，图片需要几十秒上传完成才能查看");
                            getView().finish();
                        }
                    });
        } else {
            BlogModel.getInstance().addBlog(content, "[]", address, location.getLongitude(), location.getLatitude())
                    .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                    .subscribe(new ServiceResponse<Object>() {
                        @Override
                        public void onNext(Object o) {
                            JUtils.Toast("提交成功");
                            getView().finish();
                        }
                    });
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        address = regeocodeResult.getRegeocodeAddress().getTownship();
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
    }
}
