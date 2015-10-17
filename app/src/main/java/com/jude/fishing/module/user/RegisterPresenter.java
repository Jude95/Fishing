package com.jude.fishing.module.user;

import android.app.Activity;
import android.content.Intent;

import com.jude.beam.bijection.Presenter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.utils.JUtils;

/**
 * Created by Mr.Jude on 2015/9/13.
 */
public class RegisterPresenter extends Presenter<RegisterActivity> {
    public void checkTel(String tel, String password) {
        getView().getExpansion().showProgressDialog("提交中");
        AccountModel.getInstance().checkTel(tel)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        Intent i = new Intent(getView(), RegisterVerifyActivity.class);
                        i.putExtra("number", tel);
                        i.putExtra("password", password);
                        getView().startActivityForResult(i,100);
                    }
                });
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        JUtils.Log("requestCode"+requestCode+"  resultCode"+resultCode);
        if (resultCode == Activity.RESULT_OK){
            getView().setResult(Activity.RESULT_OK);
            getView().finish();
        }
    }
}
