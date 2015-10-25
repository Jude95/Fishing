package com.jude.fishing.module.blog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.BlogModel;
import com.jude.fishing.model.entities.SeedComment;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.fishing.module.user.LoginActivity;
import com.jude.utils.JUtils;

/**
 * Created by zhuchenxi on 15/9/27.
 */
public class BlogDetailPresenter extends BeamListActivityPresenter<BlogDetailActivity, SeedComment> {
    int wid;
    RecyclerArrayAdapter.ItemView mHeader;

    @Override
    protected void onCreate(BlogDetailActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        wid = getView().getIntent().getIntExtra("id", 0);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        BlogModel.getInstance().getSeedDetail(wid)
                .doOnNext(seedDetail -> getAdapter().removeHeader(mHeader))
                .map(seedDetail -> {
                    getAdapter().addHeader(mHeader = new RecyclerArrayAdapter.ItemView() {
                        @Override
                        public View onCreateView(ViewGroup viewGroup) {
                            return getView().getBlogDetailView(seedDetail, viewGroup);
                        }

                        @Override
                        public void onBindView(View view) {

                        }
                    });
                    JUtils.Log("onCreate" + seedDetail.getComments());
                    return seedDetail.getComments();
                }).unsafeSubscribe(getRefreshSubscriber());
    }

    public void blogPraise(boolean isPraised) {
        if (AccountModel.getInstance().getAccount()==null){
            getView().startActivity(new Intent(getView(), LoginActivity.class));
            JUtils.Toast("请登录");
            return;
        }
        if (isPraised)
            BlogModel.getInstance().blogUnPraise(wid)
                    .subscribe(new ServiceResponse<Object>() {
                        @Override
                        public void onNext(Object o) {
                            onRefresh();
                        }
                    });
        else
            BlogModel.getInstance().blogPraise(wid)
                    .subscribe(new ServiceResponse<Object>() {
                        @Override
                        public void onNext(Object o) {
                            onRefresh();
                        }
                    });
    }

    public void sentComment(int fid, String content) {
        BlogModel.getInstance().sendBlogComment(wid, fid, content)
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
