package com.jude.fishing.module.user;

import com.jude.beam.bijection.Presenter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.entities.Account;
import com.jude.fishing.model.service.ServiceResponse;

/**
 * Created by Mr.Jude on 2015/9/13.
 */
public class LoginPresenter extends Presenter<LoginActivity> {

    public void login(String name,String password){
        getView().getExpansion().showProgressDialog("登录中");
        AccountModel.getInstance().login(name, password)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Account>() {
                    @Override
                    public void onNext(Account account) {
                        getView().finish();
                    }

                    @Override
                    public void onServiceError(int status, String info) {
                        super.onServiceError(status, info);

                    }
                });
    }
}
