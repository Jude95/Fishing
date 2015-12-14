package com.jude.fishing.module.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.jude.beam.expansion.data.BeamDataFragmentPresenter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.RongYunModel;
import com.jude.fishing.model.entities.Account;
import com.jude.fishing.module.blog.BlogFragment;
import com.jude.fishing.module.place.PlaceFragment;
import com.jude.fishing.module.social.MessageFragment;
import com.jude.fishing.module.user.LoginActivity;
import com.jude.fishing.module.user.UserDataActivity;
import com.jude.fishing.module.user.UserDetailActivity;
import com.jude.fishing.module.user.UserFragment;

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
//        showBlogFragment();
        mAccountSubscription = AccountModel.getInstance().registerAccountUpdate(this);
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

    @Override
    protected void onDestroyView() {
        super.onDestroyView();
        mAccountSubscription.unsubscribe();
        mMessageCountSubscription.unsubscribe();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AccountModel.getInstance().getAccount()!=null&&TextUtils.isEmpty(AccountModel.getInstance().getAccount().getName())){
            getView().startActivity(new Intent(getView().getActivity(), UserDataActivity.class));
        }
    }

    public void showUserDetail(){
        if (checkLogin()){
            Intent i = new Intent(getView().getActivity(),UserDetailActivity.class);
            i.putExtra("id",AccountModel.getInstance().getAccount().getUID());
            getView().startActivity(i);
        }
    }

    public void showPlaceFragment(){
        if (mPlaceFragment == null)mPlaceFragment = new PlaceFragment();
        if (lastFragment == mPlaceFragment){
            ((MainActivity)getView().getActivity()).closeDrawer();
            return;
        }
        getView().showFragment(mPlaceFragment);
        lastFragment = mPlaceFragment;
        getView().focusView(getView().place);
    }


    public void showBlogFragment(){
        if (mBlogFragment == null) mBlogFragment = new BlogFragment();
        if (lastFragment == mBlogFragment){
            ((MainActivity)getView().getActivity()).closeDrawer();
            return;
        }
        getView().showFragment(mBlogFragment);
        lastFragment = mBlogFragment;
        getView().focusView(getView().blog);
    }

    public void showMessageFragment(){
        if (checkLogin()) {

            if (mMessageFragment == null) mMessageFragment = new MessageFragment();
            if (lastFragment == mMessageFragment){
                ((MainActivity)getView().getActivity()).closeDrawer();
                return;
            }

            getView().showFragment(mMessageFragment);
            lastFragment = mMessageFragment;
            getView().focusView(getView().message);

        }
    }

    public void showUserFragment(){
        if (checkLogin()) {

            if (mUserFragment == null) mUserFragment = new UserFragment();
            if (lastFragment == mUserFragment){
                ((MainActivity)getView().getActivity()).closeDrawer();
                return;
            }

            getView().showFragment(mUserFragment);
            lastFragment = mUserFragment;

            getView().focusView(getView().user);

        }
    }
}
