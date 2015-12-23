package com.jude.fishing.module.place;

import android.content.Intent;
import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.fishing.config.API;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.ImageModel;
import com.jude.fishing.model.PlaceModel;
import com.jude.fishing.model.entities.PlaceDetail;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.fishing.module.user.LoginActivity;
import com.umeng.share.ShareManager;

/**
 * Created by Mr.Jude on 2015/9/23.
 */
public class PlaceDetailPresenter extends BeamDataActivityPresenter<PlaceDetailActivity,PlaceDetail> {
    private int id;
    private PlaceDetail mDetail;

    @Override
    protected void onCreate(PlaceDetailActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        id = getView().getIntent().getIntExtra("id",0);
        PlaceModel.getInstance().getPlaceDetail(getView().getIntent().getIntExtra("id", 0))
                .doOnNext(placeDetail -> mDetail=placeDetail)
                .unsafeSubscribe(getDataSubscriber());
    }

    public boolean isAuthor(){
        if (AccountModel.getInstance().getAccount()!=null&&mDetail!=null)
            return AccountModel.getInstance().getAccount().getUID()==mDetail.getAuthorId();
        else
            return false;
    }

    public void startEdit(){
        Intent i = new Intent(getView(),PlaceAddActivity.class);
        i.putExtra("place",mDetail);
        getView().startActivity(i);
    }

    public boolean collect(){
        if (AccountModel.getInstance().getAccount()==null){
            getView().startActivity(new Intent(getView(), LoginActivity.class));
            return false;
        }

        if (mDetail.isCollected())
            PlaceModel.getInstance().unCollectPlace(id).subscribe(new ServiceResponse<Object>() {});
        else
            PlaceModel.getInstance().collectPlace(id).subscribe(new ServiceResponse<Object>() {});
        mDetail.setCollected(!mDetail.isCollected());
        return mDetail.isCollected();
    }

    public void startNavigation(){
        Intent i = new Intent(getView(),PlaceMapPathActivity.class);
        i.putExtra("place",mDetail);
        getView().startActivity(i);
    }

    public void share(){
        String content = mDetail.getName()
                + "位于"+mDetail.getAddress()
                + ",欢迎使用空钩查看详情";

        ShareManager.getInstance().share(getView(), content, mDetail.getName(), API.URL.SHARE + mDetail.getId(), ImageModel.getInstance().getSmallImage(mDetail.getPreview()).toString());
    }
}
