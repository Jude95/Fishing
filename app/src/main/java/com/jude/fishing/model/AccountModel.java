package com.jude.fishing.model;

import android.content.Context;

import com.jude.beam.model.AbsModel;
import com.jude.fishing.config.Dir;
import com.jude.fishing.model.entities.Account;
import com.jude.fishing.model.entities.PersonBrief;
import com.jude.fishing.model.service.DefaultTransform;
import com.jude.fishing.model.service.HeaderInterceptors;
import com.jude.fishing.model.service.ServiceClient;
import com.jude.utils.JFileManager;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
public class AccountModel extends AbsModel {
    public static final String FILE_ACCOUNT = "Account";
    public static AccountModel getInstance() {
        return getInstance(AccountModel.class);
    }

    public Account userAccountData = null;
    public BehaviorSubject<Account> userAccountDataBehaviorSubject = BehaviorSubject.create();

    @Override
    protected void onAppCreateOnBackThread(Context ctx) {
        super.onAppCreateOnBackThread(ctx);
        setAccount((Account) JFileManager.getInstance().getFolder(Dir.Object).readObjectFromFile(FILE_ACCOUNT));
    }

    public Account getAccount(){
        return userAccountData;
    }

    public Subscription registerAccountUpdate(Action1<? super Account> accountAction1){
        return userAccountDataBehaviorSubject.subscribe(accountAction1);
    }
    public Subscription registerAccountUpdate(Observer<? super Account> accountAction1){
        return userAccountDataBehaviorSubject.subscribe(accountAction1);
    }
    public Observable<Account> login(String name,String password){
        return ServiceClient.getService().login(name,password)
                .compose(new DefaultTransform<>())
                .doOnNext(account -> {
                    account.setRelation(-1);
                    saveAccount(account);
                    setAccount(account);
                });
    }

    public void logout(){
        saveAccount(null);
        setAccount(null);
    }

    public Observable<Object> modifyUserData(String avatar,String name,int gender,String address,int age,String skill,String sign){
        return ServiceClient.getService().modInfo(avatar,name,gender,address,age,skill,sign).compose(new DefaultTransform<>());
    }

    void saveAccount(Account account){
        if (account == null){
            JFileManager.getInstance().getFolder(Dir.Object).deleteChild(FILE_ACCOUNT);
        }else{
            JFileManager.getInstance().getFolder(Dir.Object).writeObjectToFile(account, FILE_ACCOUNT);
        }
    }

    void setAccount(Account account){
        userAccountData = account;
        userAccountDataBehaviorSubject.onNext(account);
        if (account!=null){
            ImageModel.UID = account.getUID()+"";
            HeaderInterceptors.TOKEN = account.getToken();
            HeaderInterceptors.UID = account.getUID()+"";
        }else {
            ImageModel.UID = "";
            HeaderInterceptors.TOKEN = "";
            HeaderInterceptors.UID = "";
        }

    }

    public List<PersonBrief> createVirtualPersonBriefs(int count){
        List<PersonBrief> personBriefs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            personBriefs.add(new PersonBrief("http://i1.hdslb.com/user/1570/157056/myface.jpg",i,"赛亚♂sya", (int) (Math.random()*2),"沉迷于手游无法自拔填坑是什么能吃吗"));
        }
        return personBriefs;
    }

    public Account createVirtualAccount(){
        return new Account("http://i2.hdslb.com/user/18232/1823239/myface.jpg",0,"Jude",0,"喂不熟的人，忘不掉的狗","海底捞针，倒挂金钩",18,
                "http://img3.imgtn.bdimg.com/it/u=3619136483,1678174220&fm=21&gp=0.jpg","jack slow fuck",BlogModel.getInstance().createVirtualSeedList(3),
                5,8,10,"156*****295","","");

    }

    public void updateAccount(Account account){
        saveAccount(account);
        userAccountDataBehaviorSubject.onNext(account);
    }

    public Observable<Object> register(String tel,String password,String code){
        return ServiceClient.getService().register(tel,password,code).compose(new DefaultTransform<>());
    }

    public Observable<Object> modPass(String oldPwd, String newPwd){
        return ServiceClient.getService().modPass(oldPwd, newPwd).compose(new DefaultTransform<>());
    }

    public Observable<Object> checkTel(String tel){
        return ServiceClient.getService().checkTel(tel).compose(new DefaultTransform<>());
    }

    public Observable<Object> bindTel(String tel,String code,String password){
        return ServiceClient.getService().bindTel(tel,code,password).compose(new DefaultTransform<>());
    }
}
