package com.jude.fishing.model;

import android.content.Context;

import com.google.gson.Gson;
import com.jude.beam.model.AbsModel;
import com.jude.fishing.model.db.DBConfig;
import com.jude.fishing.model.db.DBHelper;
import com.jude.fishing.model.db.PlaceDBTable;
import com.jude.fishing.model.entities.Evaluate;
import com.jude.fishing.model.entities.EvaluateComment;
import com.jude.fishing.model.entities.EvaluateDetail;
import com.jude.fishing.model.entities.PlaceBrief;
import com.jude.fishing.model.entities.PlaceDetail;
import com.jude.fishing.model.service.DefaultTransform;
import com.jude.fishing.model.service.ServiceClient;
import com.jude.utils.JUtils;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by Mr.Jude on 2015/9/12.
 */
public class PlaceModel extends AbsModel {
    public static final String PLACE_LAST_SYNC_TIME = "placeLastSyncTime";

    private BriteDatabase mDbBrite;
    public static PlaceModel getInstance() {
            return getInstance(PlaceModel.class);
    }

    @Override
    protected void onAppCreate(Context ctx) {
        SqlBrite mSqlBrite = SqlBrite.create(s -> JUtils.Log("DB",s));
        mDbBrite = mSqlBrite.wrapDatabaseHelper(new DBHelper(ctx));
    }

    @Override
    protected void onAppCreateOnBackThread(Context ctx) {
        syncPlace();
    }

    public Observable<List<PlaceBrief>> getAllPlaces(){
        return mDbBrite.createQuery(PlaceDBTable.TABLE_NAME,
                "SELECT * FROM " + PlaceDBTable.TABLE_NAME)
                .mapToList(DBConfig.PLACE_DB_TABLE::from);
    }

    public Observable<List<PlaceBrief>> getTestPlaces(double lat, double lng){
        return Observable.just(createVirtualPlaces(10));
    }

    public Observable<List<PlaceBrief>> getPlacesByDistance(double lat, double lng){
        return mDbBrite.createQuery(PlaceDBTable.TABLE_NAME,
                "SELECT *  FROM " + PlaceDBTable.TABLE_NAME
                        + " ORDER BY ((lat - " + lat + ")*(lat - " + lat + ")+(lng - " + lng + ")*(lng - " + lng + "))")
                .mapToList(DBConfig.PLACE_DB_TABLE::from)
                .first();
    }

    public Observable<List<PlaceBrief>> updatePlacesByDistance(double lat, double lng){
        return syncPlace().flatMap(placeBriefs -> getPlacesByDistance(lat,lng)).compose(new DefaultTransform<>());
    }

    public Observable<Object> publishPlace(PlaceDetail placeDetail){
        String picture  = new Gson().toJson(placeDetail.getPicture());
        return ServiceClient.getService().PublishPlace(
                placeDetail.getId(),
                placeDetail.getName(),
                placeDetail.getPreview(),
                placeDetail.getBriefAddr(),
                placeDetail.getAddress(),
                placeDetail.getCost(),
                placeDetail.getCostType(),
                placeDetail.getFishType(),
                placeDetail.getPoolType(),
                placeDetail.getServiceType(),
                placeDetail.getTel(),
                placeDetail.getContent(),
                picture,
                placeDetail.getLat(),
                placeDetail.getLng()).compose(new DefaultTransform<>());
    }

    public Observable<EvaluateDetail> getEvaluateDetail(int id){
        return Observable.just(createVirtualEvaluateDetail());
    }

    public Observable<List<PlaceBrief>> syncPlace(){
        return ServiceClient.getService().SyncPlace(JUtils.getSharedPreference().getString(PLACE_LAST_SYNC_TIME, "0"))
                .doOnCompleted(() -> JUtils.getSharedPreference().edit().putString(PLACE_LAST_SYNC_TIME, System.currentTimeMillis() / 1000 + "").apply())
                .doOnNext(placeBriefs -> {
                    BriteDatabase.Transaction transaction = mDbBrite.newTransaction();
                    for (PlaceBrief placeBrief : placeBriefs) {
                        try {
                            mDbBrite.insert(PlaceDBTable.TABLE_NAME, DBConfig.PLACE_DB_TABLE.to(placeBrief));
                            JUtils.Log("DB", "inserted:" + placeBrief.getName());
                        } catch (Exception e) {
                            try {
                                mDbBrite.update(PlaceDBTable.TABLE_NAME, DBConfig.PLACE_DB_TABLE.to(placeBrief), PlaceDBTable.COLUMN_ID + "=" + placeBrief.getId());
                                JUtils.Log("DB", "updated" + placeBrief.getName());
                            } catch (Exception e1) {
                                JUtils.Log("DB", "ERROR:" + e1.getLocalizedMessage());
                            }
                        }
                    }
                    transaction.markSuccessful();
                    transaction.end();
                });
    }

    public Observable<List<PlaceBrief>> getUserPlaces(){
        return Observable.just(createVirtualPlaces(10)).delay(500, TimeUnit.MILLISECONDS).compose(new DefaultTransform<>());
    }

    public Observable<List<PlaceBrief>> getCollectionPlaces(int id){
            return Observable.just(createVirtualPlaces(10)).delay(500, TimeUnit.MILLISECONDS).compose(new DefaultTransform<>());
    }

    public Observable<List<Evaluate>> getPlacesComments(int id,int page){
        return Observable.just(createVirtualComment(10)).delay(500, TimeUnit.MILLISECONDS).compose(new DefaultTransform<>());
    }

    public Observable<List<Evaluate>> getUserPlacesComments(int id){
        return Observable.just(createVirtualComment(10)).delay(500, TimeUnit.MILLISECONDS).compose(new DefaultTransform<>());
    }

    public Observable<PlaceDetail> getPlaceDetail(int id){
        return Observable.just(createVirtualPlaceDetail()).delay(500, TimeUnit.MILLISECONDS).compose(new DefaultTransform<>());
    }


    PlaceDetail createVirtualPlaceDetail(){
        return new PlaceDetail(0,"太阳是已知的天然东西里最圆的东西","http://upload-images.jianshu.io/upload_images/459359-2af4feed8dc0896b.jpg?imageMogr2/auto-orient/strip%7CimageView2/1/w/300/h/300",
        "加利福尼亚州山景城",4.8f,125,1,"沼跃鱼",2,"1,2",106.607208,29.533587,"023-45696542",
                "认识她的时候，她是有男友的，还是我哥们儿。不过自从她男友劈腿两人分手之后，她一直没精神。她往日里生活的精致，在这儿一年中，都跟着她的前男友一起离开她了。我安慰过她几次，却也无能为力。毕竟这样的事儿，还是得靠她自己。旁人能做的也就只有鼓励一二，了表心意。\n" +
                        "\n" +
                        "而今天她终于可以振作起来了，我一定得去啊！我走进夜晚里，一路上又生出疑惑，今天觉得她没什么异样啊，怎么突然就找到真爱了？",
                Arrays.asList(
                   "http://img0.pconline.com.cn/pconline/1503/04/6177579_01_thumb.jpg",
                        "http://img0.pconline.com.cn/pconline/1503/04/6177579_02_thumb.jpg",
                        "http://img0.pconline.com.cn/pconline/1503/04/6177579_03_thumb.jpg",
                        "http://img0.pconline.com.cn/pconline/1503/04/6177579_04_thumb.jpg",
                        "http://img0.pconline.com.cn/pconline/1503/04/6177579_05_thumb.jpg",
                        "http://img0.pconline.com.cn/pconline/1503/04/6177579_06_thumb.jpg",
                        "http://img0.pconline.com.cn/pconline/1503/04/6177579_07_thumb.jpg"
                ));
    }

    EvaluateDetail createVirtualEvaluateDetail(){
        return new EvaluateDetail("南坪","http://i1.hdslb.com/account/face/674913/bd533fd1/myface.png",1,"盛夏的青皮橘",4,
                "是这样的，“刘慈欣文笔不好”这个说法，最开始是一些写作方面的高手大家提出来的，但是他们说的是“相对欠缺”，因为相对于三体在构思和技术内核上的绚烂，大刘在人物塑造方面确实是略显失色，遣词造句方面也弱但比人物塑造强。人家说的是一个桶有长板有短板，但总体来说还是一个很牛的桶。",
                2,new String[]{
                "http://imgsrc.baidu.com/forum/w%3D580/sign=13888c7b8e1001e94e3c1407880f7b06/ea6f0d24ab18972b6443c558e5cd7b899f510aa4.jpg",
                "http://imgsrc.baidu.com/forum/w%3D580/sign=34ca51e6ae345982c58ae59a3cf5310b/bdeda0c27d1ed21bade3eb77ae6eddc450da3ff7.jpg",
                "http://imgsrc.baidu.com/forum/w%3D580/sign=031c6af24cc2d562f208d0e5d71090f3/78dedd33c895d143176f20b170f082025baf0784.jpg",
                "http://imgsrc.baidu.com/forum/w%3D580/sign=8efac50a5ddf8db1bc2e7c6c3922dddb/f5851a178a82b901980a07a1708da9773812ef73.jpg",
                "http://imgsrc.baidu.com/forum/w%3D580/sign=4b49604cf31f3a295ac8d5c6a924bce3/95ba7d310a55b3192cd943e740a98226cefc17fd.jpg",
                "http://imgsrc.baidu.com/forum/w%3D580/sign=83ee50607e1ed21b79c92eed9d6fddae/6f82b6fb43166d224b30d406452309f79152d252.jpg"
        },6,"亚特兰大","https://pic3.zhimg.com/33025a18d9ffa28fff4a85762dea8ff2_xld.png",4,1143861855, Arrays.asList(
                        createVirtualEvaluateComment(0, 1).setChild(new EvaluateComment[]{
                                createVirtualEvaluateComment(1, 4).setChild(new EvaluateComment[]{
                                        createVirtualEvaluateComment(4, 5).setChild(new EvaluateComment[]{
                                                createVirtualEvaluateComment(5, 6)
                                        }),
                                }),
                        }),
                        createVirtualEvaluateComment(0, 2).setChild(new EvaluateComment[]{
                                createVirtualEvaluateComment(2, 7)
                        }),
                        createVirtualEvaluateComment(0, 3)
                ));
    }

    EvaluateComment createVirtualEvaluateComment(int originalId, int id){
        return new EvaluateComment("http://i2.hdslb.com/account/face/8762083/8120757f/myface.png",2,"西瓜的味",id,originalId,1443342840,"问世间鬼畜为何物");
    }
    
    List<Evaluate> createVirtualComment(int count){
        List<Evaluate> comments = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            comments.add(new Evaluate("南坪","http://i1.hdslb.com/account/face/674913/bd533fd1/myface.png",1,"盛夏的青皮橘",4,
                    "是这样的，“刘慈欣文笔不好”这个说法，最开始是一些写作方面的高手大家提出来的，但是他们说的是“相对欠缺”，因为相对于三体在构思和技术内核上的绚烂，大刘在人物塑造方面确实是略显失色，遣词造句方面也弱但比人物塑造强。人家说的是一个桶有长板有短板，但总体来说还是一个很牛的桶。",
                    2,new String[]{
                    "http://imgsrc.baidu.com/forum/w%3D580/sign=13888c7b8e1001e94e3c1407880f7b06/ea6f0d24ab18972b6443c558e5cd7b899f510aa4.jpg",
                    "http://imgsrc.baidu.com/forum/w%3D580/sign=34ca51e6ae345982c58ae59a3cf5310b/bdeda0c27d1ed21bade3eb77ae6eddc450da3ff7.jpg",
                    "http://imgsrc.baidu.com/forum/w%3D580/sign=031c6af24cc2d562f208d0e5d71090f3/78dedd33c895d143176f20b170f082025baf0784.jpg",
                    "http://imgsrc.baidu.com/forum/w%3D580/sign=8efac50a5ddf8db1bc2e7c6c3922dddb/f5851a178a82b901980a07a1708da9773812ef73.jpg",
                    "http://imgsrc.baidu.com/forum/w%3D580/sign=4b49604cf31f3a295ac8d5c6a924bce3/95ba7d310a55b3192cd943e740a98226cefc17fd.jpg",
                    "http://imgsrc.baidu.com/forum/w%3D580/sign=83ee50607e1ed21b79c92eed9d6fddae/6f82b6fb43166d224b30d406452309f79152d252.jpg"
            },6,"亚特兰大","https://pic3.zhimg.com/33025a18d9ffa28fff4a85762dea8ff2_xld.png",4,1143861855));
        }
        return comments;
    }

    List<PlaceBrief> createVirtualPlaces(int count){
        double lat = LocationModel.getInstance().getCurLocation().getLatitude();
        double lng = LocationModel.getInstance().getCurLocation().getLongitude();
        List<PlaceBrief> placeBriefs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            placeBriefs.add(new PlaceBrief(
                    i,"南山鱼塘","http://img5.imgtn.bdimg.com/it/u=2219957519,4104610372&fm=21&gp=0.jpg","南山","南山",
                    3.8f, (int) (Math.random()*500), (int) (Math.random()*2),"沼跃鱼",1,"0,1",lat+(Math.random()-0.5)*10,lng+(Math.random()-0.5)*10
            ));
        }
        return placeBriefs;
    }
}
