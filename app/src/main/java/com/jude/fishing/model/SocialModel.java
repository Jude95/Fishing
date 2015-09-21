package com.jude.fishing.model;

import com.jude.beam.model.AbsModel;
import com.jude.fishing.model.bean.PersonBrief;
import com.jude.fishing.model.service.DefaultTransform;

import rx.Observable;
import rx.Subscriber;


/**
 * Created by zhuchenxi on 15/9/20.
 */
public class SocialModel extends AbsModel {

    public static SocialModel getInstance() {
        return getInstance(SocialModel.class);
    }

    public Observable<PersonBrief[]> getAttentions(int uid){
        return Observable.create(new Observable.OnSubscribe<PersonBrief[]>() {
            @Override
            public void call(Subscriber<? super PersonBrief[]> subscriber) {
                subscriber.onNext(AccountModel.getInstance().creatVirtualPersonBriefs(20));
                subscriber.onCompleted();
            }
        }).compose(new DefaultTransform<>());
    }

    public Observable<PersonBrief[]> getFans(int uid){
        return Observable.create(new Observable.OnSubscribe<PersonBrief[]>() {
            @Override
            public void call(Subscriber<? super PersonBrief[]> subscriber) {
                subscriber.onNext(AccountModel.getInstance().creatVirtualPersonBriefs(20));
                subscriber.onCompleted();
            }
        }).compose(new DefaultTransform<>());
    }
}
