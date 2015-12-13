package com.jude.fishing.app;

import android.app.ActivityManager;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.amap.api.navi.AMapNavi;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.jude.beam.Beam;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.beam.expansion.list.ListConfig;
import com.jude.beam.expansion.overlay.ViewConfig;
import com.jude.beam.expansion.overlay.ViewExpansionDelegate;
import com.jude.beam.expansion.overlay.ViewExpansionDelegateProvider;
import com.jude.fishing.BuildConfig;
import com.jude.fishing.R;
import com.jude.fishing.config.Dir;
import com.jude.fishing.utils.DataCleaner;
import com.jude.fishing.utils.TTSController;
import com.jude.utils.JFileManager;
import com.jude.utils.JUtils;
import com.umeng.message.PushAgent;

import io.rong.imkit.RongIM;


/**
 * Created by Mr.Jude on 2015/1/27.
 */
public class APP extends MultiDexApplication {
    private static APP instance = null;
    public static APP getInstance(){
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        /* OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * xxx.xxx.xxx 是您的主进程或者使用了 RongIM 的其他进程 */
        if("com.jude.fishing".equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

            try {
            /* IMKit SDK调用第一步 初始化 */
                RongIM.init(this);
            }catch (Exception e){
                //Android6.0还没有适配
            }

            /* 必须在使用 RongIM 的进程注册回调、注册自定义消息等 */
            if ("com.jude.fishing".equals(getCurProcessName(getApplicationContext()))) {
                instance = this;
                Fresco.initialize(this);
                JUtils.initialize(this);
                JUtils.setDebug(true, "Fishing");
                JFileManager.getInstance().init(this, Dir.values());
                DataCleaner.Update(this, 18);
                PushAgent.getInstance(this).setDebugMode(BuildConfig.DEBUG);
                Beam.init(this);
                Beam.setActivityLifeCycleDelegateProvider(ActivityDelegate::new);
                Beam.setViewExpansionDelegateProvider(new ViewExpansionDelegateProvider() {
                    @Override
                    public ViewExpansionDelegate createViewExpansionDelegate(BeamBaseActivity activity) {
                        return new PaddingTopViewExpansion(activity);
                    }
                });


                TTSController ttsManager = TTSController.getInstance(this);// 初始化语音模块
                ttsManager.init();
                try {
                    AMapNavi.getInstance(this).setAMapNaviListener(ttsManager);// 设置语音模块播报
                }catch (Exception e){
                }
                ListConfig.setDefaultListConfig(new ListConfig().
                        setRefreshAble(true).
                        setContainerLayoutRes(R.layout.activity_recyclerview).
                        setContainerProgressRes(R.layout.include_loading));
                ViewConfig.setDefaultViewConfig(new ViewConfig().
                                setProgressRes(R.layout.activity_progress).
                                setErrorRes(R.layout.activity_error)
                );
            }
        }
    }


    /* 一个获得当前进程的名字的方法 */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

}
