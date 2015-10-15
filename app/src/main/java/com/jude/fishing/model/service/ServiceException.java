package com.jude.fishing.model.service;

import retrofit.converter.ConversionException;

/**
 * Created by Mr.Jude on 2015/8/24.
 * 服务器返回错误的错误类型
 */
public class ServiceException extends ConversionException {
    private int status;
    private String info;

    public int getStatus() {
        return status;
    }

    public String getInfo() {
        return info;
    }

    public ServiceException(int status, String info) {
        super(info);
        this.status = status;
        this.info = info;
    }
}
