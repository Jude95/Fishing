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

    @FormUrlEncoded
    @POST(API.URL.GetPlace)
    Observable<PlaceBrief[]> PublishPlace(
            @Field("id")int id,
            @Field("name")String name,
            @Field("preview")String preview,
            @Field("address")String address,
            @Field("cost")int cost,
            @Field("costType")int costType,
            @Field("fishType")String fishType,
            @Field("poolType")int poolType,
            @Field("serviceType")String serviceType,
            @Field("tel")String tel,
            @Field("content")String content,
            @Field("picture")String[] picture,
            @Field("lat")double lat,
            @Field("lng")double lng
            );
}
