package com.jude.fishing.model;

import android.content.Context;
import android.net.Uri;

import com.jude.beam.model.AbsModel;
import com.jude.fishing.model.entities.Token;
import com.jude.fishing.model.service.ServiceClient;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.utils.JUtils;
import com.qiniu.android.storage.UploadManager;

import java.io.File;

import rx.Observable;

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

    private String createName(File file){
        String realName = "u"+UID+System.currentTimeMillis()+file.hashCode()+".jpg";
        return realName;
    }

    /**
     *
     * @param file 需上传文件
     * @return 上传文件访问地址
     */
    public Observable<String> putImage(final File file){
        return Observable.just(createName(file))
                .doOnNext(name -> ServiceClient.getService().getQiNiuToken().subscribe(new ServiceResponse<Token>() {
                    @Override
                    public void onNext(Token token) {
                        mUploadManager.put(file, name, token.getToken(), (key, info, response) -> {
                            if (!info.isOK()) JUtils.Toast("图片上传失败!");
                            else JUtils.Log("图片已上传");
                        }, null);
                    }
                }))
                .map(name -> ADDRESS + name);
    }




    public Observable<String> putImage(final File[] file){
        return Observable.from(file)
                .map(file1 -> {
                    String name = createName(file1);
                    ServiceClient.getService().getQiNiuToken().subscribe(new ServiceResponse<Token>() {
                        @Override
                        public void onNext(Token token) {
                            mUploadManager.put(file1, name, token.getToken(), (key, info, response) -> {
                                if (!info.isOK()) JUtils.Toast("图片上传失败!");
                                else JUtils.Log("图片已上传："+name);
                            }, null);
                        }
                    });
                    return name;
                })
                .map(name -> ADDRESS + name);
    }



}
