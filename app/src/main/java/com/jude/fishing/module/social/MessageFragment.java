package com.jude.fishing.module.social;

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
@RequiresPresenter(MessagePresenter.class)
public class MessageFragment extends BeamFragment<MessagePresenter> {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("消息");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.social_fragment_message,container,false);
        return root;
    }
}
