package com.jude.fishing.module.setting;


import com.jude.beam.bijection.Presenter;
import com.jude.fishing.model.CommonModel;
import com.jude.fishing.model.service.ServiceResponse;

/**
 * Created by Mr.Jude on 2015/8/13.
 */
public class FeedPresenter extends Presenter<FeedActivity> {
    public void feedback(String content) {
        getView().getExpansion().showProgressDialog("提交中");
        CommonModel.getInstance().feedback(content)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        getView().finish();
                    }
                });
    }
}
