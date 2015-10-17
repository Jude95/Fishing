package com.jude.fishing.model.service;

import com.jude.fishing.config.API;
import com.jude.utils.JUtils;

import rx.Observer;

/**
 * Created by Mr.Jude on 2015/8/24.
 * 服务器返回的回调
 */
public abstract class ServiceResponse<T> implements Observer<T> {

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (e.getCause() instanceof ServiceException){
            JUtils.Log("Server Error:"+e.getLocalizedMessage());
            onServiceError(((ServiceException) e.getCause()).getStatus(), ((ServiceException) e.getCause()).getInfo());
        }else{
            JUtils.Log("UnKnow Error:"+e.getLocalizedMessage());
            onServiceError(API.CODE.NET_INVALID, "网络错误");
        }
    }

    public void onServiceError(int status,String info){
        JUtils.Toast(info);
    }

}
