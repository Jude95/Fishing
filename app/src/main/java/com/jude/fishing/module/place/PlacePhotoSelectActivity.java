package com.jude.fishing.module.place;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.exgridview.PieceViewGroup;
import com.jude.fishing.R;
import com.jude.fishing.widget.NetImagePieceView;

/**
 * Created by Mr.Jude on 2015/10/10.
 */
@RequiresPresenter(PlacePhotoSelectPresenter.class)
public class PlacePhotoSelectActivity extends BeamBaseActivity<PlacePhotoSelectPresenter>{
    PieceViewGroup mPieceViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_activity_select_photo);
        mPieceViewGroup = $(R.id.pictures);
        mPieceViewGroup.setOnAskViewListener(this::showSelectorDialog);
        mPieceViewGroup.setAddImageRes(R.drawable.pic_add);
        mPieceViewGroup.setOKImageRes(R.drawable.pic_ok);
        mPieceViewGroup.setOnViewDeleteListener(getPresenter());
    }

    public void showSelectorDialog() {
        new MaterialDialog.Builder(this)
                .title("选择图片来源")
                .items(new String[]{"拍照", "相册"})
                .itemsCallback((materialDialog, view, i, charSequence) -> getPresenter().editFace(i)).show();
    }

    public void addImage(Uri uri) {
        NetImagePieceView pieceView = new NetImagePieceView(this);
        pieceView.setImage(uri);
        mPieceViewGroup.addView(pieceView);
    }

    public void addImage(Bitmap bitmap) {
        NetImagePieceView pieceView = new NetImagePieceView(this);
        pieceView.setImage(bitmap);
        mPieceViewGroup.addView(pieceView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ok,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.ok){
            getPresenter().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
