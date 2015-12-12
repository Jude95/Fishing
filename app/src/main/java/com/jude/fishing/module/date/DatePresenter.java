package com.jude.fishing.module.date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jude.beam.expansion.list.BeamListFragmentPresenter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.SocialModel;
import com.jude.fishing.model.entities.Date;
import com.jude.fishing.module.user.LoginActivity;

/**
 * Created by heqiang on 2015/12/2.
 */
public class DatePresenter extends BeamListFragmentPresenter<DateFragment, Date> {
    @Override
    protected void onCreate(DateFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
    }

    @Override
    protected void onCreateView(DateFragment view) {
        super.onCreateView(view);
        onRefresh();
    }

    @Override
    public void onLoadMore() {
        SocialModel.getInstance().getDateList(getCurPage()).unsafeSubscribe(getMoreSubscriber());
    }

    @Override
    public void onRefresh() {
        SocialModel.getInstance().getDateList(0).unsafeSubscribe(getRefreshSubscriber());
    }

    public void goToWrite() {
        if (AccountModel.getInstance().getAccount() != null)
            getView().getActivity().startActivityForResult(new Intent(getView().getActivity(), DateWriteActivity.class), 120);
        else
            getView().getActivity().startActivity(new Intent(getView().getActivity(), LoginActivity.class));
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK)
            onRefresh();
    }
}
