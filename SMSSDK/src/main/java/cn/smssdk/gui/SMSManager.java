package cn.smssdk.gui;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Mr.Jude on 2015/7/6.
 */
public class SMSManager {
    private static final SMSManager instance = new SMSManager();
    public static SMSManager getInstance(){
        return instance;
    }

    public ArrayList<TimeListener> timeList = new ArrayList<>();
    private boolean inited = false;
    private Timer timer;
    private int last = 0;
    private void startTimer(){
        timer = new Timer();
        notifyEnable();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                last -= 1;
                notifyLastTime();
                if (last == 0) {
                    notifyEnable();
                    timer.cancel();
                }
            }
        }, 0, 1000);
    }

    public boolean sendMessage(Context ctx,String number){
        if (!inited){
            SMSSDK.initSDK(ctx, "a5be1fdc254c", "45080cce9c186ee33294ebc1916c5d73");
            SMSSDK.registerEventHandler(new EventHandler(){
                @Override
                public void afterEvent(int event, int result, Object data) {
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        Log.i("Message", "回调完成");
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            Log.i("Message", "提交验证码成功");
                        } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            Log.i("Message", "获取验证码成功");
                        } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                            Log.i("Message", "返回支持发送验证码的国家列表");
                        }
                    } else {
                        Log.i("Message", "Error");
                        ((Throwable) data).printStackTrace();
                    }
                }});
            inited = true;
        }
        if (last==0) {
            SMSSDK.getVerificationCode("86", number);
            last = 60;
            startTimer();
            return true;
        }else{
            return false;
        }
    }

    public void notifyLastTime(){
        for (TimeListener listener:timeList){
            final TimeListener finalListener = listener;
            try {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        finalListener.onLastTimeNotify(last);
                    }
                });
            }catch (Exception e){
                unRegisterTimeListener(listener);
            }
        }
    }

    public void notifyEnable(){
        for (TimeListener listener:timeList){
            final TimeListener finalListener = listener;
            try {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        finalListener.onAbleNotify(last == 0);
                    }
                });
            }catch (Exception e){
                unRegisterTimeListener(listener);
            }
        }
    }


    public void registerTimeListener(TimeListener listener){
        timeList.add(listener);
        listener.onLastTimeNotify(last);
        listener.onAbleNotify(last==0);
    }

    public void unRegisterTimeListener(TimeListener listener){
        timeList.remove(listener);
    }

}
