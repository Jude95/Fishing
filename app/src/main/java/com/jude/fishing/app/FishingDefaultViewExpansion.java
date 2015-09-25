package com.jude.fishing.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.jude.beam.expansion.DefaultViewExpansionDelegate;
import com.jude.beam.expansion.ViewExpansionDelegate;
import com.jude.fishing.R;

/**
 * Created by Mr.Jude on 2015/9/25.
 */
public class FishingDefaultViewExpansion extends DefaultViewExpansionDelegate {
    private View mLoadingView;
    private View mError;
    private OnRetryListener mRetryListener;

    public FishingDefaultViewExpansion(Context context, FrameLayout container) {
        super(context, container);
    }

    @Override
    public View showProgressPage() {
        if (mLoadingView==null)mLoadingView = LayoutInflater.from(getContext()).inflate(R.layout.activity_progress,getContainer(),false);
        if (mLoadingView.getParent()==null)getContainer().addView(mLoadingView);
        return mLoadingView;
    }

    @Override
    public void dismissProgressPage() {
        if (mLoadingView!=null)
            getContainer().removeView(mLoadingView);
    }

    @Override
    public View showErrorPage() {
        if (mError==null)mError = LayoutInflater.from(getContext()).inflate(com.jude.beam.R.layout.beam_view_error,getContainer(),false);
        if (mError.getParent()==null)getContainer().addView(mError);
        mError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRetryListener!=null)mRetryListener.onRetry();
            }
        });
        return mError;
    }

    @Override
    public void dismissErrorPage() {
        getContainer().removeView(mError);
    }


}
