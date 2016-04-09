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

    @Override
    protected void onCreate(ArticleWebActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
    }

    public void praise(){
        if (getData().isPraised()) {
            getData().setPraised(false);
            getData().setPraiseCount(getData().getPraiseCount()-1);
            ArticleModel.getInstance().unPraise(getData().getId()).subscribe(new ServiceResponse<Object>());
        }else{
            getData().setPraised(true);
            getData().setPraiseCount(getData().getPraiseCount() + 1);
            ArticleModel.getInstance().praise(getData().getId()).subscribe(new ServiceResponse<Object>());
        }
        refresh();
    }

    public void collect(){
        if (getData().isCollected()) {
            getData().setIsCollected(false);
            ArticleModel.getInstance().unCollect(getData().getId()).subscribe(new ServiceResponse<Object>());
        }else{
            getData().setIsCollected(true);
            ArticleModel.getInstance().collect(getData().getId()).subscribe(new ServiceResponse<Object>());
        }
        refresh();
    }

}
