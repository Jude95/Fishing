package com.jude.fishing.model.service;


import com.jude.fishing.config.API;
import com.jude.fishing.model.entities.PlaceBrief;
import com.jude.fishing.model.entities.Token;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by Mr.Jude on 2015/8/23.
 * 服务器接口
 */
public interface Service {

    @FormUrlEncoded
    @POST(API.URL.QiNiuToken)
    Observable<Token> getQiNiuToken();

    @FormUrlEncoded
    @POST(API.URL.GetPlace)
    Observable<PlaceBrief[]> SyncPlace(
            @Field("time")String lastTime);

}
