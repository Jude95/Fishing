package com.jude.fishing.module.social;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.fishing.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
@RequiresPresenter(MessagePresenter.class)
public class MessageFragment extends BeamFragment<MessagePresenter> {

    @InjectView(R.id.around)
    FloatingActionButton around;
    @InjectView(R.id.contacts)
    FloatingActionButton contacts;
    @InjectView(R.id.search)
    FloatingActionButton search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("消息");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.social_fragment_message, container, false);
        ButterKnife.inject(this, root);
        around.setOnClickListener(v -> startActivity(new Intent(getActivity(), AroundActivity.class)));
        search.setOnClickListener(c->startActivity(new Intent(getActivity(),UserFindActivity.class)));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
