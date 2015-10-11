package com.jude.fishing.model.service;

import retrofit.RequestInterceptor;

/**
 * Created by zhuchenxi on 15/10/11.
 */
public class HeaderInterceptors implements RequestInterceptor {
    public static String TOKEN = "";
    public static String UID = "";

    @Override
    public void intercept(RequestFacade request) {
        request.addHeader("uid", UID);
        request.addHeader("token", TOKEN);
    }
}
