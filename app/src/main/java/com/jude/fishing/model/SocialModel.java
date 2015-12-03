package com.jude.fishing.model;

import com.jude.beam.model.AbsModel;
import com.jude.fishing.model.entities.Account;
import com.jude.fishing.model.entities.Contact;
import com.jude.fishing.model.entities.FishingSeed;
import com.jude.fishing.model.entities.PersonAround;
import com.jude.fishing.model.entities.PersonBrief;
import com.jude.fishing.model.service.DefaultTransform;
import com.jude.fishing.model.service.ServiceClient;

import java.util.List;

import rx.Observable;


/**
 * Created by zhuchenxi on 15/9/20.
 */
public class SocialModel extends AbsModel {

    public static SocialModel getInstance() {
        return getInstance(SocialModel.class);
    }


    public Observable<List<PersonBrief>> searchUser(String word){
        return ServiceClient.getService().FindUser(word).compose(new DefaultTransform<>());
    }

    public Observable<List<PersonAround>> getAround(int page){
        return ServiceClient.getService().GetNearBy(
                LocationModel.getInstance().getCurLocation().getLatitude(),
                LocationModel.getInstance().getCurLocation().getLongitude(),
                page
        ).compose(new DefaultTransform<>());
    }

    public Observable<List<PersonBrief>> getAttentions(int uid){
        return ServiceClient.getService().myAttend(uid).compose(new DefaultTransform<>());
//        return Observable.create(new Observable.OnSubscribe<List<PersonBrief>>() {
//            @Override
//            public void call(Subscriber<? super List<PersonBrief>> subscriber) {
//                subscriber.onNext(AccountModel.getInstance().createVirtualPersonBriefs(20));
//                subscriber.onCompleted();
//            }
//        }).compose(new DefaultTransform<>());
    }

    public Observable<List<PersonBrief>> getFans(int uid){
        return ServiceClient.getService().myFans(uid).compose(new DefaultTransform<>());
//        return Observable.create(new Observable.OnSubscribe<List<PersonBrief>>() {
//            @Override
//            public void call(Subscriber<? super List<PersonBrief>> subscriber) {
//                subscriber.onNext(AccountModel.getInstance().createVirtualPersonBriefs(20));
//                subscriber.onCompleted();
//            }
//        }).compose(new DefaultTransform<>());
    }

    public Observable<Object> attention(int id){
        return ServiceClient.getService().attend(id)
                .doOnNext(o -> {
                    int card = Integer.parseInt(AccountModel.getInstance().getAccount().getCared());
                    AccountModel.getInstance().getAccount().setCared(card + 1 + "");
                    AccountModel.getInstance().saveAccount(AccountModel.getInstance().getAccount());
                    AccountModel.getInstance().setAccount(AccountModel.getInstance().getAccount());
                })
                .compose(new DefaultTransform<>());
    }

    public Observable<Object> unAttention(int id){
        return ServiceClient.getService().unAttend(id)
                .doOnNext(o -> {
                    int card = Integer.parseInt(AccountModel.getInstance().getAccount().getCared());
                    AccountModel.getInstance().getAccount().setCared(card-1+"");
                    AccountModel.getInstance().saveAccount(AccountModel.getInstance().getAccount());
                    AccountModel.getInstance().setAccount(AccountModel.getInstance().getAccount());
                })
                .compose(new DefaultTransform<>());
    }

    public Observable<Account> getUserDetail(int uid){
        return ServiceClient.getService().getUserInfo(uid).compose(new DefaultTransform<>());
    }

    public Observable<List<Contact>> getContact(String data){
        return ServiceClient.getService().getContact(data).compose(new DefaultTransform<>());
    }

    public Observable<Object> addDateInfor(String title,String address,String content,long time){
        return ServiceClient.getService().addDateInfo(title,address,content,time).compose(new DefaultTransform<>());
    }

    public Observable<List<FishingSeed>> getDateList(int page){
        return ServiceClient.getService().getDateList(page).compose(new DefaultTransform<>());
    }

    public Observable<FishingSeed> getDateItem(String id){
        return ServiceClient.getService().getDateItem(id).compose(new DefaultTransform<>());
    }

    public Observable<List<PersonBrief>> getDatePersonList(String id){
        return ServiceClient.getService().getDatePersonList(id).compose(new DefaultTransform<>());
    }

    public Observable<Object> joinDate(String id){
        return ServiceClient.getService().joinDate(id).compose(new DefaultTransform<>());
    }
}
