package com.jude.fishing.module.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr.Jude on 2015/9/13.
 */
@RequiresPresenter(LoginPresenter.class)
public class LoginActivity extends BeamBaseActivity<LoginPresenter> {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.number)
    EditText number;
    @InjectView(R.id.password)
    EditText password;
    @InjectView(R.id.find_password)
    TextView findPassword;
    @InjectView(R.id.register)
    TextView register;
    @InjectView(R.id.login)
    TAGView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_login);
        ButterKnife.inject(this);
        register.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
        login.setOnClickListener(v-> checkInput());
        findPassword.setOnClickListener(v->startActivity(new Intent(this,ForgetPwdActivity.class)));
    }

    private void checkInput(){
        if (number.getText().toString().length() != 11) {
            JUtils.Toast("请输入正确手机号");
            return;
        }
        if (password.getText().toString().length() < 6 || password.getText().toString().length() > 12) {
            JUtils.Toast("请输入6-12位密码");
            return;
        }
        getPresenter().login(number.getText().toString(),password.getText().toString());
    }
}
