package com.jude.fishing.module.blog;

import android.content.Intent;

import com.jude.beam.bijection.Presenter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.module.user.LoginActivity;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
public class BlogPresenter extends Presenter<BlogFragment> {
    public void write() {
        if (AccountModel.getInstance().getAccount() == null) {
            getView().startActivity(new Intent(getView().getActivity(), LoginActivity.class));
        } else {
            getView().startActivity(new Intent(getView().getActivity(), WriteActivity.class));
        }
    }


}
