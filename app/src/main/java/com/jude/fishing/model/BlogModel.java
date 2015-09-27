package com.jude.fishing.model;

import com.jude.beam.model.AbsModel;
import com.jude.fishing.model.bean.Seed;
import com.jude.fishing.model.bean.SeedComment;
import com.jude.fishing.model.bean.SeedDetail;
import com.jude.fishing.model.service.DefaultTransform;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by Mr.Jude on 2015/9/12.
 */
public class BlogModel extends AbsModel {
    public static BlogModel getInstance() {
        return getInstance(BlogModel.class);
    }

    public Observable<Seed[]> getSeed(int type,int page){
        return Observable.just(createVirtualSeedList(20)).delay(500, TimeUnit.MILLISECONDS).compose(new DefaultTransform<>());
    }

    public Observable<SeedDetail> getSeedDetail(int id){
        return Observable.just(createVirtualSeedDetail()).delay(500, TimeUnit.MILLISECONDS).compose(new DefaultTransform<>());
    }

    Seed[] createVirtualSeedList(int count){
        Seed[] seeds = new Seed[count];
        for (int i = 0; i < seeds.length; i++) {
            seeds[i] = new Seed(0,0,"https://ss0.baidu.com/73t1bjeh1BF3odCf/it/u=4127439902,384671097&fm=96&s=7880DD194A1143CCDD9C89D5030080E3",
                    "Jude",1442044921,"重庆邮电大学","由于谷歌服务一直无缘进入中国市场，手机Rom在中国大地上形成了非常奇特的格局：各家诸侯都基于谷歌Android操作系统开发自家Rom，并且在Rom的基础上衍生出自家云服务、应..",
                    new String[]{
                            "http://img3.imgtn.bdimg.com/it/u=2491771891,3406402092&fm=21&gp=0.jpg",
                            "http://img4.imgtn.bdimg.com/it/u=2361635210,218755406&fm=21&gp=0.jpg",
                            "http://img0.imgtn.bdimg.com/it/u=3706407193,2012748231&fm=21&gp=0.jpg"
                    },5,false,0);
        }

        return seeds;
    }

    SeedDetail createVirtualSeedDetail(){
        return new SeedDetail(11,1,"http://i1.hdslb.com/52_52/account/face/25263/2526354/myface.jpg","天然呆的陌言君"
        ,1443341930,"Texas","秉持着“约会永远不迟到”理念的她还保持着七年以前的好传统，早早就提前到了咖啡馆占据了一个比较角落又靠近窗的有利位置，一边刷quora一边等我们来。"
        ,new String[]{
                "http://3.im.guokr.com/QAePH2iy3thGQG2SVHUFKcInsyM_NDUh_oOjSZbU5NlKAQAA6wAAAEpQ.jpg",
                "http://3.im.guokr.com/U0za2RLkUXBOSrWLjqOi-nlj8JkoIJ9NVss-_lynXARKAQAA7wAAAEpQ.jpg",
                "http://1.im.guokr.com/zTzlLLjAX9VQJ6WqrqW98XxC6yXmxZfB3sqT3sQniJ5KAQAA6wAAAEpQ.jpg",
                "http://3.im.guokr.com/UhUc9bPm94qfdGwrGaSjXgJVKUolNnjId-mb1Ax1LhO8CwAA0wcAAEpQ.jpg?imageView2/1/w/592/h/395",
                "http://2.im.guokr.com/XTEhHiOrc3qF-GyLUYBj-7ladymw3y1WITf5iC9ofTe4AQAAjwIAAEpQ.jpg"
        } ,8,true,12,AccountModel.getInstance().createVirtualPersonBriefs(6),
                new SeedComment[]{
                        createVirtualSeedComment(0, 1).setChild(new SeedComment[]{
                                createVirtualSeedComment(1, 4).setChild(new SeedComment[]{
                                        createVirtualSeedComment(4, 5).setChild(new SeedComment[]{
                                                createVirtualSeedComment(5, 6)
                                        }),
                                }),
                        }),
                        createVirtualSeedComment(0, 2).setChild(new SeedComment[]{
                                createVirtualSeedComment(2, 7)
                        }),
                        createVirtualSeedComment(0, 3),
                });
    }

    SeedComment createVirtualSeedComment(int originalId, int id){
        return new SeedComment("http://i2.hdslb.com/52_52/account/face/13459168/22f9a964/myface.png",2,"天蓝色的樱花",id,originalId,1443342840,"硬币投了，前来留名");
    }
}
