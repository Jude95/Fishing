package com.jude.fishing.model;

import android.accounts.NetworkErrorException;

import com.jude.beam.model.AbsModel;
import com.jude.fishing.config.API;
import com.jude.fishing.model.service.DefaultTransform;
import com.jude.fishing.model.service.ServiceClient;
import com.jude.utils.JUtils;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by Mr.Jude on 2015/9/12.
 */
public class CommonModel extends AbsModel {
    public static CommonModel getInstance() {
        return getInstance(CommonModel.class);
    }

    public Observable<String> getUpdateLog(){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Request request = new Request.Builder()
                        .url(API.URL.UpdateLog)
                        .build();
                try {
                    Response response = ServiceClient.getOkHttpClient().newCall(request).execute();
                    subscriber.onNext(response.body().string());
                } catch (Exception e) {
                    subscriber.onError(new NetworkErrorException());
                }
            }
        }).subscribeOn(Schedulers.newThread()).compose(new DefaultTransform<>());
    }
}
