package com.jude.fishing.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jude.fishing.R;

/**
 * Created by Mr.Jude on 2015/9/12.
 */
public class ScoreView extends LinearLayout implements View.OnClickListener{
    private OnScoreSelectedListener mOnScoreSelectedListener;
    public interface OnScoreSelectedListener {
        void onSelected(int score);
    }

    public void setOnScoreSelectedListener(OnScoreSelectedListener listener){
        this.mOnScoreSelectedListener = listener;
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setOnClickListener(this);
        }
    }

    public ScoreView(Context context) {
        super(context);
        init();
    }

    public ScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setOrientation(HORIZONTAL);
        for (int i = 0; i < 5; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            imageView.setImageResource(R.drawable.ic_score_unfocus);
            imageView.setTag(i + 1);
            addView(imageView);
        }
    }

    public void setScore(float score){
        for (int i = 0; i < 5; i++) {
            ((ImageView)getChildAt(i)).setImageResource(i<(int)score?R.drawable.ic_score_focus :R.drawable.ic_score_unfocus);
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnScoreSelectedListener!=null){
            setScore((int) v.getTag());
            mOnScoreSelectedListener.onSelected((int) v.getTag());
        }
    }
}

