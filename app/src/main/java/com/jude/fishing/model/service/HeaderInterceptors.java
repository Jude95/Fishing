package com.jude.fishing.model.service;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Mr.Jude on 2015/10/8.
 */
public class HeaderInterceptors implements Interceptor {
    public static String TOKEN = "";
    public static String UID = "";

    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request().newBuilder()
                .addHeader("token", TOKEN)
                .addHeader("uid",UID)
                .addHeader("type","android")
                .build());
    }
}
