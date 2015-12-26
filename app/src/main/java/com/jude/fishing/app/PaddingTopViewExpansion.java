package com.jude.fishing.app;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.beam.expansion.overlay.DefaultViewExpansionDelegate;

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
