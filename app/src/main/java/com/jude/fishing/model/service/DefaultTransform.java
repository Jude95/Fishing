package com.jude.fishing.model.service;

import com.jude.utils.JUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Mr.Jude on 2015/8/25.
 * 对服务器请求的Observer的修改
 */
public class DefaultTransform<T> implements Observable.Transformer<T, T> {
    @Override
    public Observable<T> call(Observable<T> tObservable) {
        return tObservable
                .doOnError(throwable -> JUtils.Log(throwable.getLocalizedMessage()))
                .observeOn(AndroidSchedulers.mainThread());
    }
}
