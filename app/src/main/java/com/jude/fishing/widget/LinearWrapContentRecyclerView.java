package com.jude.fishing.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Mr.Jude on 2015/3/18.
 */
public class LinearWrapContentRecyclerView extends LinearLayout {

    public RecyclerView.Adapter mAdapter;

    public LinearWrapContentRecyclerView(Context context) {
        super(context);
    }

    public LinearWrapContentRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearWrapContentRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        this.mAdapter = adapter;
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged(){
        removeAllViews();
        if(mAdapter == null){
            return;
        }
        for (int i = 0 ; i < mAdapter.getItemCount() ; i++ ){
            RecyclerView.ViewHolder holder = mAdapter.createViewHolder(this,mAdapter.getItemViewType(i));
            mAdapter.bindViewHolder(holder,i);
            addView(holder.itemView);
        }
    }

}
