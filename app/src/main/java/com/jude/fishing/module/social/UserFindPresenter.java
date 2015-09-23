package com.jude.fishing.module.social;

import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.fishing.model.SocialModel;
import com.jude.fishing.model.bean.PersonBrief;

/**
 * Created by Mr.Jude on 2015/9/23.
 */
public class UserFindPresenter extends BeamListActivityPresenter<UserFindActivity,PersonBrief> {

    @Override
    protected void onCreateView(UserFindActivity view) {
        super.onCreateView(view);
        view.getListView().showRecycler();
    }

    public void search(String word){
        SocialModel.getInstance().searchUser(word).subscribe(getRefreshSubscriber());
    }
}
