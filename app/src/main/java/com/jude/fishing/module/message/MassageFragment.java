package com.jude.fishing.module.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.fishing.R;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
@RequiresPresenter(MassagePresenter.class)
public class MassageFragment extends BeamFragment<MassagePresenter> {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.message_fragment_main,container,false);
        return root;
    }
}
