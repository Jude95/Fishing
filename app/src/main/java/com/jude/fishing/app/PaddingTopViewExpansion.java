package com.jude.fishing.app;

import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.beam.expansion.overlay.DefaultViewExpansionDelegate;
import com.jude.utils.JUtils;

/**
 * Created by zhuchenxi on 15/9/26.
 */
public class PaddingTopViewExpansion extends DefaultViewExpansionDelegate {
    public PaddingTopViewExpansion(BeamBaseActivity activity, FrameLayout container) {
        super(activity, container);
    }

    @Override
    public void setToolbar(View view) {
        Toolbar toolbar = (Toolbar)view.findViewById(com.jude.beam.R.id.toolbar);
        if(toolbar != null) {
            this.getActivity().setSupportActionBar(toolbar);
            this.getActivity().onSetToolbar(toolbar);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                toolbar.setPadding(0, JUtils.getStatusBarHeight(), 0, 0);
            }
        }
    }

}
