package com.jude.fishing.module.main;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.beam.expansion.data.BeamDataFragmentPresenter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.RongYunModel;
import com.jude.fishing.model.entities.Account;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.fishing.module.blog.BlogFragment;
import com.jude.fishing.module.place.PlaceFragment;
import com.jude.fishing.module.social.MessageFragment;
import com.jude.fishing.module.user.LoginActivity;
import com.jude.fishing.module.user.UserDetailActivity;
import com.jude.fishing.module.user.UserFragment;
import com.jude.utils.JUtils;

import rx.Subscription;

/**
 * Created by Mr.Jude on 2015/9/17.
 */
public class DrawerPresenter extends BeamDataFragmentPresenter<DrawerFragment,Account> {
    private Fragment lastFragment;

    private PlaceFragment mPlaceFragment;
    private BlogFragment mBlogFragment;
    private MessageFragment mMessageFragment;
    private UserFragment mUserFragment;

    private Subscription mAccountSubscription;
    private Subscription mMessageCountSubscription;

    @Override
    protected void onCreateView(DrawerFragment view) {
        super.onCreateView(view);
        mAccountSubscription = AccountModel.getInstance().getAccountUpdateSubject().unsafeSubscribe(getDataSubscriber());
        mMessageCountSubscription = RongYunModel.getInstance().registerNotifyCount(count -> getView().setMessageCount(count));
    }

    public boolean checkLogin(){
        if (AccountModel.getInstance().getAccount()==null){
            getView().startActivity(new Intent(getView().getActivity(), LoginActivity.class));
            return false;
        }else{
            return true;
        }
    }

    public void signIn(){
        ((BeamBaseActivity)getView().getActivity()).getExpansion().showProgressDialog("签到中");
        AccountModel.getInstance().signIn()
                .flatMap(o -> AccountModel.getInstance().updateMyInfo())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        ((BeamBaseActivity)getView().getActivity()).getExpansion().dismissProgressDialog();
                        JUtils.Toast("签到成功 积分+2");
                    }
                });
    }

    @Override
    protected void onDestroyView() {
        super.onDestroyView();
        mAccountSubscription.unsubscribe();
        mMessageCountSubscription.unsubscribe();
    }

    public void showUserDetail(){
        if (checkLogin()){
            Intent i = new Intent(getView().getActivity(),UserDetailActivity.class);
            i.putExtra("id",AccountModel.getInstance().getAccount().getUID());
            getView().startActivity(i);
        }
    }

}
