package com.jude.fishing.model;

import com.jude.beam.model.AbsModel;
import com.jude.fishing.model.bean.PlaceComment;
import com.jude.fishing.model.bean.PlaceBrief;
import com.jude.fishing.model.bean.PlaceDetail;
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
        return Observable.just(createVirtualPlace()).delay(500, TimeUnit.MILLISECONDS).compose(new DefaultTransform<>());
    }


    public Observable<PlaceBrief[]> getUserPlaces(){
        return Observable.just(createVirtualPlace()).delay(500, TimeUnit.MILLISECONDS).compose(new DefaultTransform<>());
    }

    public Observable<PlaceBrief[]> getCollectionPlaces(int id){
            return Observable.just(createVirtualPlace()).delay(500, TimeUnit.MILLISECONDS).compose(new DefaultTransform<>());
    }

    public Observable<PlaceComment[]> getUserPlacesComments(int id){
        return Observable.just(createVirtualComment(10)).delay(500, TimeUnit.MILLISECONDS).compose(new DefaultTransform<>());
    }

    public Observable<PlaceDetail> getPlaceDetail(int id){
        return Observable.just(createVirtualPlaceDetail()).delay(500, TimeUnit.MILLISECONDS).compose(new DefaultTransform<>());
    }


    PlaceDetail createVirtualPlaceDetail(){
        return new PlaceDetail(0,"太阳是已知的天然东西里最圆的东西","http://upload-images.jianshu.io/upload_images/459359-2af4feed8dc0896b.jpg?imageMogr2/auto-orient/strip%7CimageView2/1/w/300/h/300",
        "加利福尼亚州山景城",4.8f,12580000,1,"沼跃鱼",2,new int[]{0,1,2},"023-45696542",
                "认识她的时候，她是有男友的，还是我哥们儿。不过自从她男友劈腿两人分手之后，她一直没精神。她往日里生活的精致，在这儿一年中，都跟着她的前男友一起离开她了。我安慰过她几次，却也无能为力。毕竟这样的事儿，还是得靠她自己。旁人能做的也就只有鼓励一二，了表心意。\n" +
                        "\n" +
                        "而今天她终于可以振作起来了，我一定得去啊！我走进夜晚里，一路上又生出疑惑，今天觉得她没什么异样啊，怎么突然就找到真爱了？",
                new String[]{
                   "http://img0.pconline.com.cn/pconline/1503/04/6177579_01_thumb.jpg",
                        "http://img0.pconline.com.cn/pconline/1503/04/6177579_02_thumb.jpg",
                        "http://img0.pconline.com.cn/pconline/1503/04/6177579_03_thumb.jpg",
                        "http://img0.pconline.com.cn/pconline/1503/04/6177579_04_thumb.jpg",
                        "http://img0.pconline.com.cn/pconline/1503/04/6177579_05_thumb.jpg",
                        "http://img0.pconline.com.cn/pconline/1503/04/6177579_06_thumb.jpg",
                        "http://img0.pconline.com.cn/pconline/1503/04/6177579_07_thumb.jpg"
                });
    }

    PlaceComment[] createVirtualComment(int count){
        PlaceComment[] comments = new PlaceComment[count];
        for (int i = 0; i < comments.length; i++) {
            comments[i] = new PlaceComment(i,"南山鱼塘","http://img2.imgtn.bdimg.com/it/u=2340511935,3141513885&fm=21&gp=0.jpg",4,"你现在还有心工作了吧？毕竟接下来这半个月的时间可基本上都是假期，身边的不少人都已经计划好要去哪里玩了。我在这里想说的是，如果你的行程中有下面这十个景点的话.",1442925906);
        }
        return comments;
    }


    PlaceBrief[] createVirtualPlace(){
        return new PlaceBrief[]{

                new PlaceBrief(
                      0,"南山鱼塘","http://img5.imgtn.bdimg.com/it/u=2219957519,4104610372&fm=21&gp=0.jpg","南山",
                      3.8f,5280,1,"沼跃鱼",1,new int[]{0,1}
                ),
                new PlaceBrief(
                        0,"火山湖","http://img1.imgtn.bdimg.com/it/u=930950042,3109091123&fm=21&gp=0.jpg","大山深处",
                        3.0f,9280,1,"沼跃鱼",2,new int[]{0}
                ),
                new PlaceBrief(
                        0,"柯撸客湖","http://img5.imgtn.bdimg.com/it/u=592041356,2520444501&fm=21&gp=0.jpg","大麦哲伦星系的哈拉",
                        3.5f,9280,1,"沼跃鱼",3,new int[]{1}
                ),
                new PlaceBrief(
                        0,"特卡坡湖","http://img5.imgtn.bdimg.com/it/u=1214226233,2678852478&fm=21&gp=0.jpg","特卡坡山脚",
                        4.0f,9280,1,"沼跃鱼",0,new int[]{1,2}
                ),
                new PlaceBrief(
                        0,"加尔达湖","http://img4.imgtn.bdimg.com/it/u=459713766,2900261008&fm=21&gp=0.jpg","威尼斯和米兰的半途之间",
                        5.0f,9280,1,"沼跃鱼",1,new int[]{0}
                ),
                new PlaceBrief(
                        0,"南山鱼塘","http://img5.imgtn.bdimg.com/it/u=2219957519,4104610372&fm=21&gp=0.jpg","南山",
                        3.8f,5280,1,"沼跃鱼",1,new int[]{0,1}
                ),
                new PlaceBrief(
                        0,"火山湖","http://img1.imgtn.bdimg.com/it/u=930950042,3109091123&fm=21&gp=0.jpg","大山深处",
                        3.0f,9280,1,"沼跃鱼",2,new int[]{0}
                ),
                new PlaceBrief(
                        0,"柯撸客湖","http://img5.imgtn.bdimg.com/it/u=592041356,2520444501&fm=21&gp=0.jpg","世界尽头",
                        3.5f,9280,1,"沼跃鱼",3,new int[]{1}
                ),
                new PlaceBrief(
                        0,"特卡坡湖","http://img5.imgtn.bdimg.com/it/u=1214226233,2678852478&fm=21&gp=0.jpg","特卡坡山脚",
                        4.0f,9280,1,"沼跃鱼",0,new int[]{1,2}
                ),
                new PlaceBrief(
                        0,"加尔达湖","http://img4.imgtn.bdimg.com/it/u=459713766,2900261008&fm=21&gp=0.jpg","圣域",
                        5.0f,9280,1,"沼跃鱼",1,new int[]{0}
                ),
        };
    }
}
