package com.jude.fishing.module.gofishing;

import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.fishing.model.RongYunModel;
import com.jude.fishing.model.SocialModel;
import com.jude.fishing.model.entities.Date;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.utils.JUtils;

import rx.Observable;

/**
 * Created by heqiang on 2015/12/2.
 */
public class FishingDetailPresenter extends BeamDataActivityPresenter<FishingDetailActivity, Date> {
    String id;

    @Override
    protected void onCreate(FishingDetailActivity view, Bundle savedState) {
        id = getView().getIntent().getStringExtra("id");
        getView().getExpansion().showProgressPage();
        onRefresh();
    }

    private void onRefresh(){
        SocialModel.getInstance().getDatePersonList(id)
                .flatMap(personBriefs -> SocialModel.getInstance().getDateItem(id)
                        .flatMap(fishingSeed -> {
                            fishingSeed.setEnrollMember(personBriefs);
                            return Observable.just(fishingSeed);
                        }))
                .subscribe(this);
    }

    public void joinDate(String title, boolean joined) {
        if (joined) {
            RongYunModel.getInstance().chatGroup(getView(), id, title);
            return;
        }
        getView().getExpansion().showProgressDialog("请稍等");
        SocialModel.getInstance().joinDate(id)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        super.onNext(o);
                        JUtils.Toast("报名成功,正在将你自动加入群组");
                        RongYunModel.getInstance().joinGroup(getView(), id, title);
                        getView().finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
