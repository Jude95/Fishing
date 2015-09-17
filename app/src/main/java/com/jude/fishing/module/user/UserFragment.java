package com.jude.fishing.module.user;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataFragment;
import com.jude.fishing.R;
import com.jude.fishing.model.bean.Account;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
@RequiresPresenter(UserPresenter.class)
public class UserFragment extends BeamDataFragment<UserPresenter,Account> {

    @InjectView(R.id.avatar)
    SimpleDraweeView avatar;
    @InjectView(R.id.container_avatar)
    RelativeLayout containerAvatar;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.container_name)
    RelativeLayout containerName;
    @InjectView(R.id.background)
    SimpleDraweeView background;
    @InjectView(R.id.container_background)
    RelativeLayout containerBackground;
    @InjectView(R.id.age)
    TextView age;
    @InjectView(R.id.container_age)
    RelativeLayout containerAge;
    @InjectView(R.id.skill)
    TextView skill;
    @InjectView(R.id.container_skill)
    RelativeLayout containerSkill;
    @InjectView(R.id.signature)
    TextView signature;
    @InjectView(R.id.phone)
    TextView phone;
    @InjectView(R.id.container_phone)
    RelativeLayout containerPhone;
    @InjectView(R.id.container_password)
    RelativeLayout containerPassword;
    @InjectView(R.id.container_logout)
    RelativeLayout containerLogout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("个人中心");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.user_fragment_main, container, false);
        ButterKnife.inject(this, root);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void setData(Account data) {
        avatar.setImageURI(Uri.parse(data.getAvatar()));
        background.setImageURI(Uri.parse(data.getBackground()));
        name.setText(data.getName());
        signature.setText(data.getSign());
        age.setText(data.getAge() + "年");
        skill.setText(data.getSkill());
        phone.setText(data.getPhone());
    }
}
