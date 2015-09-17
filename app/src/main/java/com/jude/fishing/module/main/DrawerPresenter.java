package com.jude.fishing.module.main;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.jude.beam.expansion.data.BeamDataFragmentPresenter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.bean.Account;
import com.jude.fishing.module.blog.BlogFragment;
import com.jude.fishing.module.place.PlaceFragment;
import com.jude.fishing.module.social.CollectFragment;
import com.jude.fishing.module.social.FriendsFragment;
import com.jude.fishing.module.social.MessageFragment;
import com.jude.fishing.module.user.LoginActivity;
import com.jude.fishing.module.user.UserFragment;

/**
 * Created by Mr.Jude on 2015/9/17.
 */
public class DrawerPresenter extends BeamDataFragmentPresenter<DrawerFragment,Account> {
    private Fragment lastFragment;

    private PlaceFragment mPlaceFragment;
    private BlogFragment mBlogFragment;
    private MessageFragment mMessageFragment;
    private FriendsFragment mFriendsFragment;
    private CollectFragment mCollectFragment;
    private UserFragment mUserFragment;

    @Override
    protected void onCreateView(DrawerFragment view) {
        super.onCreateView(view);
        showPlaceFragment();
        AccountModel.getInstance().registerAccountUpdate(this);
    }

    public boolean checkLogin(){
        if (AccountModel.getInstance().getAccount()==null){
            getView().startActivity(new Intent(getView().getActivity(), LoginActivity.class));
            return false;
        }else{
            return true;
        }
    }

    public void showPlaceFragment(){
        if (mPlaceFragment == null)mPlaceFragment = new PlaceFragment();
        if (lastFragment == mPlaceFragment)return;
        getView().showFragment(mPlaceFragment);
        lastFragment = mPlaceFragment;
        getView().focusView(getView().place);
    }


    public void showBlogFragment(){
        if (mBlogFragment == null) mBlogFragment = new BlogFragment();
        if (lastFragment == mBlogFragment)return;
        getView().showFragment(mBlogFragment);
        lastFragment = mBlogFragment;
        getView().focusView(getView().blog);
    }

    public void showMessageFragment(){
        if (checkLogin()) {

            if (mMessageFragment == null) mMessageFragment = new MessageFragment();
            if (lastFragment == mMessageFragment)return;

            getView().showFragment(mMessageFragment);
            lastFragment = mMessageFragment;
            getView().focusView(getView().message);

        }
    }

    public void showFriendsFragment(){
        if (checkLogin()) {

            if (mFriendsFragment == null) mFriendsFragment = new FriendsFragment();
            if (lastFragment == mFriendsFragment)return;

            getView().showFragment(mFriendsFragment);
            lastFragment = mFriendsFragment;

            getView().focusView(getView().friend);

        }
    }

    public void showCollectFragment(){
        if (checkLogin()) {

            if (mCollectFragment == null) mCollectFragment = new CollectFragment();
            if (lastFragment == mCollectFragment)return;

            getView().showFragment(mCollectFragment);
            lastFragment = mCollectFragment;

            getView().focusView(getView().collect);

        }
    }

    public void showUserFragment(){
        if (checkLogin()) {

            if (mUserFragment == null) mUserFragment = new UserFragment();
            if (lastFragment == mUserFragment)return;

            getView().showFragment(mUserFragment);
            lastFragment = mUserFragment;

            getView().focusView(getView().user);

        }
    }
}
