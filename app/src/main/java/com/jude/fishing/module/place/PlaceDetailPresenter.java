package com.jude.fishing.module.place;

import android.content.Intent;
import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.PlaceModel;
import com.jude.fishing.model.entities.PlaceDetail;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.fishing.module.user.LoginActivity;

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
                .subscribe(this);
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
}
