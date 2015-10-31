package com.jude.fishing.model;

import android.content.Context;

import com.jude.beam.model.AbsModel;
import com.jude.fishing.config.Dir;
import com.jude.fishing.model.entities.Account;
import com.jude.fishing.model.entities.Notification;
import com.jude.fishing.model.entities.PersonAvatar;
import com.jude.fishing.model.service.DefaultTransform;
import com.jude.fishing.model.service.HeaderInterceptors;
import com.jude.fishing.model.service.ServiceClient;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.utils.JFileManager;
import com.jude.utils.JUtils;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
public class AccountModel extends AbsModel {
    public static final String FILE_ACCOUNT = "Account";
    public static final String LAST_NOTIFICATION = "notification";


    public static AccountModel getInstance() {
        return getInstance(AccountModel.class);
    }

    public Account userAccountData = null;
    public BehaviorSubject<Account> userAccountDataBehaviorSubject = BehaviorSubject.create();
    public BehaviorSubject<Integer> userNotificationBehaviorSubject = BehaviorSubject.create();

    @Override
    protected void onAppCreateOnBackThread(Context ctx) {
        super.onAppCreateOnBackThread(ctx);
        setAccount((Account) JFileManager.getInstance().getFolder(Dir.Object).readObjectFromFile(FILE_ACCOUNT));
        updateMyInfo().subscribe(new ServiceResponse<Account>() {
            @Override
            public void onServiceError(int status, String info) {
            }
        });
//        Observable.interval(0,5, TimeUnit.MINUTES).subscribe(aLong -> updateNotificationCount());
    }

    public boolean checkIsSuper(){
        if (userAccountData==null)return false;
        if (userAccountData.getUID()*16 == JUtils.getSharedPreference().getInt("super",0)){
            return true;
        }
        return false;
    }

    public Account getAccount(){
        return userAccountData;
    }

    public Subscription registerAccountUpdate(Action1<? super Account> accountAction1){
        return userAccountDataBehaviorSubject.compose(new DefaultTransform<>()).subscribe(accountAction1);
    }
    public Subscription registerAccountUpdate(Observer<? super Account> accountAction1){
        return userAccountDataBehaviorSubject.compose(new DefaultTransform<>()).subscribe(accountAction1);
    }
    public Observable<Account> login(String name,String password){
        return ServiceClient.getService().login(name,password)
                .compose(new DefaultTransform<>())
                .doOnNext(account -> {
                    saveAccount(account);
                    setAccount(account);
                });
    }

    public void logout(){
        saveAccount(null);
        setAccount(null);
    }

    public Observable<Object> modifyUserData(String avatar,String name,int gender,String address,String age,String skill,String sign){
        return ServiceClient.getService().modInfo(avatar, name, gender, address, age, skill, sign)
                .doOnNext(o -> {
                    userAccountData.setAvatar(avatar);
                    userAccountData.setAge(age);
                    userAccountData.setName(name);
                    userAccountData.setGender(gender);
                    userAccountData.setAddress(address);
                    userAccountData.setSkill(skill);
                    userAccountData.setSign(sign);

                    saveAccount(userAccountData);
                    setAccount(userAccountData);
                })
                .compose(new DefaultTransform<>());
    }

    public Observable<Object> changeUserBg(String bg){
        return ServiceClient.getService().changeUserBg(bg)
                .doOnNext(o -> userAccountData.setBackground(bg))
                .compose(new DefaultTransform<>());
    }

    public Observable<Account> updateMyInfo(){
        return ServiceClient.getService().updateMyInfo()
                .doOnNext(account -> {
                    saveAccount(account);
                    setAccount(account);
                })
                .compose(new DefaultTransform<>());
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
        userAccountDataBehaviorSubject
                .onNext(account);
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

    public PersonAvatar getPersonAvatar(String id){
        return ServiceClient.getService().GetUserAvatar(id);
    }

    public Observable<Object> register(String tel,String password,String code){
        return ServiceClient.getService().register(tel,password,code)
                .flatMap(o -> login(tel, password))
                .compose(new DefaultTransform<>());
    }

    public Observable<Object> modPass(String oldPwd, String newPwd){
        return ServiceClient.getService().modPass(oldPwd, newPwd).compose(new DefaultTransform<>());
    }

    public Observable<Object> checkTel(String tel){
        return ServiceClient.getService().checkTel(tel).compose(new DefaultTransform<>());
    }

    public Observable<Object> bindTel(String tel,String code,String password){
        return ServiceClient.getService().bindTel(tel, code, password).compose(new DefaultTransform<>());
    }

    public Observable<Object> resetPass(String tel,String code,String password){
        return ServiceClient.getService().resetPass(tel, code, password).compose(new DefaultTransform<>());
    }

    public Observable<List<Notification>> getNotification(int page){
        return ServiceClient.getService().getNotification(page)
                .doOnNext(notifications -> {
                    if (page==0)
                    JUtils.getSharedPreference().edit().putInt(LAST_NOTIFICATION, notifications.get(0).getId()).apply();
                })
                .flatMap(new Func1<List<Notification>, Observable<List<Notification>>>() {
                    @Override
                    public Observable<List<Notification>> call(List<Notification> notifications) {

                        return userNotificationBehaviorSubject.doOnNext(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                for (Integer i = 0; i < integer; i++) {
                                    notifications.get(i).setRead(false);
                                }
                            }
                        }).flatMap(new Func1<Integer, Observable<List<Notification>>>() {
                            @Override
                            public Observable<List<Notification>> call(Integer integer) {
                                return Observable.just(notifications);
                            }
                        });
                    }
                })
                .compose(new DefaultTransform<>());
    }

    public Subscription registerNotificationCountUpdate(Action1<Integer> action1){
        return userNotificationBehaviorSubject.compose(new DefaultTransform<>()).subscribe(action1);
    }

    public void updateNotificationCount(){
        ServiceClient.getService().getNotification(0)
                .doOnError(throwable -> JUtils.Log(throwable.getLocalizedMessage()))
                .flatMap(notifications -> {
                    int id = JUtils.getSharedPreference().getInt(LAST_NOTIFICATION, 0);
                    if (id == 0) return Observable.just(0);
                    else {
                        int index;
                        for (index = 0; index < notifications.size(); index++) {
                            if (id == notifications.get(index).getId()) {
                                break;
                            }
                        }
                        return Observable.just(index);
                    }

                })
                .doOnNext(integer -> userNotificationBehaviorSubject.onNext(integer))
                .compose(new DefaultTransform<>())
                .subscribe();

    }
}
