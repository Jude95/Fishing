package com.jude.fishing.model.service;

import com.jude.fishing.config.API;
import com.jude.utils.JUtils;

import retrofit.converter.ConversionException;
import rx.Observer;

/**
 * Created by Mr.Jude on 2015/8/24.
 * 服务器返回的回调
 */
public abstract class ServiceResponse<T> implements Observer<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        JUtils.Log("Error: "+e.getLocalizedMessage()+":"+ServiceException.class.getName());
        if (e.getCause() instanceof ServiceException){
            onServiceError(((ServiceException) e.getCause()).getStatus(), ((ServiceException) e.getCause()).getInfo());
        }else if (e.getCause() instanceof ConversionException){
            onServiceError(API.CODE.ANALYSIS_ERROR,"数据解析错误");
        }else{
            onServiceError(API.CODE.NET_INVALID,"网络错误");
        }
    }

    public void onServiceError(int status,String info){
        JUtils.Log("server error:"+status+" ,info:"+info);
        JUtils.Toast(info);
    }

}
