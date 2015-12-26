package com.jude.fishing.module.article;

import android.os.Bundle;

import com.jude.beam.expansion.data.BeamDataActivityPresenter;
import com.jude.fishing.model.ArticleModel;
import com.jude.fishing.model.entities.Article;
import com.jude.fishing.model.service.ServiceResponse;

/**
 * Created by Mr.Jude on 2015/12/26.
 */
public class ArticleWebPresenter extends BeamDataActivityPresenter<ArticleWebActivity,Article> {
    private Article data;

    @Override
    protected void onCreate(ArticleWebActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        data = (Article) getView().getIntent().getSerializableExtra("data");
        publishObject(data);
    }

    public void praise(){
        if (data.isPraised()) {
            data.setPraised(false);
            data.setPraiseCount(data.getPraiseCount()-1);
            ArticleModel.getInstance().unPraise(data.getId()).subscribe(new ServiceResponse<Object>());
        }else{
            data.setPraised(true);
            data.setPraiseCount(data.getPraiseCount() + 1);
            ArticleModel.getInstance().praise(data.getId()).subscribe(new ServiceResponse<Object>());
        }
        publishObject(data);
    }

    public void collect(){
        if (data.isCollected()) {
            data.setIsCollected(false);
            ArticleModel.getInstance().unCollect(data.getId()).subscribe(new ServiceResponse<Object>());
        }else{
            data.setIsCollected(true);
            ArticleModel.getInstance().collect(data.getId()).subscribe(new ServiceResponse<Object>());
        }
        publishObject(data);
    }

}
