package com.jude.fishing.module.blog;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.jude.beam.expansion.list.BeamListActivityPresenter;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.fishing.model.BlogModel;
import com.jude.fishing.model.bean.SeedComment;
import com.jude.utils.JUtils;

/**
 * Created by zhuchenxi on 15/9/27.
 */
public class BlogDetailPresenter extends BeamListActivityPresenter<BlogDetailActivity,SeedComment> {

    @Override
    protected void onCreate(BlogDetailActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        BlogModel.getInstance().getSeedDetail(getView().getIntent().getIntExtra("id",0)).map(seedDetail -> {
            getAdapter().addHeader(new RecyclerArrayAdapter.ItemView() {
                @Override
                public View onCreateView(ViewGroup viewGroup) {
                    return getView().getBlogDetailView(seedDetail,viewGroup);
                }

                @Override
                public void onBindView(View view) {

                }
            });
            JUtils.Log("onCreate"+seedDetail.getComment());
            return seedDetail.getComment();
        }).subscribe(getRefreshSubscriber());
    }
}
