package com.jude.fishing.model;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.net.Uri;

import com.jude.beam.model.AbsModel;
import com.jude.fishing.model.service.ServiceClient;
import com.jude.utils.JUtils;
import com.qiniu.android.storage.UploadManager;

import java.io.File;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by zhuchenxi on 15/7/21.
 */
public class ImageModel extends AbsModel {
    public static String UID = "";
    public static ImageModel getInstance() {
        return getInstance(ImageModel.class);
    }
    public static final String ADDRESS = "http://7xn7nj.com2.z0.glb.qiniucdn.com/";

    private UploadManager mUploadManager;


    @Override
    protected void onAppCreate(Context ctx) {
        super.onAppCreate(ctx);
        mUploadManager = new UploadManager();
    }

    public Uri getSmallImage(String path){
        if (path==null)return null;
        if (path.startsWith(ADDRESS))
            return Uri.parse(path+"?imageView2/0/w/360");
        else
            return Uri.parse(path);
    }

    public Uri getLargeImage(String path){
        if (path==null)return null;
        if (path.startsWith(ADDRESS))
            return Uri.parse(path+"?imageView2/0/w/1024");
        else
            return Uri.parse(path);
    }

    public Uri getSizeImage(String path,int width){
        if (path==null)return null;
        if (path.startsWith(ADDRESS))
            return Uri.parse(path+"?imageView2/0/w/"+width);
        else
            return Uri.parse(path);
    }


    /**
     *
     * @param file 需上传文件
     * @return 上传文件访问地址
     */
    public Observable<String> putImage(final File file){
        return ServiceClient.getService().getQiNiuToken().flatMap(token -> Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String realName = "u"+UID+System.currentTimeMillis()+file.hashCode()+".jpg";
                String path = ADDRESS+realName;
                mUploadManager.put(file, realName, token.getToken(), (key, info, response) -> {
                    if (info.isOK())subscriber.onNext(path);
                    else subscriber.onError(new NetworkErrorException(info.error));
                    subscriber.onCompleted();
                }, null);
            }
        }));
    }

    public Observable<String> putImage(final File[] file){

        return ServiceClient.getService().getQiNiuToken().flatMap(token -> Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                final int[] counter = {0};
                for (File temp : file) {
                    JUtils.Log(temp.getPath()+" exist:"+temp.exists());

                    String realName = "u"+UID+System.currentTimeMillis()+temp.hashCode()+".jpg";
                    String path = ADDRESS+realName;
                    mUploadManager.put(temp, realName, token.getToken(), (key, info, response) -> {
                        JUtils.Log("Uploaded "+path+info.isOK()+" "+info.toString());
                        if (info.isOK())subscriber.onNext(path);
                        else subscriber.onError(new NetworkErrorException("Error:"+info.error));
                        if (++counter[0]==file.length)subscriber.onCompleted();
                    }, null);
                }
            }
        }));
    }



}
