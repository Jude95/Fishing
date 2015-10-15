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
        if (e.getLocalizedMessage().equals(ServiceException.class.getName())){
            onServiceError(((ServiceException)e).getStatus(),((ServiceException) e).getInfo());
        }else if (e.getLocalizedMessage().equals(ConversionException.class.getName())){
            onServiceError(API.CODE.ANALYSIS_ERROR,"数据解析错误");
        }else{
            onServiceError(API.CODE.NET_INVALID,"网络错误");
        }
    }

    public void onServiceError(int status,String info){
        JUtils.Toast(info);
    }

}
