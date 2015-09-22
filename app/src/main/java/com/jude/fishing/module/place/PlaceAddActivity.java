package com.jude.fishing.module.place;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.exgridview.ImagePieceView;
import com.jude.exgridview.PieceViewGroup;
import com.jude.fishing.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/9/22.
 */
@RequiresPresenter(PlaceAddPresenter.class)
public class PlaceAddActivity extends BeamBaseActivity<PlaceAddPresenter> {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.address)
    TextView address;
    @InjectView(R.id.cost_type)
    RadioGroup costType;
    @InjectView(R.id.price)
    EditText price;
    @InjectView(R.id.pool_type)
    RadioGroup poolType;
    @InjectView(R.id.fish_type)
    EditText fishType;
    @InjectView(R.id.intro)
    EditText intro;
    @InjectView(R.id.picture)
    PieceViewGroup picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_activity_add);
        ButterKnife.inject(this);
        picture.setOnAskViewListener(this::showSelectorDialog);
        picture.setOnViewDeleteListener(getPresenter());
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
        picture.addView(pieceView);
    }

}
