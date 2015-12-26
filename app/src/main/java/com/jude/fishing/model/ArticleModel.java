package com.jude.fishing.model;

import com.jude.beam.model.AbsModel;
import com.jude.fishing.model.entities.Article;
import com.jude.fishing.model.service.DefaultTransform;
import com.jude.fishing.model.service.ServiceClient;

import java.util.List;

import rx.Observable;

/**
 * Created by zhuchenxi on 15/12/24.
 */
public class ArticleModel extends AbsModel {
    public static ArticleModel getInstance() {
        return getInstance(ArticleModel.class);
    }

    public Observable<List<Article>> getArticleList(int type, int page){
        return ServiceClient.getService().getArticles(type,page,30).compose(new DefaultTransform<>());
    }

    public Observable<Object> collect(int id){
        return ServiceClient.getService().collectArticle(id).compose(new DefaultTransform<>());
    }
    public Observable<Object> praise(int id){
        return ServiceClient.getService().praiseArticle(id).compose(new DefaultTransform<>());
    }
    public Observable<Object> unCollect(int id){
        return ServiceClient.getService().unCollectArticle(id).compose(new DefaultTransform<>());
    }
    public Observable<Object> unPraise(int id){
        return ServiceClient.getService().unPraiseArticle(id).compose(new DefaultTransform<>());
    }
}
