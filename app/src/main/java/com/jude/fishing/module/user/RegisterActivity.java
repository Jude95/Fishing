package com.jude.fishing.module.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

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
@RequiresPresenter(RegisterPresenter.class)
public class RegisterActivity extends BeamBaseActivity<RegisterPresenter> {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.number)
    EditText number;
    @InjectView(R.id.password)
    EditText password;
    @InjectView(R.id.password_re)
    EditText passwordRe;
    @InjectView(R.id.register)
    TAGView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_register);
        ButterKnife.inject(this);
        register.setOnClickListener(v->checkInput());
    }

    public void checkInput() {
        if (number.getText().toString().length() != 11) {
            JUtils.Toast("请输入正确手机号");
            return;
        }
        if (password.getText().toString().length() < 6 || password.getText().toString().length() > 12) {
            JUtils.Toast("请输入6-12位密码");
            return;
        }
        if (!passwordRe.getText().toString().equals(password.getText().toString())){
            JUtils.Toast("2次密码不一致");
            return;
        }
        getPresenter().checkTel(number.getText().toString(),password.getText().toString());
    }

}
