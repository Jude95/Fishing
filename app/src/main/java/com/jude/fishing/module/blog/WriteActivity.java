package com.jude.fishing.module.blog;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.exgridview.ImagePieceView;
import com.jude.exgridview.PieceViewGroup;
import com.jude.fishing.R;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/9/13.
 */

@RequiresPresenter(WritePresenter.class)
public class WriteActivity extends BeamBaseActivity<WritePresenter> {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.send)
    TAGView send;
    @InjectView(R.id.images)
    PieceViewGroup images;
    @InjectView(R.id.content)
    EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_activity_write);
        ButterKnife.inject(this);
        images.setOnAskViewListener(this::showSelectorDialog);
        images.setAddImageRes(R.drawable.pic_add);
        images.setOKImageRes(R.drawable.pic_ok);
        images.setOnViewDeleteListener(getPresenter());
        send.setOnClickListener(v->checkInput());
    }

    private void checkInput() {
        if (content.getText().toString().trim().isEmpty()){
            JUtils.Toast("说点什么吧");
            return;
        }
        getPresenter().writeBlog(content.getText().toString().trim());
    }

    public void showSelectorDialog() {
        new MaterialDialog.Builder(this)
                .title("选择图片来源")
                .items(new String[]{"拍照", "相册", "网络"})
                .itemsCallback((materialDialog, view, i, charSequence) -> getPresenter().editFace(i)).show();
    }

    public void addImage(Bitmap bitmap) {
        ImagePieceView pieceView = new ImagePieceView(this);
        pieceView.setImageBitmap(bitmap);
        images.addView(pieceView);
    }

}
