package com.jude.fishing.module.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.ImageModel;
import com.jude.fishing.model.entities.Account;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.jude.utils.JUtils;

import java.io.File;

import rx.Subscription;

/**
 * Created by heqiang on 2015/9/23.
 */
public class UserDataPresenter extends BeamDataActivityPresenter<UserDataActivity, Account> {
    private Uri avatar;
    private String avatarUrl;
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

    Subscription subscription;
    @Override
    protected void onCreate(UserDataActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        provider = new ImageProvider(getView());
        subscription = AccountModel.getInstance().registerAccountUpdate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();JUtils.Log("onDestroy0"+subscription.isUnsubscribed());
        subscription.unsubscribe();
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

    public void sendUserData(String name, String region, int gender, String age, String skill, String sign, String avatarStr) {
        getView().getExpansion().showProgressDialog("提交中...");
        if (avatar == null) {
            avatarUrl = avatarStr;
            AccountModel.getInstance().modifyUserData(avatarUrl, name, gender, region, age, skill, sign)
                    .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                    .subscribe(new ServiceResponse<Object>() {
                        @Override
                        public void onNext(Object o) {
                            JUtils.Toast("修改成功");
                            getView().finish();
                        }
                    });
        } else
            ImageModel.getInstance().putImageAsync(new File(avatar.getPath()))
                    .doOnError(throwable -> {
                        getView().getExpansion().dismissProgressDialog();
                        JUtils.Toast("图片上传失败");
                    })
                    .flatMap(path -> {
                        getView().getExpansion().showProgressDialog("修改资料中...");
                        avatarUrl = path;
                        return AccountModel.getInstance().modifyUserData(path, name, gender, region, age, skill, sign);
                    })
                    .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                    .subscribe(new ServiceResponse<Object>() {
                        @Override
                        public void onNext(Object o) {
                            JUtils.Toast("修改成功");
                            getView().finish();
                        }
                    });
    }
}
