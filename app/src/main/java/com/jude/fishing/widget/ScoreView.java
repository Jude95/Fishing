package com.jude.fishing.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jude.fishing.R;

/**
 * Created by Mr.Jude on 2015/9/12.
 */
public class ScoreView extends LinearLayout {
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
            imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT,1));
            imageView.setImageResource(R.drawable.ic_score_unfocus);
            addView(imageView);
        }
    }

    public void setScore(float score){
        for (int i = 0; i < 5; i++) {
            ((ImageView)getChildAt(i)).setImageResource(i<score?R.drawable.ic_score_focus :R.drawable.ic_score_unfocus);
        }
    }
}

