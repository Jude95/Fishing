package com.jude.fishing.module.place;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.fishing.model.PlaceModel;
import com.jude.fishing.model.entities.EvaluateComment;
import com.jude.fishing.model.entities.EvaluateDetail;
import com.jude.fishing.model.service.ServiceResponse;

import rx.functions.Action1;

/**
 * Created by zhuchenxi on 15/10/3.
 */
public class EvaluateCommentPresenter extends BeamListActivityPresenter<EvaluateCommentActivity,EvaluateComment> {
    private int id;
    private RecyclerArrayAdapter.ItemView mHeader;
    @Override
    protected void onCreate(EvaluateCommentActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        id = getView().getIntent().getIntExtra("id", 0);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        PlaceModel.getInstance().getEvaluateDetail(id)
                .doOnNext(new Action1<EvaluateDetail>() {
                    @Override
                    public void call(EvaluateDetail evaluateDetail) {
                        getAdapter().removeHeader(mHeader);
                    }
                })
                .map(seedDetail -> {
                    getAdapter().addHeader(mHeader = new RecyclerArrayAdapter.ItemView() {
                        @Override
                        public View onCreateView(ViewGroup viewGroup) {
                            return getView().getEvaluateDetailView(seedDetail, viewGroup);
                        }

                        @Override
                        public void onBindView(View view) {

                        }
                    });
                    return seedDetail.getComments();
                })
                .unsafeSubscribe(getRefreshSubscriber());
    }

    public void sentComment(String content,int fid){
        getView().getExpansion().showProgressDialog("提交中");
        PlaceModel.getInstance().sendEvaluateComment(id,fid,content)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        getView().getListView().getSwipeToRefresh().setRefreshing(true);
                        onRefresh();
                    }
                });
    }
}
