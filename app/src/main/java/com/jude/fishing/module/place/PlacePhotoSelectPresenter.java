package com.jude.fishing.module.place;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.exgridview.PieceViewGroup;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.jude.utils.JUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Mr.Jude on 2015/10/10.
 */
public class PlacePhotoSelectPresenter extends Presenter<PlacePhotoSelectActivity>  implements PieceViewGroup.OnViewDeleteListener{
    private ImageProvider provider;
    private ArrayList<Uri> uriArrayList = new ArrayList<>();
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
            JUtils.Log(uri.getPath() + " exist:" + new File(uri.getPath()).exists());
        }

        @Override
        public void onError() {

        }
    };

    @Override
    protected void onCreate(PlacePhotoSelectActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        provider = new ImageProvider(getView());
        ArrayList<Uri> arr = getView().getIntent().getParcelableArrayListExtra("uri");
        if (arr!=null)uriArrayList.addAll(arr);
    }

    @Override
    protected void onCreateView(PlacePhotoSelectActivity view) {
        super.onCreateView(view);
        for (Uri temp : uriArrayList) {
            getView().addImage(ImageProvider.readImageWithSize(temp,300,300));
        }
    }

    public void finish(){
        Intent i = new Intent();
        i.putParcelableArrayListExtra("uri",uriArrayList);
        getView().setResult(Activity.RESULT_OK,i);
        getView().finish();
    }

    public void editFace(int style){
        switch (style){
            case 0:
                provider.getImageFromCamera(listener);
                break;
            case 1:
                provider.getImageFromAlbum(listener);
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
        JUtils.Log("delete");
        uriArrayList.remove(i);
    }
}
