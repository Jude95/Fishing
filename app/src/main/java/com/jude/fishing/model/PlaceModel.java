package com.jude.fishing.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.jude.beam.model.AbsModel;
import com.jude.fishing.model.db.DBHelper;
import com.jude.fishing.model.db.PlaceDBTable;
import com.jude.fishing.model.entities.Evaluate;
import com.jude.fishing.model.entities.EvaluateDetail;
import com.jude.fishing.model.entities.PlaceBrief;
import com.jude.fishing.model.entities.PlaceDetail;
import com.jude.fishing.model.service.DefaultTransform;
import com.jude.fishing.model.service.ServiceClient;
import com.jude.utils.JUtils;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Mr.Jude on 2015/9/12.
 */
public class PlaceModel extends AbsModel {
    public static final String PLACE_LAST_SYNC_TIME = "placeLastSyncTime";
    public static final String FILTER_POOL = "filter_pool";
    public static final String FILTER_COST = "filter_cost";

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
        syncPlace().subscribe();
    }

    public void saveFilter(int poolType,int costType){
        SharedPreferences.Editor editor = JUtils.getSharedPreference().edit();
        editor.putInt(FILTER_POOL,poolType);
        editor.putInt(FILTER_COST,costType);
        editor.apply();
    }

    public int getFilterPoolType(){
        return JUtils.getSharedPreference().getInt(FILTER_POOL,-1);
    }
    public int getFilterCostType(){
        return JUtils.getSharedPreference().getInt(FILTER_COST,-1);
    }


    public Observable<List<PlaceBrief>> getAllPlaces(){
        return mDbBrite.createQuery(PlaceDBTable.TABLE_NAME,
                "SELECT * FROM " + PlaceDBTable.TABLE_NAME + " WHERE "+PlaceDBTable.COLUMN_STATUS+"==1")
                .mapToList(cursor -> PlaceDBTable.getInstance().from(cursor))
                .subscribeOn(Schedulers.io())
                .compose(new DefaultTransform<>());
    }

    public Observable<List<PlaceBrief>> getPlacesByDistance(double lat, double lng){
        return mDbBrite.createQuery(PlaceDBTable.TABLE_NAME,
                "SELECT *  FROM " + PlaceDBTable.TABLE_NAME
                        + " WHERE "+PlaceDBTable.COLUMN_STATUS+"==1"
                        + (getFilterCostType()==-1?"":" AND "+PlaceDBTable.COLUMN_COST_TYPE+" == "+getFilterCostType())
                        + (getFilterPoolType()==-1?"":" AND "+PlaceDBTable.COLUMN_POOL_TYPE+" == "+getFilterPoolType())
                        + " ORDER BY ((lat - " + lat + ")*(lat - " + lat + ")+(lng - " + lng + ")*(lng - " + lng + "))")
                .mapToList(cursor -> PlaceDBTable.getInstance().from(cursor))
                .subscribeOn(Schedulers.io())
                .compose(new DefaultTransform<>());
    }

    public Observable<List<PlaceBrief>> updatePlacesByDistance(double lat, double lng){
        return syncPlace()
                .flatMap(placeBriefs -> getPlacesByDistance(lat, lng))
                .first()
                .compose(new DefaultTransform<>());
    }


    public Observable<List<PlaceBrief>> syncPlace(){
        return ServiceClient.getService().syncPlace(JUtils.getSharedPreference().getString(PLACE_LAST_SYNC_TIME, "0"))
                .doOnCompleted(() -> JUtils.getSharedPreference().edit().putString(PLACE_LAST_SYNC_TIME, System.currentTimeMillis() / 1000 + "").apply())
                .doOnNext(placeBriefs -> {
                    BriteDatabase.Transaction transaction = mDbBrite.newTransaction();
                    for (PlaceBrief placeBrief : placeBriefs) {
                        try {
                            mDbBrite.insert(PlaceDBTable.TABLE_NAME, PlaceDBTable.getInstance().to(placeBrief));
                            JUtils.Log("DB", "inserted:" + placeBrief.getName());
                        } catch (Exception e) {
                            JUtils.Log("DB", "inserted ERROR:" + e.getLocalizedMessage());
                            try {
                                mDbBrite.update(PlaceDBTable.TABLE_NAME, PlaceDBTable.getInstance().to(placeBrief), PlaceDBTable.COLUMN_ID + "=" + placeBrief.getId());
                                JUtils.Log("DB", "updated" + placeBrief.getName());
                            } catch (Exception e1) {
                                JUtils.Log("DB", "updated ERROR:" + e1.getLocalizedMessage());
                            }
                        }
                    }
                    transaction.markSuccessful();
                    transaction.end();
                });
    }

    public Observable<PlaceDetail> getPlaceDetail(int id){
        return ServiceClient.getService().getPlaceDetail(id).compose(new DefaultTransform<>());
    }

    public Observable<Object> publishPlace(PlaceDetail placeDetail){
        String picture  = new Gson().toJson(placeDetail.getPicture());
        return ServiceClient.getService()
                .publishPlace(
                    placeDetail.getId(),
                    placeDetail.getName(),
                    placeDetail.getPreview(),
                    placeDetail.getAddressBrief(),
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
                    placeDetail.getLng(),
                    placeDetail.getArea(),
                    placeDetail.getDeep(),
                    placeDetail.getNest())
                .compose(new DefaultTransform<>());
    }

    public Observable<List<PlaceBrief>> getUserPlaces(){
        return ServiceClient.getService().myPlace().compose(new DefaultTransform<>());
    }

    public Observable<List<PlaceBrief>> getMyCollectionPlaces(){
        return ServiceClient.getService().myPlaceCollect().compose(new DefaultTransform<>());
    }

    public Observable<List<Evaluate>> getEvaluates(int id, int page){
        return ServiceClient.getService().getEvaluate(id, page).compose(new DefaultTransform<>());
    }

    public Observable<EvaluateDetail> getEvaluateDetail(int id){
        return ServiceClient.getService().getEvaluateDetail(id).compose(new DefaultTransform<>());
    }

    public Observable<Object> sendEvaluateComment(int sid,int fid,String content){
        return ServiceClient.getService().EvaluateComment(sid, fid, content).compose(new DefaultTransform<>());
    }

    public Observable<Object> publishEvaluate(int pid,String content,List<String> images,int score){
        String imageStr = "";
        if (images!=null){
            imageStr = new Gson().toJson(images);
        }
        return ServiceClient.getService().publishEvaluate(pid, content, imageStr, score).compose(new DefaultTransform<>());
    }

    public Observable<Object> collectPlace(int id){
        return ServiceClient.getService().collectPlace(id).compose(new DefaultTransform<>());
    }

    public Observable<Object> unCollectPlace(int id){
        return ServiceClient.getService().unCollectPlace(id).compose(new DefaultTransform<>());
    }

    public Observable<List<Evaluate>> getMyEvaluate(){
        return ServiceClient.getService().myEvaluate().compose(new DefaultTransform<>());
    }

}
