package com.jude.fishing.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import com.jude.beam.model.AbsModel;
import com.jude.fishing.app.APP;
import com.jude.fishing.model.entities.Token;
import com.jude.fishing.model.service.ServiceClient;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.utils.JUtils;
import com.qiniu.android.storage.UploadManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhuchenxi on 15/7/21.
 */
public class ImageModel extends AbsModel {
    public static String UID = "";
    public static final int IMAGE_WIDTH_MAX=480;
    public static final int IMAGE_HEIGHT_MIN=800;

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
                        mUploadManager.put(compressImage(file), name, token.getToken(), (key, info, response) -> {
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
                            mUploadManager.put(compressImage(file1), name, token.getToken(), (key, info, response) -> {
                                if (!info.isOK()) JUtils.Toast("图片上传失败!");
                                else JUtils.Log("图片已上传：" + name);
                            }, null);
                        }
                    });
                    return name;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(name -> ADDRESS + name);
    }

    private File compressImage(File file){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), options);
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        int reqHeight=IMAGE_HEIGHT_MIN;
        int reqWidth=IMAGE_WIDTH_MAX;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap bitmap= BitmapFactory.decodeFile(file.getPath(), options);

        File tempfile =  createTempImage();
        FileOutputStream baos;
        try {
            baos = new FileOutputStream(tempfile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
            baos.close();
        } catch (IOException e) {
            return null;
        }
        return tempfile;
    }

    private File createTempImage(){
        String state = Environment.getExternalStorageState();
        String name = Math.random()*10000+System.nanoTime()+".jpg";
        if(state.equals(Environment.MEDIA_MOUNTED)){
            // 已挂载
            File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File tmpFile = new File(pic, name);
            return tmpFile;
        }else{
            File cacheDir = APP.getInstance().getCacheDir();
            File tmpFile = new File(cacheDir, name);
            return tmpFile;
        }
    }

}
