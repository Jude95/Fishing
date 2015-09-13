package com.jude.fishing.model;

import android.content.Context;

import com.jude.beam.model.AbsModel;
import com.qiniu.android.storage.UploadManager;

import java.io.File;

/**
 * Created by zhuchenxi on 15/7/21.
 */
public class RemoteFileModel extends AbsModel {
    public static RemoteFileModel getInstance() {
        return getInstance(RemoteFileModel.class);
    }
    public static final String ADDRESS = "http://7xjdz6.com2.z0.glb.qiniucdn.com/";

    private UploadManager mUploadManager;
    public interface UploadImageListener{
        void onComplete(SizeImage path);
        void onError();
    }

    public class Token{
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }


    @Override
    protected void onAppCreate(Context ctx) {
        super.onAppCreate(ctx);
        mUploadManager = new UploadManager();
    }

    /**
     *
     * @param file 需上传文件
     * @return 上传文件访问地址
     */
    public SizeImage putImage(final File file,final UploadImageListener listener){
        String realName = "u"+System.currentTimeMillis()+".jpg";
        String path = ADDRESS+realName;
        final SizeImage img = new SizeImage(path+"?imageView2/0/w/360",path+"?imageView2/0/w/1024",path);

        return img;
    }

    public class SizeImage{
        private String smallImage;
        private String largeImage;
        private String originalImage;

        public String getOriginalImage() {
            return originalImage;
        }

        public void setOriginalImage(String originalImage) {
            this.originalImage = originalImage;
        }

        public String getSmallImage() {
            return smallImage;
        }

        public void setSmallImage(String smallImage) {
            this.smallImage = smallImage;
        }

        public String getLargeImage() {
            return largeImage;
        }

        public void setLargeImage(String largeImage) {
            this.largeImage = largeImage;
        }

        public SizeImage(String smallImage, String largeImage, String originalImage) {
            this.smallImage = smallImage;
            this.largeImage = largeImage;
            this.originalImage = originalImage;
        }
    }

}
