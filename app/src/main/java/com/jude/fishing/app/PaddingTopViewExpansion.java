package com.jude.fishing.app;

import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.beam.expansion.overlay.DefaultViewExpansionDelegate;
import com.jude.utils.JUtils;

/**
 * Created by zhuchenxi on 15/9/26.
 */
public class PaddingTopViewExpansion extends DefaultViewExpansionDelegate {
    public PaddingTopViewExpansion(BeamBaseActivity activity) {
        super(activity);
    }

    private MaterialDialog mProgressDialog;

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

    @Override
    public void showProgressDialog(String title) {
        if (mProgressDialog!=null)mProgressDialog.dismiss();
        mProgressDialog = new MaterialDialog.Builder(getActivity())
                .progress(true,100)
                .cancelable(false)
                .content(title)
                .show();
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog!=null)mProgressDialog.dismiss();
    }
}
