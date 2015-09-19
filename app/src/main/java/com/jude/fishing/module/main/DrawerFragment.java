package com.jude.fishing.module.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataFragment;
import com.jude.fishing.R;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.bean.Account;
import com.jude.fishing.module.setting.UpdateLogActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/9/17.
 */
@RequiresPresenter(DrawerPresenter.class)
public class DrawerFragment extends BeamDataFragment<DrawerPresenter,Account> {

    @InjectView(R.id.imgFace)
    SimpleDraweeView imgFace;
    @InjectView(R.id.tvName)
    TextView tvName;
    @InjectView(R.id.tvSign)
    TextView tvSign;
    @InjectView(R.id.viewAccount)
    LinearLayout viewAccount;
    @InjectView(R.id.place)
    RelativeLayout place;
    @InjectView(R.id.blog)
    RelativeLayout blog;
    @InjectView(R.id.message)
    RelativeLayout message;
    @InjectView(R.id.user)
    RelativeLayout user;
    @InjectView(R.id.setting)
    RelativeLayout setting;
    @InjectView(R.id.logout)
    RelativeLayout logout;
    @Override
    public void setData(Account info) {
        if (info == null){
            imgFace.setImageURI(null);
            tvName.setText("未登录,点击登陆");
            tvSign.setText("");
        }else{
            imgFace.setImageURI(Uri.parse(info.getAvatar()));
            tvName.setText(info.getName());
            tvSign.setText(info.getSign());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_drawer, container, false);
        ButterKnife.inject(this, view);
        viewAccount.setOnClickListener(v -> getPresenter().checkLogin());
        place.setOnClickListener(v -> getPresenter().showPlaceFragment());
        blog.setOnClickListener(v -> getPresenter().showBlogFragment());
        message.setOnClickListener(v -> getPresenter().showMessageFragment());
        user.setOnClickListener(v -> getPresenter().showUserFragment());
        setting.setOnClickListener(v -> startActivity(new Intent(getActivity(), UpdateLogActivity.class)));
        logout.setOnClickListener(v -> AccountModel.getInstance().logout());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void showFragment(Fragment fragment){
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
        ((MainActivity)getActivity()).closeDrawer();
    }
    private View lastView;
    public void focusView(View view){
        if (lastView!=null)lastView.setBackgroundColor(getResources().getColor(R.color.white));
        view.setBackgroundColor(getResources().getColor(R.color.gray_light));
        lastView = view;
    }

}
