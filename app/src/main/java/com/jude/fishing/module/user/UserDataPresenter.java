package com.jude.fishing.module.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.fishing.model.ImageModel;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.jude.utils.JUtils;

import java.io.File;

/**
 * Created by heqiang on 2015/9/23.
 */
public class UserDataPresenter extends Presenter<UserDataActivity> {
    private Uri avatar;
    private ImageProvider provider;
    OnImageSelectListener listener = new OnImageSelectListener() {

        @Override
        public void onImageSelect() {
            getView().getExpansion().showProgressDialog("加载中");
        }

        @Override
        public void onImageLoaded(Uri uri) {
            getView().getExpansion().dismissProgressDialog();
            provider.corpImage(uri, 200, 200, new OnImageSelectListener() {
                @Override
                public void onImageSelect() {

                }

                @Override
                public void onImageLoaded(Uri uri) {
                    avatar = uri;
                    getView().setAvatar(uri);
                }

                @Override
                public void onError() {

                }
            });
        }

        @Override
        public void onError() {

        }
    };

    @Override
    protected void onCreate(UserDataActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        provider = new ImageProvider(getView());
    }

    public void editFace(int style) {
        switch (style) {
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

    public void sendUserData(String name, String region, boolean isMale) {
        getView().getExpansion().showProgressDialog("上传图片中...");
        ImageModel.getInstance().putImage(new File(avatar.getPath())).subscribe(new ServiceResponse<String>() {
            @Override
            public void onNext(String path) {
                getView().getExpansion().dismissProgressDialog();
                Intent intent = new Intent(getView(), UserData2Activity.class);
                intent.putExtra("avatar", path);
                intent.putExtra("name", name);
                intent.putExtra("region", region);
                intent.putExtra("isMale", isMale);
                getView().startActivity(intent);
                getView().finish();
            }

            @Override
            public void onError(Throwable e) {
                getView().getExpansion().dismissProgressDialog();
                JUtils.Toast("图片上传失败");
            }
        });
    }
}
