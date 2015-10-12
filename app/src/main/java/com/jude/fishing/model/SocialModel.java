package com.jude.fishing.model;

import com.jude.beam.model.AbsModel;
import com.jude.fishing.model.entities.PersonBrief;
import com.jude.fishing.model.entities.PersonDetail;
import com.jude.fishing.model.service.DefaultTransform;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public Observable attention(int id){
        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        }).delay(500, TimeUnit.MILLISECONDS).compose(new DefaultTransform<>());
    }

    public Observable unAttention(int id){
        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        }).delay(500, TimeUnit.MILLISECONDS).compose(new DefaultTransform<>());
    }

    public Observable<PersonDetail> getUserDetail(int id){
        return Observable.create(new Observable.OnSubscribe<PersonDetail>() {
            @Override
            public void call(Subscriber<? super PersonDetail> subscriber) {
                subscriber.onNext(AccountModel.getInstance().createVirtualAccount());
                subscriber.onCompleted();
            }
        }).delay(500, TimeUnit.MILLISECONDS).compose(new DefaultTransform<>());
    }
}
