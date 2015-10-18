package com.jude.fishing.model;

import com.jude.beam.model.AbsModel;
import com.jude.fishing.model.entities.Seed;
import com.jude.fishing.model.entities.SeedDetail;
import com.jude.fishing.model.service.DefaultTransform;
import com.jude.fishing.model.service.ServiceClient;

import java.util.List;

import rx.Observable;

/**
 * Created by Mr.Jude on 2015/9/12.
 */
public class BlogModel extends AbsModel {
    public static BlogModel getInstance() {
        return getInstance(BlogModel.class);
    }


    public Observable<List<Seed>> getBlogGround(int page){
        return ServiceClient.getService().getBlogGround(page).compose(new DefaultTransform<>());
    }

    public Observable<List<Seed>> getBlogFriend(int page){
        return ServiceClient.getService().getBlogFriend(page).compose(new DefaultTransform<>());
    }

    public Observable<List<Seed>> getBlogNearby(int page,int count,double lat,double lng){
        return ServiceClient.getService().getBlogNearby(page, count, lat, lng).compose(new DefaultTransform<>());
    }

    public Observable<List<Seed>> getUserBlog(int id,int page){
        return ServiceClient.getService().getUserBlog(id,page).compose(new DefaultTransform<>());
    }

    public Observable<SeedDetail> getSeedDetail(int id){
        return ServiceClient.getService().getBlogDetail(id).compose(new DefaultTransform<>());
    }

    public Observable<Object> blogPraise(int id){
        return ServiceClient.getService().blogPraise(id).compose(new DefaultTransform<>());
    }

    public Observable<Object> blogUnPraise(int id){
        return ServiceClient.getService().blogUnPraise(id).compose(new DefaultTransform<>());
    }

    public Observable<Object> addBlog(String content,String images,String address,double lng,double lat){
        return ServiceClient.getService().addBlog(content, images, address, lng, lat)
                .doOnNext(o -> {
                    int blogs = AccountModel.getInstance().getAccount().getBlogCount();
                    AccountModel.getInstance().getAccount().setCared(blogs+1+"");
                })
                .compose(new DefaultTransform<>());
    }

    public Observable<Object> sendBlogComment(int id,int fid,String content){
        return ServiceClient.getService().blogComment(id, fid, content).compose(new DefaultTransform<>());
    }

}
