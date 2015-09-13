package com.jude.fishing.model;

import com.jude.beam.model.AbsModel;
import com.jude.fishing.model.bean.Seed;
import com.jude.fishing.model.service.DefaultTransform;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by Mr.Jude on 2015/9/12.
 */
public class BBSModel extends AbsModel {
    public static BBSModel getInstance() {
        return getInstance(BBSModel.class);
    }

    public Observable<Seed[]> getSeed(int type,int page){
        return Observable.just(createVirtualSeed()).delay(1, TimeUnit.SECONDS).compose(new DefaultTransform<>());
    }

    Seed[] createVirtualSeed(){
        return new Seed[]{
            new Seed("0","0","https://ss0.baidu.com/73t1bjeh1BF3odCf/it/u=4127439902,384671097&fm=96&s=7880DD194A1143CCDD9C89D5030080E3",
                    "Jude",1442044921,"重庆邮电大学","由于谷歌服务一直无缘进入中国市场，手机Rom在中国大地上形成了非常奇特的格局：各家诸侯都基于谷歌Android操作系统开发自家Rom，并且在Rom的基础上衍生出自家云服务、应..",
                    new String[]{
                            "http://img3.imgtn.bdimg.com/it/u=2491771891,3406402092&fm=21&gp=0.jpg",
                            "http://img4.imgtn.bdimg.com/it/u=2361635210,218755406&fm=21&gp=0.jpg",
                            "http://img0.imgtn.bdimg.com/it/u=3706407193,2012748231&fm=21&gp=0.jpg"
                    },5,false,0,null),
                new Seed("0","0","https://ss0.baidu.com/73t1bjeh1BF3odCf/it/u=4127439902,384671097&fm=96&s=7880DD194A1143CCDD9C89D5030080E3",
                        "Jude",1442044921,"重庆邮电大学","由于谷歌服务一直无缘进入中国市场，手机Rom在中国大地上形成了非常奇特的格局：各家诸侯都基于谷歌Android操作系统开发自家Rom，并且在Rom的基础上衍生出自家云服务、应..",
                        new String[]{
                                "http://img3.imgtn.bdimg.com/it/u=2491771891,3406402092&fm=21&gp=0.jpg",
                                "http://img4.imgtn.bdimg.com/it/u=2361635210,218755406&fm=21&gp=0.jpg",
                                "http://img0.imgtn.bdimg.com/it/u=3706407193,2012748231&fm=21&gp=0.jpg"
                        },5,false,0,null),
                new Seed("0","0","https://ss0.baidu.com/73t1bjeh1BF3odCf/it/u=4127439902,384671097&fm=96&s=7880DD194A1143CCDD9C89D5030080E3",
                        "Jude",1442044921,"重庆邮电大学","由于谷歌服务一直无缘进入中国市场，手机Rom在中国大地上形成了非常奇特的格局：各家诸侯都基于谷歌Android操作系统开发自家Rom，并且在Rom的基础上衍生出自家云服务、应..",
                        new String[]{
                                "http://img3.imgtn.bdimg.com/it/u=2491771891,3406402092&fm=21&gp=0.jpg",
                                "http://img4.imgtn.bdimg.com/it/u=2361635210,218755406&fm=21&gp=0.jpg",
                                "http://img0.imgtn.bdimg.com/it/u=3706407193,2012748231&fm=21&gp=0.jpg"
                        },5,false,0,null),
                new Seed("0","0","https://ss0.baidu.com/73t1bjeh1BF3odCf/it/u=4127439902,384671097&fm=96&s=7880DD194A1143CCDD9C89D5030080E3",
                        "Jude",1442044921,"重庆邮电大学","由于谷歌服务一直无缘进入中国市场，手机Rom在中国大地上形成了非常奇特的格局：各家诸侯都基于谷歌Android操作系统开发自家Rom，并且在Rom的基础上衍生出自家云服务、应..",
                        new String[]{
                                "http://img3.imgtn.bdimg.com/it/u=2491771891,3406402092&fm=21&gp=0.jpg",
                                "http://img4.imgtn.bdimg.com/it/u=2361635210,218755406&fm=21&gp=0.jpg",
                                "http://img0.imgtn.bdimg.com/it/u=3706407193,2012748231&fm=21&gp=0.jpg"
                        },5,false,0,null),
                new Seed("0","0","https://ss0.baidu.com/73t1bjeh1BF3odCf/it/u=4127439902,384671097&fm=96&s=7880DD194A1143CCDD9C89D5030080E3",
                        "Jude",1442044921,"重庆邮电大学","由于谷歌服务一直无缘进入中国市场，手机Rom在中国大地上形成了非常奇特的格局：各家诸侯都基于谷歌Android操作系统开发自家Rom，并且在Rom的基础上衍生出自家云服务、应..",
                        new String[]{
                                "http://img3.imgtn.bdimg.com/it/u=2491771891,3406402092&fm=21&gp=0.jpg",
                                "http://img4.imgtn.bdimg.com/it/u=2361635210,218755406&fm=21&gp=0.jpg",
                                "http://img0.imgtn.bdimg.com/it/u=3706407193,2012748231&fm=21&gp=0.jpg"
                        },5,false,0,null),

        };
    }
}
