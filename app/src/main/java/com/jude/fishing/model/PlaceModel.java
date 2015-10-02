package com.jude.fishing.model;

import android.content.Context;
import android.database.Cursor;

import com.jude.beam.model.AbsModel;
import com.jude.fishing.model.db.DBConfig;
import com.jude.fishing.model.db.DBHelper;
import com.jude.fishing.model.db.PlaceDBTable;
import com.jude.fishing.model.entities.PlaceBrief;
import com.jude.fishing.model.entities.PlaceComment;
import com.jude.fishing.model.entities.PlaceDetail;
import com.jude.fishing.model.service.DefaultTransform;
import com.jude.utils.JUtils;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by Mr.Jude on 2015/9/12.
 */
public class PlaceModel extends AbsModel {
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

    public Observable<PlaceBrief> getAllPlaces(){
        return mDbBrite.createQuery(PlaceDBTable.TABLE_NAME, "SELECT * FROM " + PlaceDBTable.TABLE_NAME)
                .flatMap(query -> query.asRows(DBConfig.PLACE_DB_TABLE::from));
    }

    public Observable<PlaceBrief[]> getPlaces(double lat, double lng){
        JUtils.Log("lat:"+lat+"  lng:"+lng);
        return mDbBrite.createQuery(PlaceDBTable.TABLE_NAME,
                        "SELECT *  FROM "+PlaceDBTable.TABLE_NAME
                        //+ " ORDER BY ((lat - 29.823975)*(lat - 29.823975)+(lng - 107.064447)*(lng - 107.064447))"
        )
        .flatMap(query -> {
            ArrayList<PlaceBrief> arrayList = new ArrayList<>();
            Cursor cursor = query.run();
            while (cursor.moveToNext()){
                arrayList.add(DBConfig.PLACE_DB_TABLE.from(cursor));
            }
            Collections.sort(arrayList, (placeBrief, t1) -> (int) (JUtils.distance(lng,lat,placeBrief.getLng(),placeBrief.getLat())-JUtils.distance(lng,lat,t1.getLng(),t1.getLat())));
            return Observable.just(arrayList);
        }).map(placeBriefs -> placeBriefs.toArray(new PlaceBrief[placeBriefs.size()]));
    }


    public void syncPlace(){
        Observable.from(createVirtualPlaces(5)).subscribe(placeBrief -> mDbBrite.insert(PlaceDBTable.TABLE_NAME, DBConfig.PLACE_DB_TABLE.to(placeBrief)));
    }

    public Observable<PlaceBrief[]> getUserPlaces(){
        return Observable.just(createVirtualPlaces(10)).delay(500, TimeUnit.MILLISECONDS).compose(new DefaultTransform<>());
    }

    public Observable<PlaceBrief[]> getCollectionPlaces(int id){
            return Observable.just(createVirtualPlaces(10)).delay(500, TimeUnit.MILLISECONDS).compose(new DefaultTransform<>());
    }

    public Observable<PlaceComment[]> getUserPlacesComments(int id){
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

    PlaceBrief[] createVirtualPlaces(int count){
        double lat = LocationModel.getInstance().getCurLocation().getLatitude();
        double lng = LocationModel.getInstance().getCurLocation().getLongitude();
        PlaceBrief[] placeBriefs = new PlaceBrief[count];
        for (int i = 0; i < placeBriefs.length; i++) {
            placeBriefs[i] = new PlaceBrief(
                    i,"南山鱼塘","http://img5.imgtn.bdimg.com/it/u=2219957519,4104610372&fm=21&gp=0.jpg","南山",
                    3.8f, (int) (Math.random()*500), (int) (Math.random()*2),"沼跃鱼",1,"0,1",lat+(Math.random()-0.5)*10,lng+(Math.random()-0.5)*10
            );
        }
        return placeBriefs;
    }
}
