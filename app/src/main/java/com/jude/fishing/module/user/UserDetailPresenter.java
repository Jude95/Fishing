package com.jude.fishing.module.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.fishing.event.OnCompleteListener;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.ImageModel;
import com.jude.fishing.model.RongYunModel;
import com.jude.fishing.model.SocialModel;
import com.jude.fishing.model.entities.Account;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.jude.utils.JUtils;

import java.io.File;

import rx.Subscription;

/**
 * Created by Mr.Jude on 2015/9/18.
 */
public class UserDetailPresenter extends BeamDataActivityPresenter<UserDetailActivity, Account> {
    private int id;
    private ImageProvider provider;
    OnImageSelectListener listener = new OnImageSelectListener() {

        @Override
        public void onImageSelect() {
            getView().getExpansion().showProgressDialog("加载中");
        }

        @Override
        public void onImageLoaded(Uri uri) {
            changeUserBg(uri.getPath());
        }

        @Override
        public void onError() {

        }
    };

    Subscription subscription;
    @Override
    protected void onCreate(UserDetailActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        provider = new ImageProvider(getView());
        id = getView().getIntent().getIntExtra("id", 0);
        if (id == 0) subscription = AccountModel.getInstance().registerAccountUpdate(this);
        else subscription = SocialModel.getInstance().getUserDetail(id).subscribe(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();JUtils.Log("onDestroy detail"+subscription.isUnsubscribed());
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

    public void attention() {
        getView().getExpansion().showProgressDialog("请稍等...");
        SocialModel.getInstance().attention(id)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        getView().changeAttention();
                    }
                });
    }

    public void unAttention() {
        getView().getExpansion().showProgressDialog("请稍等...");
        SocialModel.getInstance().unAttention(id)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        getView().changeAttention();
                    }
                });
    }

    public void chat(String name) {
        if (checkLogin()) RongYunModel.getInstance().chatPerson(getView(), String.valueOf(id), name);
    }

    String bgUri;
    void changeUserBg(String uri){
        ImageModel.getInstance().putImage(new File(uri),new OnCompleteListener(){

            @Override
            public void onComplete() {
                getView().changeBackground(bgUri);
            }
        })
                .doOnError(throwable -> {
                    getView().getExpansion().dismissProgressDialog();
                    JUtils.Toast("图片上传失败");
                })
                .flatMap(path -> {
                    bgUri = path;
                    return AccountModel.getInstance().changeUserBg(path);
                })
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {

                        JUtils.Toast("修改成功");
                    }
                });

    }

    boolean checkLogin(){
        if (AccountModel.getInstance().getAccount()==null){
            getView().startActivity(new Intent(getView(),LoginActivity.class));
            return false;
        }
        return true;
    }

    public void goToActivityWithLogin(Class clz,int uid){
        if (checkLogin()){
            goToActivity(clz, uid);
        }
    }

    //调用相关页面
    public void goToActivity(Class clz,int uid) {
        Intent intent = new Intent(getView(),clz);
        intent.putExtra("id",uid);
        getView().startActivity(intent);
    }

    public void changeAttention(boolean isAttended) {
        if (checkLogin()) {
            if (isAttended)
                unAttention();
            else
                attention();
        }
    }
}
