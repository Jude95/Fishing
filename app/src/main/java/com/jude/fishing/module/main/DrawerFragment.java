package com.jude.fishing.module.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataFragment;
import com.jude.fishing.R;
import com.jude.fishing.model.AccountModel;
import com.jude.fishing.model.ImageModel;
import com.jude.fishing.model.entities.Account;
import com.jude.fishing.module.setting.SettingActivity;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/9/17.
 */
@RequiresPresenter(DrawerPresenter.class)
public class DrawerFragment extends BeamDataFragment<DrawerPresenter, Account> implements View.OnClickListener {

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
    @InjectView(R.id.message_count)
    TextView messageCount;

    @Override
    public void setData(Account info) {
        JUtils.Log("DrawerFragment I Get It");
        if (info == null) {
            imgFace.setImageURI(null);
            tvName.setText("未登录,点击登陆");
            tvSign.setText("");
        } else {
            imgFace.setImageURI(ImageModel.getInstance().getSmallImage(info.getAvatar()));
            tvName.setText(info.getName());
            tvSign.setText(info.getSign());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_drawer, container, false);
        ButterKnife.inject(this, view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ((RelativeLayout.LayoutParams) imgFace.getLayoutParams()).setMargins(
                    JUtils.dip2px(16),
                    JUtils.dip2px(16) + JUtils.getStatusBarHeight(),
                    JUtils.dip2px(16),
                    JUtils.dip2px(16)
            );
        }
        imgFace.setOnClickListener(v -> getPresenter().checkLogin());
        viewAccount.setOnClickListener(v -> getPresenter().checkLogin());
        setting.setOnClickListener(v -> startActivity(new Intent(getActivity(), SettingActivity.class)));
        logout.setOnClickListener(v -> showLogoutDialog());
        place.setOnClickListener(this);
        blog.setOnClickListener(this);
        message.setOnClickListener(this);
        user.setOnClickListener(this);
        blog.post(new Runnable() {
            @Override
            public void run() {
                blog.performClick();
            }
        });

        return view;
    }

    public void setMessageCount(int i) {
        messageCount.setVisibility(i == 0 ? View.GONE : View.VISIBLE);
        messageCount.setText(i + "");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void showFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        ((MainActivity) getActivity()).closeDrawer();
    }

    private View lastView;

    public void focusView(View view) {
        if (lastView != null) lastView.setBackgroundColor(getResources().getColor(R.color.white));
        view.setBackgroundColor(getResources().getColor(R.color.gray_light));
        lastView = view;
    }

    private void showLogoutDialog() {
        MaterialDialog dialog = new MaterialDialog.Builder(getContext()).
                title("注销登录").
                content("您确定要退出登录吗？").
                positiveText("注销").
                negativeText("取消").
                callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
//                        getPresenter().showBlogFragment();
                        blog.performClick();
                        AccountModel.getInstance().logout();
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void onClick(View v) {
        if (drawerChangedListener!=null){
            if (v.getId()==R.id.message||v.getId()==R.id.user){
                if (!getPresenter().checkLogin())return;
            }
            drawerChangedListener.onChange(v);
            focusView(v);
        }
    }

    DrawerChangedListener drawerChangedListener;

    public void setDrawerChangedListener(DrawerChangedListener drawerChangedListener) {
        this.drawerChangedListener = drawerChangedListener;
    }

    public interface DrawerChangedListener{
        void onChange(View view);
    }
}
