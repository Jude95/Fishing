package com.jude.fishing.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.jude.utils.JUtils;

import java.util.ArrayList;

/**
 * Created by Mr.Jude on 2015/3/13.
 */
public class PieceViewGroup extends FrameLayout{
    private int columnCount = 4;

    private boolean isEditing = false;
    private int enderCount;

    private int divider;

    private ArrayList<PieceView> views = new ArrayList<>();

    public PieceViewGroup(Context context) {
        this(context, null);
        init();
    }

    public PieceViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public PieceViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        divider = JUtils.dip2px(2);
    }

    public void addEnder(final PieceView view){
        views.add(view);
        view.setViewGroup(this);
        addView(view);
        view.post(new Runnable() {
            @Override
            public void run() {
                view.applyEditMode(isEditing, false);
            }
        });
        enderCount++;
    }

    public void add(final PieceView view){
        int pos = getCount()-enderCount;
        views.add(pos,view);
        view.setViewGroup(this);
        addView(view,pos);
        view.post(new Runnable() {
            @Override
            public void run() {
                view.applyEditMode(isEditing,false);
            }
        });
    }

    public View get(int index){
        return views.get(index);
    }

    public void delete(PieceView view){
        views.remove(view);
        removeView(view);
    }

    public void clear(){
        views.clear();
        removeAllViews();
    }

    public int getCount(){
        return views.size();
    }

    void edit(){
        isEditing = true;
        for (PieceView c:views){
            c.applyEditMode(true,false);
        }
    }

    @Override
    public boolean isInEditMode() {
        return isEditing;
    }

    public void finishEdit(){
        isEditing = false;
        for (PieceView c:views){
            c.applyEditMode(false,false);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int size = (measuredWidth-getPaddingLeft()-getPaddingRight())/columnCount;

        int line = (views.size()-1)/4+1;

        int measuredHeight = (views.size() == 0?0:size*line)+getPaddingTop()+getPaddingBottom();

        setMeasuredDimension(measuredWidth,measuredHeight);
        int childSize = (measuredWidth-getPaddingLeft()-getPaddingRight() - divider *(columnCount-1))/columnCount;
        int sizeSpec = MeasureSpec.makeMeasureSpec(childSize,MeasureSpec.AT_MOST);
        for (View v:views){
            v.measure(sizeSpec,sizeSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int wight = right - left;
        int size = (wight-getPaddingLeft()-getPaddingRight() - divider *(columnCount-1))/columnCount;
        for (int i = 0 ; i < views.size() ; i++){
            View child = get(i);

            int x = (i % 4) * (size+divider) + getPaddingLeft();
            int y = (i / 4) * (size+divider) + getPaddingTop();

            child.layout(x,y,x+size,y+size);
        }
    }
}
