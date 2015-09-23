package com.jude.fishing.module.setting;

import android.os.Bundle;
import android.widget.EditText;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.fishing.R;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

@RequiresPresenter(PwdChangePresenter.class)
public class PwdChangeActivity extends BeamBaseActivity<PwdChangePresenter> {

    @InjectView(R.id.et_old_pwd)
    EditText oldPwd;
    @InjectView(R.id.et_new_pwd)
    EditText newPwd;
    @InjectView(R.id.et_new_pwd2)
    EditText newPwd2;
    @InjectView(R.id.tg_ok)
    TAGView tgOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity_pwd_change);
        ButterKnife.inject(this);

        tgOk.setOnClickListener(v -> checkInput());
    }

    private void checkInput() {
        if (oldPwd.getText().toString().length() < 6 || oldPwd.getText().toString().length() > 12) {
            JUtils.Toast("请输入6-12位密码");
            return;
        }
        if (newPwd.getText().toString().length() < 6 || newPwd.getText().toString().length() > 12) {
            JUtils.Toast("请输入6-12位密码");
            return;
        }
        if (!newPwd2.getText().toString().equals(newPwd.getText().toString())) {
            JUtils.Toast("两次密码不一致");
            return;
        }

        getPresenter().changePwd(oldPwd.getText().toString(), newPwd.getText().toString());
    }
}
