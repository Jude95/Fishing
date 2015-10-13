package com.jude.fishing.model;

import com.jude.beam.model.AbsModel;
import com.jude.fishing.model.entities.PersonBrief;
import com.jude.fishing.model.entities.PersonDetail;
import com.jude.fishing.model.service.DefaultTransform;
import com.jude.fishing.model.service.ServiceClient;

import java.util.List;

import rx.Observable;
import rx.Subscriber;


/**
 * Created by zhuchenxi on 15/9/20.
 */
public class SocialModel extends AbsModel {

    public static SocialModel getInstance() {
        return getInstance(SocialModel.class);
    }


    public Observable<List<PersonBrief>> searchUser(String word){
        return Observable.create(new Observable.OnSubscribe<List<PersonBrief>>() {
            @Override
            public void call(Subscriber<? super List<PersonBrief>> subscriber) {
                subscriber.onNext(AccountModel.getInstance().createVirtualPersonBriefs(20));
                subscriber.onCompleted();
            }
        }).compose(new DefaultTransform<>());
    }

    public Observable<List<PersonBrief>> getAround(int page){
        return Observable.create(new Observable.OnSubscribe<List<PersonBrief>>() {
            @Override
            public void call(Subscriber<? super List<PersonBrief>> subscriber) {
                subscriber.onNext(AccountModel.getInstance().createVirtualPersonBriefs(20));
                subscriber.onCompleted();
            }
        }).compose(new DefaultTransform<>());
    }

    public Observable<List<PersonBrief>> getAttentions(int uid){
        return Observable.create(new Observable.OnSubscribe<List<PersonBrief>>() {
            @Override
            public void call(Subscriber<? super List<PersonBrief>> subscriber) {
                subscriber.onNext(AccountModel.getInstance().createVirtualPersonBriefs(20));
                subscriber.onCompleted();
            }
        }).compose(new DefaultTransform<>());
    }

    public Observable<List<PersonBrief>> getFans(int uid){
        return Observable.create(new Observable.OnSubscribe<List<PersonBrief>>() {
            @Override
            public void call(Subscriber<? super List<PersonBrief>> subscriber) {
                subscriber.onNext(AccountModel.getInstance().createVirtualPersonBriefs(20));
                subscriber.onCompleted();
            }
        }).compose(new DefaultTransform<>());
    }

    public Observable<Object> attention(int id){
        return ServiceClient.getService().attend(id).compose(new DefaultTransform<>());
    }

    public Observable<Object> unAttention(int id){
        return ServiceClient.getService().unAttend(id).compose(new DefaultTransform<>());
    }

    public Observable<PersonDetail> getUserDetail(int uid){
        return ServiceClient.getService().getUserInfo(uid).compose(new DefaultTransform<>());
    }
}
