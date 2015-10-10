package com.jude.fishing.module.place;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.exgridview.ImagePieceView;
import com.jude.exgridview.PieceViewGroup;
import com.jude.fishing.R;
import com.jude.fishing.widget.ScoreView;
import com.jude.tagview.TAGView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhuchenxi on 15/10/3.
 */
@RequiresPresenter(WriteEvaluatePresenter.class)
public class WriteEvaluateActivity extends BeamBaseActivity<WriteEvaluatePresenter> {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.score)
    ScoreView score;
    @InjectView(R.id.content)
    EditText content;
    @InjectView(R.id.images)
    PieceViewGroup images;
    @InjectView(R.id.send)
    TAGView send;
    @InjectView(R.id.emoticon)
    ImageView emoticon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_activity_evaluate_write);
        ButterKnife.inject(this);
        images.setOnAskViewListener(this::showSelectorDialog);
        images.setAddImageRes(R.drawable.pic_add);
        images.setOKImageRes(R.drawable.pic_ok);
        images.setOnViewDeleteListener(getPresenter());
        score.setOnScoreSelectedListener(getPresenter());
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

    int[] emoticonRes = {
            R.drawable.emot_1,
            R.drawable.emot_2,
            R.drawable.emot_3,
            R.drawable.emot_4,
            R.drawable.emot_5,
    };
    public void setEmoticon(int score){
        emoticon.setImageResource(emoticonRes[score-1]);
    }
}
