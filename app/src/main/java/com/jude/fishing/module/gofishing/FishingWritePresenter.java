package com.jude.fishing.module.gofishing;

import android.app.Activity;

import com.jude.beam.bijection.Presenter;
import com.jude.fishing.model.SocialModel;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.utils.JUtils;

/**
 * Created by heqiang on 2015/12/2.
 */
public class FishingWritePresenter extends Presenter<FishingWriteActivity> {
    public void addDateInfor(String title, String address, String content, long time) {
        SocialModel.getInstance().addDateInfor(title, address, content, time)
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        JUtils.Toast("提交成功");
                        getView().setResult(Activity.RESULT_OK);
                        getView().finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
