package com.jude.fishing.module.setting;


import com.jude.beam.bijection.Presenter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.utils.JUtils;

/**
 * Created by Mr.Jude on 2015/8/13.
 */
public class PwdChangePresenter extends Presenter<PwdChangeActivity> {
    public void changePwd(String oldPwd, String newPwd) {
        getView().getExpansion().showProgressDialog("请稍候...");
        AccountModel.getInstance().modPass(oldPwd, newPwd).subscribe(new ServiceResponse<Object>() {
            @Override
            public void onNext(Object o) {
                JUtils.Toast("修改密码成功");
                getView().finish();
            }

            @Override
            public void onCompleted() {
                getView().getExpansion().dismissProgressDialog();
            }
        });
    }
}
