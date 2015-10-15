package com.jude.fishing.module.place;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.fishing.model.PlaceModel;
import com.jude.fishing.model.entities.EvaluateComment;

/**
 * Created by zhuchenxi on 15/10/3.
 */
public class EvaluateCommentPresenter extends BeamListActivityPresenter<EvaluateCommentActivity,EvaluateComment> {
    @Override
    protected void onCreate(EvaluateCommentActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        PlaceModel.getInstance().getEvaluateDetail(getView().getIntent().getIntExtra("id", 0)).map(seedDetail -> {
            getAdapter().addHeader(new RecyclerArrayAdapter.ItemView() {
                @Override
                public View onCreateView(ViewGroup viewGroup) {
                    return getView().getEvaluateDetailView(seedDetail, viewGroup);
                }

                @Override
                public void onBindView(View view) {

                }
            });
            return seedDetail.getComments();
        }).subscribe(getRefreshSubscriber());
    }
}
