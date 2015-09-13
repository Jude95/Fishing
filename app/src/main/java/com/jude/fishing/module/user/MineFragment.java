package com.jude.fishing.module.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.fishing.R;
import com.jude.tagview.TAGView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
@RequiresPresenter(MinePresenter.class)
public class MineFragment extends BeamFragment<MinePresenter> {
    @InjectView(R.id.avatar)
    SimpleDraweeView avatar;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.sign)
    TextView sign;
    @InjectView(R.id.arrow)
    ImageView arrow;
    @InjectView(R.id.bbs_count)
    TextView bbsCount;
    @InjectView(R.id.bbs_container)
    LinearLayout bbsContainer;
    @InjectView(R.id.attention_count)
    TextView attentionCount;
    @InjectView(R.id.attention_container)
    LinearLayout attentionContainer;
    @InjectView(R.id.fans_count)
    TextView fansCount;
    @InjectView(R.id.fans_container)
    LinearLayout fansContainer;
    @InjectView(R.id.info)
    LinearLayout info;
    @InjectView(R.id.avatar_null)
    ImageView avatarNull;
    @InjectView(R.id.register)
    TAGView register;
    @InjectView(R.id.login)
    TAGView login;
    @InjectView(R.id.login_container)
    RelativeLayout loginContainer;
    @InjectView(R.id.comment)
    LinearLayout comment;
    @InjectView(R.id.collection)
    LinearLayout collection;
    @InjectView(R.id.setting)
    LinearLayout setting;
    @InjectView(R.id.version)
    TextView version;
    @InjectView(R.id.ablout)
    LinearLayout ablout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.user_fragment_main, container, false);
        ButterKnife.inject(this, root);
        login.setOnClickListener(v -> startActivity(new Intent(getActivity(), LoginActivity.class)));
        register.setOnClickListener(v->startActivity(new Intent(getActivity(),RegisterActivity.class)));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
