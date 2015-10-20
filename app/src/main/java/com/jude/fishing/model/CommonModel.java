package com.jude.fishing.model;

import com.jude.beam.model.AbsModel;
import com.jude.fishing.model.service.DefaultTransform;
import com.jude.fishing.model.service.ServiceClient;

import rx.Observable;

/**
 * Created by Mr.Jude on 2015/9/12.
 */
public class CommonModel extends AbsModel {
    public static CommonModel getInstance() {
        return getInstance(CommonModel.class);
    }

    public Observable<Object> feedback(String content){
        return ServiceClient.getService().feedback(content).compose(new DefaultTransform<>());
    }
}
