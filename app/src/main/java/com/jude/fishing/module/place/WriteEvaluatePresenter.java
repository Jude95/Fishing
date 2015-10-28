package com.jude.fishing.module.place;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.exgridview.PieceViewGroup;
import com.jude.fishing.model.ImageModel;
import com.jude.fishing.model.PlaceModel;
import com.jude.fishing.model.service.ServiceResponse;
import com.jude.fishing.widget.ScoreView;
import com.jude.library.imageprovider.ImageProvider;
import com.jude.library.imageprovider.OnImageSelectListener;
import com.jude.utils.JUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhuchenxi on 15/10/3.
 */
public class WriteEvaluatePresenter extends Presenter<WriteEvaluateActivity> implements  PieceViewGroup.OnViewDeleteListener, ScoreView.OnScoreSelectedListener {
    private ImageProvider provider;
    private ArrayList<Uri> uriArrayList = new ArrayList<>();
    private int pid;
    private int score;
    OnImageSelectListener listener = new OnImageSelectListener() {

        @Override
        public void onImageSelect() {
            getView().getExpansion().showProgressDialog("加载中");
        }

        @Override
        public void onImageLoaded(Uri uri) {
            getView().getExpansion().dismissProgressDialog();
            getView().addImage(ImageProvider.readImageWithSize(uri, 300, 300));
            uriArrayList.add(uri);
        }

        @Override
        public void onError() {

        }
    };

    @Override
    protected void onCreate(WriteEvaluateActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        pid = view.getIntent().getIntExtra("id",0);
        provider = new ImageProvider(getView());
    }

    public void editFace(int style){
        if (uriArrayList.size()>=9){
            JUtils.Toast("最多上传9张图片");
            return;
        }
        switch (style){
            case 0:
                provider.getImageFromCamera(listener);
                break;
            case 1:
                provider.getImageFromAlbum(listener,9-uriArrayList.size());
                break;
            case 2:
                provider.getImageFromNet(listener);
                break;
        }
    }

    @Override
    protected void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        provider.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onViewDelete(int i) {
        uriArrayList.remove(i);
    }

    @Override
    public void onSelected(int score) {
        this.score = score;
        getView().setEmoticon(score);
    }

    public void submit(String content){
        if (score==0){
            JUtils.Toast("请选择评分");
            return;
        }
        if (uriArrayList.size()>0){
            submitWithImage(content);
        }else {
            submitOnlyText(content);
        }
    }

    private void submitOnlyText(String content){
        getView().getExpansion().showProgressDialog("开始上传");
        PlaceModel.getInstance().publishEvaluate(pid, content, null, score)
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        JUtils.Toast("提交成功");
                        getView().setResult(Activity.RESULT_OK);
                        getView().finish();
                    }
                });
    }

    private void submitWithImage(String content){
        File[] files = new File[uriArrayList.size()];
        for (int i = 0; i < uriArrayList.size(); i++) {
            files[i] = new File(uriArrayList.get(i).getPath());
        }
        List<String> images = new ArrayList<>();
        getView().getExpansion().showProgressDialog("开始上传");
        ImageModel.getInstance().putImage(files)
                .doOnError(throwable -> {
                    getView().getExpansion().dismissProgressDialog();
                    JUtils.Toast("图片上传失败");
                    JUtils.Log("Error:"+throwable.getLocalizedMessage());
                })
                .doOnNext(s -> {
                    getView().getExpansion().showProgressDialog("上传图片第"+(images.size())+"/"+files.length+"张");
                    images.add(s);
                })
                .toList()
                .flatMap(strings -> {
                    getView().getExpansion().showProgressDialog("上传服务器中");
                    return PlaceModel.getInstance().publishEvaluate(pid, content, images, score);
                })
                .finallyDo(() -> getView().getExpansion().dismissProgressDialog())
                .subscribe(new ServiceResponse<Object>() {
                    @Override
                    public void onNext(Object o) {
                        JUtils.ToastLong("提交成功，图片需要几十秒上传完成才能查看");
                        getView().setResult(Activity.RESULT_OK);
                        getView().finish();
                    }
                });
    }
}
