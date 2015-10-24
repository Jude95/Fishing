package com.jude.fishing.module.setting.update;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.fishing.R;
import com.jude.fishing.model.entities.UpdateInfo;
import com.jude.fishing.model.service.DefaultTransform;
import com.jude.fishing.model.service.ServiceClient;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.utils.JUtils;

/**
 * Created by zhuchenxi on 15/10/20.
 */
public class UpdateChecker {
    public static final String NEW_VERSION_URL = "newVersionUrl";
    private static UpdateChecker instance = new UpdateChecker();

    public static UpdateChecker getInstance() {
        return instance;
    }


    public String getDownloadUrl(){
        return JUtils.getSharedPreference().getString(NEW_VERSION_URL,"");
    }

    public void checkUpdate(Context ctx){
        ServiceClient.getService().getUpdateInfo()
                .compose(new DefaultTransform<>())
                .subscribe(new ServiceResponse<UpdateInfo>() {
                    @Override
                    public void onNext(UpdateInfo updateInfo) {
                        JUtils.getSharedPreference().edit().putString(NEW_VERSION_URL,updateInfo.getUrl()).apply();
                        if (updateInfo.getVersionCode() > JUtils.getAppVersionCode()) {
                            showUpdateDialog(
                                    ctx,
                                    updateInfo.getVersionName(),
                                    updateInfo.getInfo(),
                                    updateInfo.getUrl());
                        }
                    }
                });
    }

    public void forceUpdate(Context ctx){
        ServiceClient.getService().getUpdateInfo()
                .compose(new DefaultTransform<>())
                .subscribe(new ServiceResponse<UpdateInfo>() {
                    @Override
                    public void onNext(UpdateInfo updateInfo) {
                        JUtils.getSharedPreference().edit().putString(NEW_VERSION_URL, updateInfo.getInfo()).apply();
                        if (updateInfo.getVersionCode() > JUtils.getAppVersionCode()) {
                            showUpdateDialog(
                                    ctx,
                                    updateInfo.getVersionName(),
                                    updateInfo.getInfo(),
                                    updateInfo.getUrl());
                        } else {
                            JUtils.Toast("已经是最新版本");
                        }
                    }
                });
    }

    private void showUpdateDialog(Context ctx,String versionName,String content,String url){
        new MaterialDialog.Builder(ctx)
                .title("新版本 "+versionName)
                .content(content)
                .positiveText("立即升级")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        JUtils.Log("Get Start");
                        Intent updateIntent = new Intent(ctx,UpdateService.class);
                        updateIntent.putExtra("title","空钩正在更新");
                        updateIntent.putExtra("url",url);
                        updateIntent.putExtra("path", Environment.getExternalStorageDirectory() +"/"+"download/");
                        updateIntent.putExtra("name",ctx.getString(R.string.app_name)+"v"+versionName+".apk");
                        ctx.startService(updateIntent);
                    }
                })
                .show();

    }
}
