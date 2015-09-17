package com.jude.fishing.module.blog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.exgridview.PieceViewGroup;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;

import java.util.ArrayList;

/**
 * Created by Mr.Jude on 2015/9/13.
 */
public class WritePresenter extends Presenter<WriteActivity> implements PieceViewGroup.OnViewDeleteListener{
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
        }

        @Override
        public void onError() {

        }
    };

    @Override
    protected void onCreate(WriteActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        provider = new ImageProvider(getView());
    }

    public void editFace(int style){
        switch (style){
            case 0:
                provider.getImageFromCamera(listener);
                break;
            case 1:
                provider.getImageFromAlbum(listener);
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
}
