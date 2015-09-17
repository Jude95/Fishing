package com.jude.fishing.model;

import android.content.Context;
import android.net.Uri;

import com.jude.beam.model.AbsModel;
import com.jude.fishing.model.bean.PersonBrief;
import com.jude.utils.JUtils;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

/**
 * Created by Mr.Jude on 2015/7/8.
 */
public class RongYunModel extends AbsModel {

    public static RongYunModel getInstance() {
        return getInstance(RongYunModel.class);
    }
    public BehaviorSubject<Integer> mNotifyBehaviorSubject = BehaviorSubject.create();
    @Override
    protected void onAppCreate(Context ctx) {
        AccountModel.getInstance().registerAccountUpdate(user -> {
            if (user != null) connectRongYun1(user.getRongToken());
        });
        if (AccountModel.getInstance().getAccount()!=null)
            connectRongYun1(AccountModel.getInstance().getAccount().getRongToken());
    }

    public void loginOut(){
        connectRongYun1("");
    }

    public Subscription registerNotifyCount(Action1<Integer> notify){
        return mNotifyBehaviorSubject.subscribe(notify);
    }

    public void connectRongYun1(String token){
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                JUtils.Log("融云Token失效");
            }

            @Override
            public void onSuccess(String s) {
                JUtils.Log("融云连接成功");
                setRongYun();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                JUtils.Log("融云连接失败：" + errorCode.getValue() + ":" + errorCode.getMessage());
            }
        });
    }

    public void setRongYun(){
        try {

        } catch (Exception e) {
            JUtils.Log("融云出错");
        }
    }


    public void updateRongYunPersonBrief(PersonBrief p){
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(p.getUID()+"",p.getName(), Uri.parse(p.getAvatar())));
    }

    public void chatPerson(Context ctx,String id,String title){
//        Intent i = new Intent(ctx, ChatActivity.class);
//        i.putExtra("id",id);
//        i.putExtra("title",title);
//        i.putExtra("type", Conversation.ConversationType.PRIVATE.getName().toLowerCase());
//        ctx.startActivity(i);
        RongIM.getInstance().startPrivateChat(ctx, id, title);
    }

    public void chatGroup(Context ctx,String id,String title){
//        Intent i = new Intent(ctx, ChatActivity.class);
//        i.putExtra("id",id);
//        i.putExtra("title",title);
//        i.putExtra("type", Conversation.ConversationType.GROUP.getName().toLowerCase());
//        ctx.startActivity(i);
        RongIM.getInstance().startGroupChat(ctx,id,title);
    }

    public void chatList(Context ctx){
        RongIM.getInstance().startConversationList(ctx);
        //ctx.startActivity(new Intent(ctx, ChatListActivity.class));
    }
}
