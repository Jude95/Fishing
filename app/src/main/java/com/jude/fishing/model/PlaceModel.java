package com.jude.fishing.model;

import com.jude.beam.model.AbsModel;
import com.jude.fishing.model.bean.PlaceBrief;
import com.jude.fishing.model.service.DefaultTransform;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by Mr.Jude on 2015/9/12.
 */
public class PlaceModel extends AbsModel {

        public static PlaceModel getInstance() {
                return getInstance(PlaceModel.class);
        }
    public Observable<PlaceBrief[]> getPlaces(int page){
        return Observable.just(createVirtualPlace()).delay(1, TimeUnit.SECONDS).compose(new DefaultTransform<>());
    }

    PlaceBrief[] createVirtualPlace(){
        return new PlaceBrief[]{

                new PlaceBrief(
                      "0","南山鱼塘","http://img5.imgtn.bdimg.com/it/u=2219957519,4104610372&fm=21&gp=0.jpg","南山",
                      3.8f,5280,1,"沼跃鱼",1,new int[]{0,1}
                ),
                new PlaceBrief(
                        "0","火山湖","http://img1.imgtn.bdimg.com/it/u=930950042,3109091123&fm=21&gp=0.jpg","大山深处",
                        3.0f,9280,1,"沼跃鱼",2,new int[]{0}
                ),
                new PlaceBrief(
                        "0","柯撸客湖","http://img5.imgtn.bdimg.com/it/u=592041356,2520444501&fm=21&gp=0.jpg","大麦哲伦星系的哈拉",
                        3.5f,9280,1,"沼跃鱼",3,new int[]{1}
                ),
                new PlaceBrief(
                        "0","特卡坡湖","http://img5.imgtn.bdimg.com/it/u=1214226233,2678852478&fm=21&gp=0.jpg","特卡坡山脚",
                        4.0f,9280,1,"沼跃鱼",0,new int[]{1,2}
                ),
                new PlaceBrief(
                        "0","加尔达湖","http://img4.imgtn.bdimg.com/it/u=459713766,2900261008&fm=21&gp=0.jpg","威尼斯和米兰的半途之间",
                        5.0f,9280,1,"沼跃鱼",1,new int[]{0}
                ),
                new PlaceBrief(
                        "0","南山鱼塘","http://img5.imgtn.bdimg.com/it/u=2219957519,4104610372&fm=21&gp=0.jpg","南山",
                        3.8f,5280,1,"沼跃鱼",1,new int[]{0,1}
                ),
                new PlaceBrief(
                        "0","火山湖","http://img1.imgtn.bdimg.com/it/u=930950042,3109091123&fm=21&gp=0.jpg","大山深处",
                        3.0f,9280,1,"沼跃鱼",2,new int[]{0}
                ),
                new PlaceBrief(
                        "0","柯撸客湖","http://img5.imgtn.bdimg.com/it/u=592041356,2520444501&fm=21&gp=0.jpg","世界尽头",
                        3.5f,9280,1,"沼跃鱼",3,new int[]{1}
                ),
                new PlaceBrief(
                        "0","特卡坡湖","http://img5.imgtn.bdimg.com/it/u=1214226233,2678852478&fm=21&gp=0.jpg","特卡坡山脚",
                        4.0f,9280,1,"沼跃鱼",0,new int[]{1,2}
                ),
                new PlaceBrief(
                        "0","加尔达湖","http://img4.imgtn.bdimg.com/it/u=459713766,2900261008&fm=21&gp=0.jpg","圣域",
                        5.0f,9280,1,"沼跃鱼",1,new int[]{0}
                ),
        };
    }
}
