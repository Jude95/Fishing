package com.jude.fishing.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.fishing.R;


/**
 * Created by Mr.Jude on 2015/3/14.
 */
public class AddImagePieceView extends PieceView {
    protected TextView mText;
    protected ImageView mImage;

    public AddImagePieceView(Context context) {
        super(context);
    }

    public AddImagePieceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AddImagePieceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        super.initView();
        isEditAble = false;
        mImage = new ImageView(getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mImage.setLayoutParams(params);
        mImage.setImageResource(R.drawable.ic_addimg);
        mImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addView(mImage);

        mText = new TextView(getContext());
        mText.setLayoutParams(params);
        mText.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        mText.setText("添加图片");
        mText.setTextColor(Color.GRAY);
        addView(mText);
    }
}
