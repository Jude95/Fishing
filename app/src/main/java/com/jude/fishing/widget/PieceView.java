package com.jude.fishing.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jude.fishing.R;
import com.jude.utils.JUtils;

/**
 * Created by Mr.Jude on 2015/3/13.
 */
public class PieceView extends FrameLayout implements View.OnLongClickListener{
    private PieceViewGroup mGroup;


    protected ImageView mDelete;


    protected boolean isEditAble = true;
    protected boolean isEditMode = false;

    public PieceView(Context context) {
        this(context, null);
    }

    public PieceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    protected void initView(){
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        setOnLongClickListener(this);
        mDelete = new ImageView(getContext());
        mDelete.setLayoutParams(new LayoutParams(JUtils.dip2px(32), JUtils.dip2px(32)));
        mDelete.setImageResource(R.drawable.ic_delete);
        mDelete.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mDelete.setVisibility(GONE);
        mDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete();
            }
        });
        addView(mDelete);
    }
    public void setEditAble(boolean isEditAble) {
        this.isEditAble = isEditAble;
    }

    public void setViewGroup(PieceViewGroup manager){
        mGroup = manager;
    }

    public void onDelete(){
        clearAnimation();
        mGroup.delete(this);
    }

    @Override
    public boolean onLongClick(View v) {
        applyEditMode(true,true);
        return true;
    }

    void applyEditMode(boolean isEditMode,boolean isNeedChangeOther){
        if (isEditMode == this.isEditMode)return;
        if (isEditAble&&isEditMode){
            mDelete.bringToFront();
            mDelete.setVisibility(VISIBLE);
            this.isEditMode = true;
            if (isNeedChangeOther)
                mGroup.edit();
                startAnimation(shakeAnimation());
        }else{
            mDelete.setVisibility(GONE);
            clearAnimation();
            this.isEditMode = false;
        }
    }

    public RotateAnimation shakeAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(-1,1,getWidth()/2,getHeight()/2);
        rotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        rotateAnimation.setDuration(60);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setRepeatMode(RotateAnimation.REVERSE);
        rotateAnimation.setFillAfter(false);
        return rotateAnimation;
    }

}
